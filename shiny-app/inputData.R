source('util.R')

codes<-data.frame(read.csv(file='~/TermProject/AirlineAnalysis/results/AirlineCodes.csv', header=TRUE))
codes$Code<-as.character(codes$Code)
codes$Airline<-as.character(codes$Airline)

# Input Data Set
airline_safety = data.frame(read.table('~/TermProject/AirlineAnalysis/tests/data/airline-safety.tsv', header = TRUE, sep = '\t'))
delays = data.frame()#read.csv('../tests/data/on-time/2008.csv', header = TRUE)
paxfatal = data.frame(read.csv('~/TermProject/AirlineAnalysis/tests/data/paxfatal.csv', header = TRUE))

# On-Time results data set
otIn <- read.csv('~/TermProject/AirlineAnalysis/results/ontime-results/OnTimeStats.csv', header = TRUE)
colnames(otIn) <- cleanColNames(colnames(otIn))
ontimeStats<-data.frame(Airline = otIn$UniqueCarrier, CountFlights = otIn$CountUniqueCarrier)
ontimeStats<-data.frame(ontimeStats, PercentCancelled = 100*otIn$SumCancelled/ontimeStats$CountFlights)
ontimeStats<-data.frame(ontimeStats, PercentCarrierDelay = 100*otIn$CountCarrierDelay/ontimeStats$CountFlights)
ontimeStats<-data.frame(ontimeStats, PercentWeatherDelay = 100*otIn$CountWeatherDelay/ontimeStats$CountFlights)
ontimeStats<-data.frame(ontimeStats, PercentNASDelay = 100*otIn$CountNASDelay/ontimeStats$CountFlights)
ontimeStats<-data.frame(ontimeStats, PercentSecurityDelay = 100*otIn$CountSecurityDelay/ontimeStats$CountFlights)
ontimeStats<-data.frame(ontimeStats, PercentLateAircraftDelay = 100*otIn$CountLateAircraftDelay/ontimeStats$CountFlights)
ontimeStats<-data.frame(ontimeStats, PercentArrivalDelay = 100*otIn$CountArrDelay/ontimeStats$CountFlights)
ontimeStats<-data.frame(ontimeStats, AvgTaxiInDelay = 100*otIn$SumTaxiIn/ontimeStats$CountFlights)
ontimeStats<-data.frame(ontimeStats, AvgTaxiOutDelay = 100*otIn$SumTaxiOut/ontimeStats$CountFlights)
ontimeStats<-data.frame(ontimeStats, AvgDistance = 100*otIn$SumDistance/ontimeStats$CountFlights)
names(ontimeStats)[1]<-"Code"
ontimeStats<-merge(codes,ontimeStats)
ontimeStats<-ontimeStats[,!(names(ontimeStats)=='Code')]

ontimeCols<-colnames(ontimeStats)[-(1)]

# Airline Safety results data set
safety_results <- data.frame(read.csv(file='~/TermProject/AirlineAnalysis/results/safety-results/anlysOut.csv', header=TRUE))

# Airfare Results
airfare_results<- data.frame(read.csv(file='~/TermProject/AirlineAnalysis/results/airfare-results/Overall_Average_Cost_Per_Mile.csv', header=TRUE))
airfare_results$Code<-as.character(airfare_results$airline)
airfare_results<-airfare_results[,!(names(airfare_results)=='airline')]
airfare_results<-merge(codes,airfare_results)
airfare_results<-airfare_results[,!(names(airfare_results)=='Code')]
airfare_by_origin<-data.frame(read.csv(file='~/TermProject/AirlineAnalysis/results/airfare-results/AverageFarePerPersonByOriginAirport.csv', header=TRUE))