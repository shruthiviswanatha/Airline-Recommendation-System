/**
  * Created by ajpiers on 4/21/17.
  */
final case class OnTimeRecord(
  Year: Int,
  Month: Int,
  DayofMonth: Int,
  DayOfWeek: Int,
  DepTime: String,
  CRSDepTime: String,
  ArrTime: String,
  CRSArrTime: String,
  UniqueCarrier: String,
  FlightNum: String,
  TailNum: String,
  ActualElapsedTime: String,
  CRSElapsedTime: String,
  AirTime: String,
  ArrDelay: String,
  DepDelay: String,
  Origin: String,
  Dest: String,
  Distance: String,
  TaxiIn: String,
  TaxiOut: String,
  Cancelled: String,
  CancellationCode: String,
  Diverted: String,
  CarrierDelay: String,
  WeatherDelay: String,
  NASDelay: String,
  SecurityDelay: String,
  LateAircraftDelay: String){

  def getInt[String](v:Any): Int = {
    if (v.asInstanceOf[String]=="NA") {return 0}
    return v.toString.toInt
  }
}
