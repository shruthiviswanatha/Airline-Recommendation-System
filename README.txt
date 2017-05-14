###############################################################
# Airline Analysis README
# CS455
# 26 April, 2017
# Alex Jones, Adam Pierson, Isaac Trujillo, Shruthi Viswanatha
###############################################################

This project is composed of three separate dataset analysis and a common visualization component. Each Spark analysis uses a different CSV input dataset and outputs an aggregated CSV result.


# On-time-performance
#####################
Analysis of the ontime data set is performed by a Spark Job implemented in the airline_ontime directory. This Job assumes the ontime dataset has been staged in the
hdfs instance managed by the hadoop namenode associated with the spark master specified in the spark configuration. This job utilizes the spark JavaAPI to create a partitioned spark DataSet. Several statistics are gathered and grouped by airline, and collected into a single output file.

To Run On-time-performance analysis:
1. Stage the input data on hdfs and note the location.

2. CD to AirlineAnalys/airline_ontime.
3. Build the application with mvn package
4. Remove any previous results with:
  $HADOOP_HOME/bin/hdfs dfs -rm -r /AirlineAnalysis/on-time-results
5. To run the On-time-performance analysis on HDFS using YARN, use the following command:
  
  
  $SPARK_HOME/bin/spark-submit --class main.java.AnalysisDriver --master yarn --deploy-mode cluster --jars $SPARK_HOME/jars/spark-core_2.11-2.1.0.jar ~/AirlineAnalys   is/target/Ontime-Analysis-1.0.jar /AirlineAnalysis/on-time /AirlineAnalysis/on-time-results 10

  Note: The job parameters above are examples of location of the staged input and desired ouput location on hdfs.
 
6. Results must be saved locally to 'AirlineAnalysis/results/ontime-results' to anable exploration in the shiny-app descussed below.


# Airline cost
#####################
These Apache Spark Java projects perform analysis on the airfare dataset. The HDFS cluster will need to have an "airfares" directory located at the root level, in which all input dataset will be held. The Spark jobs will read all dataset csv files inside the "airfares" directory and perform the analysis. 

1. Create "airfares" directory on HDFS at the root level. Put input dataset inside the "airfares" directory.
2. Create "airfares_out" directory on HDFS at the root level. Output files will be written to this directory.

3. To Build, copy the "airline_airfares" directory to the computer running apache spark. Navigate to the "airline_airfares" directory, and build using maven command "mvn package" This will create the necessary jar files to run the program. 

4. To run the Airline Average Cost Per Mile analysis program on HDFS using YARN, use the following command:

 ~/spark-2.1.0-bin-hadoop2.7/bin/spark-submit --class main.java.airfares --master yarn --deploy-mode cluster --jars ~/airline_airfares/spark_dependencies/spark-core_2.10-2.1.0.jar ~/airline_airfares/target/simple-project-1.0.jar 10
 
where the paths to the "airline_airfares" directory are updated to match the correct location of the project directory. This will then submit the job to YARN. Once the job completes, it will create an output output file on HDFS at "airfare_out/run_<Timestamp>/average_<Timestamp>.txt"
 
 
 5. To run the Average Cost Per Mile by Airport and Average Fare by Airport analysis program on HDFS using YARN, use the following command:

~/spark-2.1.0-bin-hadoop2.7/bin/spark-submit --class main.java.airfares_byAirport --master yarn --deploy-mode cluster --jars ~/airline_airfares/spark_dependencies/spark-core_2.10-2.1.0.jar ~/airline_airfares/target/simple-project-1.0.jar 10
 
where the paths to the "airline_airfares" directory are updated to match the correct location of the project directory. This will then submit the job to YARN. Once the job completes, it will create an output output file on HDFS at "airfare_out/run_<Timestamp>/averageFarePerPersonByAirport_<Timestamp>.txt" and "airfare_out/run_<Timestamp>/averageFarePerMileByAirport_<Timestamp>.txt"


# Safety
#####################
This analysis uses hdfs, SparkR, Rshiny and R. 
The spark job uses R and visualization uses Rshiny. 

Running the spark job
	command to run my app under spark home directory
		shruthi5@boston:~ cd Term_Project/spark-2.1.0-bin-hadoop2.7/
		
	run the command to launch spark job 
		./bin/spark-submit ~/TermProject/arlSftySparkAnlysApp/drvSparkrAnlys.R
	
To perform interactive visual analysis of the data using the stored file on local file system
  (see Visualization Below)
	
	Spark job starts and after a while we get to see "Listening on http://127.0.0.1:portNo." in the command prompt
	Open firefox and submit "localhost:portNo." in the search tab
	It opens up a interactive data visualization app

File descriptions
	drvSparkrAnlys.R reads dataset from hdfs and performs some preliminary analysis on it and writes the output into a csv file in local file system.
	drvAppFn.R reads output file from local file system and calls a function kmeansAnlys (present in kmeansAnlysAppFn.R) which contains the ui and server part of code.
	kmeanAnlysAppFn.R contains the UI and server part of the code and the server part of code calls few functions in kmeansAnlys.R file such as plotCat, filtByCat where the kmeans 
	algorithm works and clusters each datapoints which belongs to 3 types of clusters and plots and list according to the requirement.


# Visualization
#####################
The visualization can be run on a system that has R installed.
This app assumes that results from the above analyses have been saved to AirlineAnalysis/results. Previously, acquired results are included.
But does not require a connection to a spark cluster.

The following dependencies must be installed :
[Home]$R
> install.packages('plyr')
> install.packages('shiny')
> install.packages('shinythemes')
> install.packages('ggplot2')
> install.packages('plotly')
(Note: If these are not available on your CRAN based  package manager, R Dev::Tools can be used to build from respective git repos)

The following command should start the app on localhost:
$cd AirlineAnalysis
$R -e "shiny::runApp('~/shiny-app')"


