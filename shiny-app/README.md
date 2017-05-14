


shiny-app
_______________________________________________________________

dependencies can be installed like this:
[Home]$R
> install.packages('plyr')
> install.packages('shiny')
> install.packages('shinythemes')
> install.packages('plotly')

The following command should start the app on localhost:
[Home]$cd AirlineAnalysis
[Home]$R -e "shiny::runApp('~/shiny-app')"

Alternatively, you can also install 'RStudio' which is an R IDE and run the app from there.

