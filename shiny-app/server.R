# Server logic for the vis-app
library(shiny,lib.loc='~/usr/local/R/library')
library(plotly,lib.loc='~/usr/local/R/library')
library(plyr,lib.loc='~/usr/local/R/library')
library(shinythemes,lib.loc='~/usr/local/R/library')
library(ggplot2,lib.loc='~/usr/local/R/library')

source('inputData.R')
source('plotting.R')
source('kmeansAnlys.R')

shinyServer(function(input, output) {
  
  output$saftey_plot <- renderPlotly({
    plotFields(airline_safety, "airline", input$safety_y)
  })
  
   output$delays_plot <- renderPlotly({
     plotCounts(delays, X=input$delays_x)
   })
  
  output$fatals_plot <- renderPlotly({
    plotCounts(paxfatal, X=input$fatals_x)
  })
  
  output$avg_fatals_plot <- renderPlotly({
    plotAverages(paxfatal, X = input$fatals_x, Y = input$fatals_y)
  })
  
  output$ot_results_plot <- renderPlotly({
    plotFields(ontimeStats, "Airline", input$ot_stats_y)
  })
  
  output$ot_summary_tbl<- renderTable({
    tbl<-data.frame()
    for(y in input$ot_stats_y){
      dat<-ontimeStats[!is.na(ontimeStats[,y]),]
      tbl[y,"Y-Value"]<-y
      tbl[y,"Mean"]<-mean(dat[,y])
      tbl[y,"Sdev"]<-sd(dat[,y])
    }
    return(tbl)
  })
  
  output$airfare_plot <- renderPlotly({
    p<-plotFields(airfare_results, "Airline", "avgCostPerMile")
  })
  output$airfare_by_origin_plot <- renderPlotly({
    p<-plotFields(airfare_by_origin, "Airport", "avgFare")
  })
  
  output$safety_results_plot <- renderPlot({
    plotCat(safety_results, 3)
  })
  
  output$safety_tbl <- renderTable({
    filtByCat(safety_results, 3, input$cat)
  })
  
})