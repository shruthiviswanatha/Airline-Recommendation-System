crtClst <- function (df, numCat) {
	#categorize the data points in volume of travel and fatalities
	# plot into "numCat" categories using kmeans clustering
	set.seed(20)
	sfCluster <- kmeans(df[, 2:3], numCat, nstart = 20)
	sfCluster$cluster <- as.factor(sfCluster$cluster)
	
	#sfCat column contians the category an airline operator falls
	#into
	df$sfCat <- sfCluster$cluster
	return(df)
}

