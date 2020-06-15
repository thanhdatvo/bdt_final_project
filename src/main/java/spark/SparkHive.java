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
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;

import scala.Tuple2;

public final class SparkHive {

	public static void main(String[] args) throws Exception {

		SparkSession ss = SparkSession.builder().appName("Java Spark Hive Example")
//				.config("spark.driver.allowMultipleContexts", "true")
//				.config("spark.sql.warehouse.dir", "/user/hive/warehouse")
//				.enableHiveSupport()
				.getOrCreate();

		Dataset<Row> input = ss.read().option("header", "true")
				.option("inferSchema", "true").csv("/user/user/input/data-0.csv");
		input.show();


	}
}
