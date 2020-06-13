package spark;

import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoder;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

/**
 * Counts words in UTF8 encoded, '\n' delimited text received from the network.
 *
 * Usage: JavaStructuredNetworkOnlineStoreCount <hostname> <port> <hostname> and
 * <port> describe the TCP server that Structured Streaming would connect to
 * receive data.
 *
 * To run this on your local machine, you need to first run a Netcat server `$
 * nc -lk 9999` and then run the example `$ bin/run-example
 * sql.streaming.JavaStructuredNetworkOnlineStoreCount localhost 9999`
 */
public final class SparkElasticsearch {

	public static void main(String[] args) throws Exception {
		if (args.length < 2) {
			System.err.println("Usage: JavaStructuredNetworkOnlineStoreCount <hostname> <port>");
			System.exit(1);
		}

		String host = args[0];
		int port = Integer.parseInt(args[1]);

		SparkSession spark = SparkSession.builder().appName("JavaStructuredNetworkOnlineStoreCount").master("local[*]")
				.config("es.index.auto.create", "true").config("es.nodes.wan.only", "true")
				.config("es.nodes.wan.only", "true").config("es.nodes", "localhost").config("es.port", "9200")
				.getOrCreate();

		spark.sparkContext().setLogLevel("ERROR");

		Dataset<Row> lines = spark.readStream().format("socket").option("host", host).option("port", port).load();

		Encoder<OnlineStore> onlineStoreEncoder = Encoders.bean(OnlineStore.class);

		Dataset<OnlineStore> onlineStoreData = lines.as(Encoders.STRING()).map((MapFunction<String, OnlineStore>) x -> {
			String[] properties = x.split(",");
			return new OnlineStore(properties[0], properties[1], properties[2], properties[3], properties[4],
					properties[5], properties[6], properties[7], properties[8]);
		}, onlineStoreEncoder);

		onlineStoreData.writeStream().format("org.elasticsearch.spark.sql")
				.option("checkpointLocation", "/home/checkpointLocation8").start("online_stores/_doc")
				.awaitTermination();

	}
}
