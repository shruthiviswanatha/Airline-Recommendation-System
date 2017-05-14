package main.java;

import static org.apache.spark.sql.functions.avg;
import static org.apache.spark.sql.functions.col;
import static org.apache.spark.sql.functions.stddev;
import static org.apache.spark.sql.functions.sum;

import java.io.IOException;

import static org.apache.spark.sql.functions.count;

import org.apache.spark.sql.DataFrameNaFunctions;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;


public class AnalysisDriver {
	static String statsFileName = "otrStats.csv";
	public static void main(String[] args) {
		String inDir = args[0];
		String outDir = args[1];
		
		SparkSession spark = SparkSession.builder()
				.appName("Airline On-Time Analysis")
				.getOrCreate();
		
 //		Uncomment to run on you're local machine
//		SparkSession spark = SparkSession.builder()
//				.appName("Airline Analysis")
//				.master("local[2]")
//				.getOrCreate();
		
		//Define the data and automatically infer data types
		Dataset<Row> ds = spark.read()
				.format("com.databricks.spark.csv")
				.option("header", "true") 
				.option("inferSchema", "true") 
				.load(inDir);
		
		// print the inferred schema.
		//ds.printSchema();
		
		// Replace NaNs with 0
		DataFrameNaFunctions filter = new DataFrameNaFunctions(ds);
		Dataset<Row> cleanDS = filter.fill(0);
		
		//Take advantage of catalyst optimizer by telling spark what columns exist and what thier types are,
		//by telling spark that they are 'OnTimeRecords'.
		Dataset<OnTimeRecord> otrDS = cleanDS.as(Encoders.bean(OnTimeRecord.class));
		otrDS.repartition(col("Year"));
		//Define the calculations we want to fill our dataset with
		Dataset<Row> otrStats = otrDS.groupBy(col("UniqueCarrier"))
				.agg(count(col("UniqueCarrier")),
					 sum(col("Cancelled")),
					 sum(col("WeatherDelay")),
					 sum(col("WeatherDelay").gt(0).cast("integer")).alias("Count(WeatherDelay)"),
					 sum(col("CarrierDelay")),
					 sum(col("CarrierDelay").gt(0).cast("integer")).alias("Count(CarrierDelay)"),
					 sum(col("NASDelay")),
					 sum(col("NASDelay").gt(0).cast("integer")).alias("Count(NASDelay)"),
					 sum(col("SecurityDelay")),
					 sum(col("SecurityDelay").gt(0).cast("integer")).alias("Count(SecurityDelay)"),
					 sum(col("LateAircraftDelay")),
					 sum(col("LateAircraftDelay").gt(0).cast("integer")).alias("Count(LateAircraftDelay)"),
					 sum(col("ArrDelay")),
					 sum(col("ArrDelay").gt(0).cast("integer")).alias("Count(ArrDelay)"),
					 sum(col("TaxiIn")),
					 sum(col("TaxiOut")),
					 sum(col("Distance"))
					 );
		//otrStats.printSchema();
		//otrStats.show();
		
		//Combine the dataset into a single output file (expensive!)
		otrStats.coalesce(1).write().option("header", "true").csv(outDir);
		
		//Rename the file something we can search for programmatically
		FileSystem fs;
		try {
			fs = FileSystem.get(spark.sparkContext().hadoopConfiguration());
			String file = fs.globStatus(new Path(outDir + "/part*"))[0].getPath().getName();
			fs.rename(new Path(outDir + file), new Path(outDir + statsFileName));
			fs.delete(new Path(statsFileName + "-temp"), true);				
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			spark.stop();			
		}


		//otrDS.createOrReplaceTempView("AirlineSafety");

	}
}