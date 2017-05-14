/**
  * Created by ajpiers on 4/20/17.
  * [Year,Month,DayofMonth,DayOfWeek,DepTime,CRSDepTime,ArrTime,
  * CRSArrTime,UniqueCarrier,FlightNum,TailNum,ActualElapsedTime,
  * CRSElapsedTime,AirTime,ArrDelay,DepDelay,Origin,Dest,Distance,
  * TaxiIn,TaxiOut,Cancelled,CancellationCode,Diverted,CarrierDelay,
  * WeatherDelay,NASDelay,SecurityDelay,LateAircraftDelay]
  */

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.expressions.scalalang.typed.{
count,
sum, avg}
import org.apache.spark.sql.functions.stddev

import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}
//For implicit conversions like converting RDDs to DataFrames


  object SampleApp {
    def main(args: Array[String]) {
      val spark = SparkSession
        .builder()
        .appName("Spark SQL basic example")
        .master("local[2]")
        .config("spark.executor.memory","1g")
        .getOrCreate()
      import spark.implicits._

      val df = spark.read
        .format("com.databricks.spark.csv")
        .option("header", "true") // Use first line of all files as header
        .option("inferSchema", "true") // Automatically infer data types
        .load("tests/data/on-time/")

      //Use a DataSet for complile time typesaftey and to allow catallyst to optimize
      val ds = df.as[OnTimeRecord]
      // Displays the layout of the DataSet to stdout
      ds.printSchema()

      ds.groupByKey(record => record.UniqueCarrier)
        .agg(avg[OnTimeRecord](x=>x.getInt(x.DepDelay)).name("avg(DepDelay)"),
            count[OnTimeRecord](_.UniqueCarrier).name("count(UniqueCarrier)"),
            sum[OnTimeRecord](x=>x.getInt(x.DepDelay)).name("sum(DepDelay)")
          )
        .withColumnRenamed("value", "group")
        .alias("Summary by airline")
        .show()


      df.createOrReplaceTempView("AirlineSafety")

    }
  }
