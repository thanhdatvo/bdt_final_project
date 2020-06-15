package spark;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoder;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.*;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;

import scala.Tuple2;

public final class SparkHive_new {

	public static void main(String[] args) throws Exception {

		SparkSession ss = SparkSession.builder().appName("Java Spark Hive Example")
				.config("spark.driver.allowMultipleContexts", "true")
				.config("spark.sql.warehouse.dir", "/user/hive/warehouse").enableHiveSupport().getOrCreate();

		 StructField[] structFields = new StructField[]{
		            new StructField("eventTime", DataTypes.StringType, true, Metadata.empty()),
		            new StructField("eventType", DataTypes.StringType, true, Metadata.empty()),
		            new StructField("productId", DataTypes.StringType, true, Metadata.empty()),
		            new StructField("categoryId", DataTypes.StringType, true, Metadata.empty()),
		            new StructField("categoryCode", DataTypes.StringType, true, Metadata.empty()),
		            new StructField("brand", DataTypes.StringType, true, Metadata.empty()),
		            new StructField("price", DataTypes.DoubleType, true, Metadata.empty()),
		            new StructField("userId", DataTypes.StringType, true, Metadata.empty()),
		            new StructField("userSession", DataTypes.StringType, true, Metadata.empty()),
		    };
		StructType onlineStoreSchema = new StructType(structFields);
//		StructType onlineStoreSchema2 = new StructType()
//				.add("p1", DataTypes.StringType)
//				.add("p2", DataTypes.StringType)
//				.add("p3", DataTypes.StringType)
//				.add("p4", DataTypes.StringType)
//				.add("p5", DataTypes.StringType)
//				.add("p6", DataTypes.StringType)
//				.add("p7", DataTypes.DoubleType)
//				.add("p8", DataTypes.StringType)
//				.add("p9", DataTypes.StringType)
//				;
//				
//		
//		System.out.print(onlineStoreSchema.equals(onlineStoreSchema2));
		Dataset<Row> input = ss.readStream()
//				.format("com.databricks.spark.csv")
				.option("header", "true")
//				.option("inferSchema", "true")
				.schema(onlineStoreSchema)
				.csv("/user/user/input/data-0.csv");
		
		
		Encoder<OnlineStore> onlineStoreEncoder = Encoders.bean(OnlineStore.class);
		Dataset<OnlineStore> onlineStoreData = input.as(onlineStoreEncoder);

		onlineStoreData.writeStream().format("org.elasticsearch.spark.sql")
				.option("checkpointLocation", "/home/checkpointLocation5").start("online_stores/_doc")
				.awaitTermination();
		
//		input.show();
//
//		
//		JavaStreamingContext ssc = new JavaStreamingContext(new JavaSparkContext(ss.sparkContext()), new Duration(1000));
//
//		Queue<JavaRDD<Row>> rddQueue = new LinkedList<>();
//		rddQueue.add(input.toJavaRDD());
//
//
//		JavaDStream<Row> dsStream = ssc.queueStream(rddQueue);
//
//		Encoder<OnlineStore> onlineStoreEncoder = Encoders.bean(OnlineStore.class);
//
//		dsStream.foreachRDD(rdd -> {
//			Dataset<Row> rowDataset = ss.createDataFrame(rdd, OnlineStore.class);
//			Dataset<OnlineStore> onlineStoreData = rowDataset.map((MapFunction<Row, OnlineStore>) row -> {
//				return new OnlineStore(row.get(0).toString(), row.get(1).toString(), row.get(2).toString(),
//						row.get(3).toString(), row.get(4).toString(), row.get(5).toString(), row.get(6).toString(),
//						row.get(7).toString(), row.get(8).toString());
//			}, onlineStoreEncoder);
//			onlineStoreData.writeStream().format("org.elasticsearch.spark.sql")
//					.option("checkpointLocation", "/home/checkpointLocation5").start("online_stores/_doc")
//					.awaitTermination();
//
//		});
//		
//		ssc.start();
//		ssc.awaitTermination();

	}
}
