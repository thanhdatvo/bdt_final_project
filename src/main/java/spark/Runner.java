package spark;

import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoder;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.SparkSession;

public final class Runner {

	public static void main(String[] args) throws Exception {
		if (args.length < 2) {
			System.err.println("Usage: JavaStructuredNetworkOnlineStoreCount <hostname> <port>");
			System.exit(1);
		}

		String kafkaHost = args[0];
		int kafkaPort = Integer.parseInt(args[1]);
		String topic = "ecommerce";
		SparkSession spark = SparkSession.builder().appName("JavaStructuredNetworkOnlineStoreCount").master("local[*]")
				.config("es.index.auto.create", "true").config("es.nodes.wan.only", "true")
				.config("es.nodes.wan.only", "true").config("es.nodes", "localhost").config("es.port", "9200")
				.getOrCreate();

		spark.sparkContext().setLogLevel("ERROR");

		Dataset<String> lines = spark.readStream().format("kafka")
				.option("kafka.bootstrap.servers", kafkaHost + ":" + kafkaPort).option("subscribe", topic).load()
				.selectExpr("CAST(value AS STRING)").as(Encoders.STRING());

		Encoder<OnlineStore> onlineStoreEncoder = Encoders.bean(OnlineStore.class);

		Dataset<OnlineStore> onlineStoreData = lines.as(Encoders.STRING()).map((MapFunction<String, OnlineStore>) x -> {
			String[] properties = x.split(",");
			return new OnlineStore(properties[0], properties[1], properties[2], properties[3], properties[4],
					properties[5], properties[6], properties[7], properties[8]);
		}, onlineStoreEncoder);

		
		onlineStoreData.writeStream()
//		.foreachBatch((dataset, batchId)->{
//			dataset.show();
//		})
		.format("org.elasticsearch.spark.sql")
				.option("checkpointLocation", "/home/checkpointLocation").start(topic + "/_doc")
				.awaitTermination();
		
//		onlineStoreData.toJavaRDD().foreach(rdd -> {
//			Configuration config = HBaseConfiguration.create();
//
//			System.out.println(config.toString());
//			try (Connection connection = ConnectionFactory.createConnection(config); Admin admin = connection.getAdmin()) {
//				TableName tableName = TableName.valueOf(TABLE);
//				HTableDescriptor descriptor = new HTableDescriptor(tableName);
//				descriptor.addFamily(new HColumnDescriptor(CF_DEFAULT).setCompressionType(Algorithm.NONE));
//				descriptor.addFamily(new HColumnDescriptor("prof_details"));
//			
//				System.out.print("Creating table.... ");
//				
//				if (admin.tableExists(tableName)) {
//					admin.disableTable(tableName);
//					admin.deleteTable(tableName);
//				}
//
//				admin.createTable(descriptor);
//				
//				Table table = connection.getTable(tableName);
//				
//				Put p = new Put(Bytes.toBytes("1"));
//
//				p.addColumn(Bytes.toBytes(CF_DEFAULT), Bytes.toBytes("Name"), Bytes.toBytes("John"));
//				p.addColumn(Bytes.toBytes(CF_DEFAULT), Bytes.toBytes("City"), Bytes.toBytes("Boston"));
//				p.addColumn(Bytes.toBytes("prof_details"), Bytes.toBytes("Designation"), Bytes.toBytes("Manager"));
//				p.addColumn(Bytes.toBytes("prof_details"), Bytes.toBytes("salary"), Bytes.toBytes("150,000"));
//				
//				table.put(p);
//			}
//		});

	}
}
