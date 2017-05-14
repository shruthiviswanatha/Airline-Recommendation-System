# Download Spark version for our hadoop version on CSU machine (186 Mb)

wget http://d3kbcqa49mib13.cloudfront.net/spark-2.1.0-bin-hadoop2.7.tgz
tar -xvzf spark-2.1.0-bin-hadoop2.7.tgz

# In the ~/.bashrc add:
export SPARK_HOME=${HOME}/spark-2.1.0-bin-hadoop2.7
source ~/.bashrc

# In the configuration directory:
cd $SPARK_HOME/conf

# Add these lines spark-env.sh.template (save it as spark-env.sh):
export SPARK_MASTER_IP=<some.csu.machine>.cs.colostate.edu
export SPARK_MASTER_PORT=<unique_port>
export SPARK_MASTER_WEBUI_PORT=<another_unique_port>
export SPARK_WORKER_CORES=1
export SPARK_WORKER_MEMORY=2g
export SPARK_WORKER_INSTANCES=1

# Add these lines to spark-defualts.conf.template (save it as spark-defualts.conf)
# Note: fs.default.name:= name node of your local cluster and your.namenode.port
# 	is it's port specified in core-site.xml
spark.master spark://<some.csu.machine>.cs.colostate.edu:<SPARK_MASTER_PORT>
spark.eventLog.dir hdfs://<fs.default.name>.cs.colostate.edu:<your.namenode.port>/spark_log
spark.eventLog.enabled true
spark.serializer org.apache.spark.serializer.KryoSerializer
spark.logConf false
spark.executor.memory 2g


#Start spark with:
$SPARK_HOME/sbin/start-all.sh

#Run spark-shell (it'll detect the spark master started above)
$SPARK_HOME/bin/spark-shell --packages com.databricks:spark-csv_2.10:1.3.0

#Get some data
val df = spark.read.format("com.databricks.spark.csv").option("header", "true").option("inferSchema", "true").load("hdfs://salem.cs.colostate.edu:31070/AirlineAnalysis/on-time/*.csv")
