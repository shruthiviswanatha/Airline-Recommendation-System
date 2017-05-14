package main.java;
import java.io.Serializable;
import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.Encoder;
import org.apache.spark.sql.Encoders;

public class OnTimeRecord implements Serializable {
	private int Year,Month,DayofMonth,DayOfWeek,
	DepTime,CRSDepTime,ArrTime,CRSArrTime,FlightNum,
	ActualElapsedTime,CRSElapsedTime,AirTime,ArrDelay,Distance,
	  TaxiIn,TaxiOut,Cancelled,CancellationCode,Diverted,CarrierDelay,DepDelay,
	  WeatherDelay,NASDelay,SecurityDelay,LateAircraftDelay;
	  
	private String 
	  UniqueCarrier,Origin,Dest,TailNum;
	  
	  
	
	public int getYear() {
		return Year;
	}
	public void setYear(int year) {
		Year = year;
	}
	public int getMonth() {
		return Month;
	}
	public void setMonth(int month) {
		Month = month;
	}
	public int getDayofMonth() {
		return DayofMonth;
	}
	public void setDayofMonth(int dayofMonth) {
		DayofMonth = dayofMonth;
	}
	public int getDayOfWeek() {
		return DayOfWeek;
	}
	public void setDayOfWeek(int dayOfWeek) {
		DayOfWeek = dayOfWeek;
	}
	public int getDepTime() {
		return DepTime;
	}
	public void setDepTime(int depTime) {
		DepTime = depTime;
	}
	public int getCRSDepTime() {
		return CRSDepTime;
	}
	public void setCRSDepTime(int cRSDepTime) {
		CRSDepTime = cRSDepTime;
	}
	public int getArrTime() {
		return ArrTime;
	}
	public void setArrTime(int arrTime) {
		ArrTime = arrTime;
	}
	public int getCRSArrTime() {
		return CRSArrTime;
	}
	public void setCRSArrTime(int cRSArrTime) {
		CRSArrTime = cRSArrTime;
	}
	public String getUniqueCarrier() {
		return UniqueCarrier;
	}
	public void setUniqueCarrier(String uniqueCarrier) {
		UniqueCarrier = uniqueCarrier;
	}
	public int getFlightNum() {
		return FlightNum;
	}
	public void setFlightNum(int flightNum) {
		FlightNum = flightNum;
	}
	public String getTailNum() {
		return TailNum;
	}
	public void setTailNum(String tailNum) {
		TailNum = tailNum;
	}
	public int getActualElapsedTime() {
		return ActualElapsedTime;
	}
	public void setActualElapsedTime(int actualElapsedTime) {
		ActualElapsedTime = actualElapsedTime;
	}
	public int getCRSElapsedTime() {
		return CRSElapsedTime;
	}
	public void setCRSElapsedTime(int cRSElapsedTime) {
		CRSElapsedTime = cRSElapsedTime;
	}
	public int getAirTime() {
		return AirTime;
	}
	public void setAirTime(int airTime) {
		AirTime = airTime;
	}
	public int getArrDelay() {
		return ArrDelay;
	}
	public void setArrDelay(int arrDelay) {
		ArrDelay = arrDelay;
	}
	public int getDepDelay() {
		return DepDelay;
	}
	public void setDepDelay(int depDelay) {
		DepDelay = depDelay;
	}
	public String getOrigin() {
		return Origin;
	}
	public void setOrigin(String origin) {
		Origin = origin;
	}
	public String getDest() {
		return Dest;
	}
	public void setDest(String dest) {
		Dest = dest;
	}
	public int getDistance() {
		return Distance;
	}
	public void setDistance(int distance) {
		Distance = distance;
	}
	public int getTaxiIn() {
		return TaxiIn;
	}
	public void setTaxiIn(int taxiIn) {
		TaxiIn = taxiIn;
	}
	public int getTaxiOut() {
		return TaxiOut;
	}
	public void setTaxiOut(int taxiOut) {
		TaxiOut = taxiOut;
	}
	public int getCancelled() {
		return Cancelled;
	}
	public void setCancelled(int cancelled) {
		Cancelled = cancelled;
	}
	public int getCancellationCode() {
		return CancellationCode;
	}
	public void setCancellationCode(int cancellationCode) {
		CancellationCode = cancellationCode;
	}
	public int getDiverted() {
		return Diverted;
	}
	public void setDiverted(int diverted) {
		Diverted = diverted;
	}
	public int getCarrierDelay() {
		return CarrierDelay;
	}
	public void setCarrierDelay(int carrierDelay) {
		CarrierDelay = carrierDelay;
	}
	public int getWeatherDelay() {
		return WeatherDelay;
	}
	public void setWeatherDelay(int weatherDelay) {
		WeatherDelay = weatherDelay;
	}
	public int getNASDelay() {
		return NASDelay;
	}
	public void setNASDelay(int nASDelay) {
		NASDelay = nASDelay;
	}
	public int getSecurityDelay() {
		return SecurityDelay;
	}
	public void setSecurityDelay(int securityDelay) {
		SecurityDelay = securityDelay;
	}
	public int getLateAircraftDelay() {
		return LateAircraftDelay;
	}
	public void setLateAircraftDelay(int lateAircraftDelay) {
		LateAircraftDelay = lateAircraftDelay;
	}
	
	
}
