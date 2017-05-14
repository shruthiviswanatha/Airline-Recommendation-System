#UI Layout for vis-app

library(shiny,lib.loc='~/usr/local/R/library')
library(plotly,lib.loc='~/usr/local/R/library')
library(shinythemes,lib.loc='~/usr/local/R/library')
source("inputData.R")
navbarPage("Airline Analysis", theme = shinytheme("flatly"),
           tabPanel("Fatalities Raw",
                    sidebarLayout(
                      sidebarPanel(
                        radioButtons("fatals_y", "Y:",
                                     c("Passengers.Fatal", "Passengers.Surv"),
                                     selected = "Passengers.Fatal"
                        ),
                        
                        radioButtons("fatals_x", "X:",
                                     c("Date","Location","Operator","Aircraft.Type"),
                                     selected = "Operator"
                        )
                      ),
                      mainPanel(
                        tags$h1("Fatalities Raw Data Inspection"),
                        plotlyOutput("fatals_plot"),
                        plotlyOutput("avg_fatals_plot")
                      )
                    )
           ),
           tabPanel("Airline Safety Raw",
                    sidebarLayout(
                      sidebarPanel(
                        checkboxGroupInput("safety_y", "Y:",
                                           c("avail_seat_km_per_week","incidents_85_99","fatal_accidents_85_99","fatalities_85_99","incidents_00_14","fatal_accidents_00_14"),
                                           selected = "incidents_85_99")
                      ),
                      mainPanel(
                        tags$h1("Airline Safety Raw Data Inspection"),
                        plotlyOutput("saftey_plot")
                      )
                    )
           ),
           # This tab takes ~ 1 minute to load (disabled by defualt)
           # tabPanel("On-Time Raw (2008 only)",
           #          sidebarLayout(
           #            sidebarPanel(
           #              checkboxGroupInput("delays_y", "Y:",
           #                                 c("freq", "AvgDelay"),
           #                                 selected = "freq"
           #                                 ),
           #              radioButtons("delays_x", "X:",
           #                                 c("Month","DayOfWeek","CRSDepTime","Origin","Dest"),
           #                                 selected = "Origin"
           #                                 )
           #              ),
           #            mainPanel(
           #              tags$h1("On-time Raw Data Inspection"),
           #              plotlyOutput("delays_plot"),
           #              plotlyOutput("avg_delays_plot")
           #            )
           #          )
           # ),
           tabPanel("On-Time Analysis",
                    sidebarLayout(
                      sidebarPanel(
                        checkboxGroupInput("ot_stats_y", "Y:",
                                           ontimeCols,
                                           selected = ontimeCols
                        )
                      ),
                      mainPanel(
                        tags$h1("On-time Results by Airline"),
                        plotlyOutput("ot_results_plot"),
                        tableOutput("ot_summary_tbl")
                      )
                      
                    )
           ),
           tabPanel("Airfare Analysis",
                      mainPanel(
                        tags$h1("Airline Airfare Analysis"),
                        tabsetPanel(
                          tabPanel("By Airline", plotlyOutput("airfare_plot")),
                          tabPanel("By Airport", plotlyOutput("airfare_by_origin_plot"))
                        )
                      )
           ),
           tabPanel("Safety Analysis",
                    sidebarLayout(
                      sidebarPanel(
                        selectInput("cat", label = h4("Choose category"), 
                                    choices = list("Safe" = 2, "Unsafe" = 1,
                                                   "Not Recommended" = 3), selected = 1)
                      ),
                      mainPanel(
                        tags$h1("Airline Safety Analysis"),
                        tabsetPanel(
                          tabPanel("List",tableOutput("safety_tbl")),
                          tabPanel("Plot",plotOutput("safety_results_plot"))
                        )
                      )
                    )
           )

           
)