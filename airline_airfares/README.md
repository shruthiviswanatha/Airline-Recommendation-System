These Apache Spark Java projects create perform analysis on the airfare dataset. The HDFS cluster will need to have an "airfares" directory located at the root level, in which all input dataset will be held. The Spark jobs will read all dataset csv files inside the "airfares" directory and perform the analysis. 

1. Create "airfares" directory on HDFS at the root level. Put input dataset inside the "airfares" directory.
2. Create "airfares_out" directory on HDFS at the root level. Output files will be written to this directory.

3.
To Build, copy the "airline_airfares" directory to the computer running apache spark. Navigate to the "airline_airfares" directory, and build using maven command "mvn package" This will create the necessary jar files to run the program. 

4.
To run the Airline Average Cost Per Mile analysis program on HDFS using YARN, use the following command:

 ~/spark-2.1.0-bin-hadoop2.7/bin/spark-submit --class main.java.airfares --master yarn --deploy-mode cluster --jars ~/airline_airfares/spark_dependencies/spark-core_2.10-2.1.0.jar ~/airline_airfares/target/simple-project-1.0.jar 10
 
where the paths to the "airline_airfares" directory are updated to match the correct location of the project directory. This will then submit the job to YARN. Once the job completes, it will create an output output file on HDFS at "airfare_out/run_<Timestamp>/average_<Timestamp>.txt"
 
 
 5.
 To run the Average Cost Per Mile by Airport and Average Fare by Airport analysis program on HDFS using YARN, use the following command:

~/spark-2.1.0-bin-hadoop2.7/bin/spark-submit --class main.java.airfares_byAirport --master yarn --deploy-mode cluster --jars ~/airline_airfares/spark_dependencies/spark-core_2.10-2.1.0.jar ~/airline_airfares/target/simple-project-1.0.jar 10
 
where the paths to the "airline_airfares" directory are updated to match the correct location of the project directory. This will then submit the job to YARN. Once the job completes, it will create an output output file on HDFS at "airfare_out/run_<Timestamp>/averageFarePerPersonByAirport_<Timestamp>.txt" and "airfare_out/run_<Timestamp>/averageFarePerMileByAirport_<Timestamp>.txt"