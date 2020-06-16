package spark;
import org.apache.spark.sql.SparkSession;
public final class SparkHive {
	public static void main(String[] args) throws Exception {

		SparkSession ss = SparkSession.builder().appName("Java Spark Hive Example")
				.config("spark.driver.allowMultipleContexts", "true")
				.config("spark.sql.warehouse.dir", "/user/hive/warehouse").enableHiveSupport().getOrCreate();
		ss.sql("DROP TABLE IF EXISTS test_serializer");
		ss.sql("CREATE TABLE test_serializer(" 
				+ " event_time STRING," 
				+ " event_type STRING," 
				+ " product_id STRING,"
				+ " category_id STRING," 
				+ " category_code STRING," 
				+ " brand STRING," 
				+ " price STRING,"
				+ " user_id STRING," 
				+ " user_session STRING) "
				+ " ROW FORMAT DELIMITED FIELDS TERMINATED BY ','"
				+ " LINES TERMINATED BY '\n'"
				+ " STORED AS TEXTFILE");
		
		ss.sql("LOAD DATA LOCAL INPATH '/home/data-0.csv' INTO TABLE test_serializer");
		ss.sql("SELECT * FROM test_serializer").show();
		ss.close();
	}
}
