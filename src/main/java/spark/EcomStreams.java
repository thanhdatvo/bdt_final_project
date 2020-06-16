package spark;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka010.ConsumerStrategies;
import org.apache.spark.streaming.kafka010.KafkaUtils;
import org.apache.spark.streaming.kafka010.LocationStrategies;

/**
 * Counts words in UTF8 encoded, '\n' delimited text received from the network.
 *
 * Usage: JavaStructuredNetworkWordCount <hostname> <port>
 * <hostname> and <port> describe the TCP server that Structured Streaming
 * would connect to receive data.
 *
 * To run this on your local machine, you need to first run a Netcat server
 *    `$ nc -lk 9999`
 * and then run the example
 *    `$ bin/run-example sql.streaming.JavaStructuredNetworkWordCount
 *    localhost 9999`
 */
public final class EcomStreams {

	private static final String CHECKPOINT_DIR = "/tmp";
	
  public static void main(String[] args) throws Exception {
    if (args.length < 3) {
      System.err.println("Usage: JavaStructuredNetworkWordCount <hostname> <port> <topicName>");
      System.exit(1);
    }

    String host = args[0];
    int port = Integer.parseInt(args[1]);
    String topicName = args[2];
    
 
    
    SparkConf conf = new SparkConf().setMaster("local[*]").setAppName("ecommerce");

	JavaStreamingContext jssc = new JavaStreamingContext(conf, Durations.seconds(3));
	
	 
	 jssc.checkpoint(CHECKPOINT_DIR); 
		
		Map<String, Object> props = new HashMap<>();
		  props.put("bootstrap.servers", host+":"+port);
		 props.put("key.deserializer", StringDeserializer.class);
		 props.put("value.deserializer", StringDeserializer.class);
		 props.put("auto.offset.reset", "latest");
		 props.put("enable.auto.commit", false);
		 props.put("group.id",topicName+"_Ecom");
		 props.put("metadata.broker.list",host+":"+port);
		 
		 Collection<String> topics = Arrays.asList(topicName);
		
		
		 JavaInputDStream<ConsumerRecord<String, String>> directKafkaStream = KafkaUtils.createDirectStream(jssc, LocationStrategies.PreferConsistent(),
			    ConsumerStrategies.<String, String>Subscribe(topics, props));
	
	 
		 JavaDStream<String> lines = directKafkaStream.map(rc -> rc.value().toString());
	 
		 lines.foreachRDD((rdd)->{
			 
			// Get the singleton instance of SparkSession
			 SparkSession spark = MySparkSessionSingleton
					 .getInstance(rdd
					 .context().getConf());
			// Convert JavaRDD[String] to JavaRDD[bean class] to DataFrame
		      JavaRDD<Ecommerce> rowRDD = rdd.map(word -> {
		    	
		    	 String[] wrdsplit = word.split(",");
		    	 Ecommerce ec = new Ecommerce();
		    	 
		    	 ec.setEvent_time(wrdsplit[0]);
		    	 ec.setEvent_type(wrdsplit[1]);
		    	 ec.setProduct_id(wrdsplit[2]);
		    	 ec.setCategory_id(wrdsplit[3]);
		    	 ec.setCategory_code(wrdsplit[4]);
		    	 ec.setBrand(wrdsplit[5]);
		    	 ec.setPrice(Float.parseFloat(wrdsplit[6]));
		    	 ec.setUser_id(wrdsplit[7]);
		    	 ec.setUser_session(wrdsplit[8]);
		    	 
		        return ec;
		      });
		      Dataset<Row> ecDataFrame = spark.createDataFrame(rowRDD, Ecommerce.class);

		      // Creates a temporary view using the DataFrame
		      ecDataFrame.createOrReplaceTempView("ecommerce");

		      // Do word count on table using SQL and print it
		      Dataset<Row> ecCountDataFrame =
		          spark.sql("select count(*) as total from ecommerce");
		      System.out.println("==================");
		      
		      
		    // price range
		      Dataset<Row> price1 = spark.sql("Select * from ecommerce price between 0 and 100");
		      Dataset<Row> price2 = spark.sql("Select * from ecommerce price between 101 and 500");
		      Dataset<Row> price3 = spark.sql("Select * from ecommerce price between 501 and 2000");
		      
		      System.out.println("========0-100==========");
		      price1.show();
		      
		      System.out.println("========101-500==========");
		      price2.show();
		      
		      System.out.println("========501-2000==========");
		      price3.show();
		      
		   // The results of SQL queries are themselves DataFrames and support all normal functions.
		      Dataset<Row> sqlDF = spark.sql("SELECT value FROM src WHERE key < 10 ORDER BY key");
		      
		      
		      ecCountDataFrame.show();
		 });
		 
	
	jssc.start();
	
	jssc.awaitTermination();
    
  }
}

/** Lazily instantiated singleton instance of SparkSession */
class MySparkSessionSingleton {
  private static transient SparkSession instance = null;
//warehouseLocation points to the default location for managed databases and tables
  static String warehouseLocation = new File("spark-warehouse").getAbsolutePath();
  public static SparkSession getInstance(SparkConf sparkConf) {
    if (instance == null) {
      instance = SparkSession
        .builder()
        .config(sparkConf)
        .config("spark.sql.warehouse.dir", warehouseLocation)
        .enableHiveSupport()
        .getOrCreate();
    }
    return instance;
  }
}