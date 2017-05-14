package main.java;
/* SimpleApp.java */
import org.apache.spark.api.java.*;

import java.util.Arrays;
import java.util.List;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.api.java.function.PairFunction;

import scala.Tuple2;

public class airfares_byAirport {
	
	private static int ORIGIN = 4;
	private static int ORIGIN_STATE_ABBREV_INDEX = 10;
	private static int ORIGIN_STATE_FULL_INDEX = 11;
		private static int FARE_PER_MILE_INDEX = 16;
		private static int FARE_PER_PERSON_INDEX = 19;
		private static int CARRIER_INDEX = 17;
	
	
	  public static void main(String[] args) {
	    String data_directory = "hdfs://olympia.cs.colostate.edu:41802/airfares"; 
		SparkConf conf = new SparkConf().setAppName("Airfares");
		JavaSparkContext sc = new JavaSparkContext(conf);
		JavaRDD<String> source_data = sc.textFile(data_directory).cache();
		
		//read each line of excel file, split line on "," and return a list of strings split on ",".
		// the return type is a JavaRDD
		JavaRDD<List<String>> mappedData = source_data.map(
				new Function<String, List<String>>(){
					public List<String> call(String s){
						
						List<String> splitRowList = Arrays.asList(s.split(","));
						//System.out.println(splitRowList.get(ORIGIN_STATE_FULL_INDEX));
						
						try{
							// Do not map the first header row
							Long year = Long.parseLong(splitRowList.get(0));
						}catch(Exception ex){
							splitRowList = Arrays.asList(new String[0]);
						}
						
						//splitRowList.add();
						
											
						return splitRowList;
					}
				}
		);
		
		
		
		
		// use pair when returning a tuple. the string list is passed and we get a tuple of origin(orig) and fare per person(afpp)
		JavaPairRDD<String, Double> farePerAirport = mappedData.mapToPair(
				new PairFunction<List<String>, String, Double>(){
					public Tuple2<String, Double> call(List<String> s){
						if(s.size() >= 25){
							return(new Tuple2(s.get(ORIGIN), Double.parseDouble(s.get(FARE_PER_PERSON_INDEX))));
						}else{
							return(new Tuple2("", 0.0));
						}
					}
				}
		);
		
		// use pair when returning a tuple. the string list is passed and we get a tuple of origin(orig) and fare per mile(afpm)
		JavaPairRDD<String, Double> costPerMilePerAirport = mappedData.mapToPair(
				new PairFunction<List<String>, String, Double>(){
					public Tuple2<String, Double> call(List<String> s){
						if(s.size() >= 25){
							return(new Tuple2(s.get(ORIGIN), Double.parseDouble(s.get(FARE_PER_MILE_INDEX))));
						}else{
							return(new Tuple2("", 0.0));
						}
					}
				}
		);
		
		
		
		
		Long currentMillis = System.currentTimeMillis();
		
		// pass farePerAirport JavaPairRDD to below function - getSortedAveragePairRDD, for further processing
		getSortedAveragePairRDD(farePerAirport).coalesce(1).saveAsTextFile("hdfs://olympia.cs.colostate.edu:41802/airfares_out/run_"+currentMillis+"/averageFarePerPersonByAirport" + currentMillis);
		
		// pass costPerMilePerAirport JavaPairRDD to below function - getSortedAveragePairRDD, for further processing
		getSortedAveragePairRDD(costPerMilePerAirport).coalesce(1).saveAsTextFile("hdfs://olympia.cs.colostate.edu:41802/airfares_out/run_"+currentMillis+"/averageFarePerMileByAirport" + currentMillis);
		 
		
	    sc.stop();
	  }
	  
	  private static JavaPairRDD<String, Double> getSortedAveragePairRDD(JavaPairRDD<String, Double> cleanRDD){

			// remove rdds with blank keys
			JavaPairRDD<String, Double> cleanFarePerPerson = cleanRDD.filter(tuple -> tuple._1 != "");
			
			// associate count to each rdd value - airfare metric (afm) which is either afpp or afpm. 
			// Value is now a tuple [org, [afm, count]]
			JavaPairRDD<String, Tuple2<Double, Integer>> valueCount = cleanFarePerPerson.mapValues(value -> new Tuple2<Double, Integer>(value,1));
			
			// [org, [afmT, countT]]
			JavaPairRDD<String, Tuple2<Double, Integer>> reducedCount = valueCount.reduceByKey((tuple1,tuple2) ->  new Tuple2<Double, Integer>(tuple1._1 + tuple2._1, tuple1._2 + tuple2._2));
			
			// [org, aAfm]
			JavaPairRDD<String, Double> averagePair = reducedCount.mapToPair(getAverage);
			
			// [aAfm, org]
			JavaPairRDD<Double, String> swappedPair = averagePair.mapToPair(tuple -> new Tuple2<Double, String>(tuple._2, tuple._1));
			
			// [aAfm, org]
			JavaPairRDD<Double, String> sortedSwappedPair = swappedPair.sortByKey();
			
			// [org, aAfm]
			JavaPairRDD<String, Double> sortedAveragePair = sortedSwappedPair.mapToPair(tuple -> new Tuple2<String, Double>(tuple._2, tuple._1));
		  
			return sortedAveragePair;
	  }
	  
	  private static PairFunction<Tuple2<String, Tuple2<Double, Integer>>, String,Double> getAverage = (tuple) -> {
		     Tuple2<Double, Integer> val = tuple._2;
		     Double total = val._1;
		     int count = val._2;
		     Tuple2<String, Double> averagePair = new Tuple2<String, Double>(tuple._1, total / count);
		     return averagePair;
		  };
	  
}