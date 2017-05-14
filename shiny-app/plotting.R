plotFields<-function(df, X, Y_fields){
  if (nrow(df)< 2) return
  if (length(Y_fields)== 0) return
  p<-plot_ly()
  for(field in Y_fields){
    p<-add_trace(p, x = df[,X],   y = df[,field], name = field, type = 'bar') %>%
      layout(yaxis = list(title = str(field)), barmode = 'group',
             margin=list(b = 160))
  }
  return(p)
}

plotCounts<-function(df, X){
  df<-plyr::count(df,X)
  ttl<-paste0('Count by ', X)
  if (nrow(df)< 2) return
  p<-plot_ly() %>%
     add_trace(p, x = df[,X],   y = df$freq, name = ttl, type = 'bar') %>%
     layout(yaxis = list(title = 'Count'), barmode = 'group',
            margin=list(b = 160))
  return(p)
}

plotAverages<-function(df, X, Y){
  df<-agg(df, X, Y, mean)
  ttl<-paste0('Average ', Y)
  if (nrow(df)< 2) return
  p<-plot_ly() %>%
    add_trace(p, x = df[,X],   y = df[,Y], name = ttl,type = 'bar') %>%
    layout(yaxis = list(title = 'Average'), barmode = 'group',
           margin=list(b = 160))
  return(p)
}

plotCat <- function(df,numCat) {
  df <- crtClst(df, numCat)
  ggplot(df, aes(sr, tf, color = df$sfCat)) + 
    labs(x="Safety Ratio (sr)", y="Total Fatalites (tf)") +
    theme(axis.title = element_text(family = "Trebuchet MS", color="#666666", face="bold", size=14)) +
    scale_colour_discrete(name  ="Safety\nCategory",
                          breaks=c("1", "2", "3"),
                          labels=c("Unsafe", "Safe", "Not Recommended")) +
    geom_point()
}

agg<-function(df,X,Y,fn){
  result<-aggregate(df[,Y], list(grp=df[,X]),fn)
  colnames(result)<-c(X,Y)
  return(result)
}

