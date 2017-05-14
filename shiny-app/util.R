cleanColNames <- function(s) {
  s<-gsub("\\.", "", s)
  paste(toupper(substring(s, 1, 1)), substring(s, 2), sep = "")
}

agg<-function(df,X,Y,fn){
  result<-aggregate(df[,Y], list(grp=df[,X]),fn)
  colnames(result)<-c(X,Y)
  return(result)
}

filtByCat <- function(df, numCat, cat) {
  df <- crtClst(df, numCat)
  df <- df[df$sfCat == cat,]
  data.frame(df$airline, df$sr, df$tf)
}