library(SparkR) # spark frontend for this script to interact with

# connect with spark to access hdfs
sparkR.session(appName = "arlSftySparkAnlys")

sparkDf <- read.df("hdfs://boston.cs.colostate.edu:45076/cs455/Term_Project/airline_safety.csv", header='true', inferSchema='true', "csv")
sparkDf$tf <- sparkDf$fatalities_85_99 + sparkDf$fatalities_00_14
sparkDf$tfe9 <- (sparkDf$fatalities_85_99 + sparkDf$fatalities_00_14) * 1e9
sparkDf$sr <- sparkDf$tfe9 / sparkDf$avail_seat_km_per_week
csvDf <- select(sparkDf, sparkDf$airline, sparkDf$tf, sparkDf$sr)
rCsvDf <- collect(csvDf)
write.csv(file='~/TermProject/anlysOut.csv', x=rCsvDf, row.names = FALSE)

#exit spark session
sparkR.session.stop()

