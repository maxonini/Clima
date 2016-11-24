package com.climattention.shared;

import java.io.Serializable;

public class Datapoint implements Serializable {
	
	public Datapoint(){}

/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
private String date;
private String year;
private String country;
private String city;
private float temperature;
private float avgTemperatur;
private float uncertainty;
private float longitude;
private float latitude;

public Datapoint(String date,float temperature,float uncertainty,String city,String country,float latitude,float longitude){
	this.date= date; 
	this.temperature = temperature;
	this.uncertainty = uncertainty;
	this.country=country;
	this.city= city;
	this.longitude = longitude;
	this.latitude = latitude;		
	}
public String getYear(){
	return year;
	}
public float getLongitude(){
	return longitude;
	}
public float getLatitude(){
	return latitude;
	}
public float getTemperature(){
	return temperature;
	}
public float getUncertainty(){
	return uncertainty;
	}
public String getDate(){
	return date;
	}
public String getCity(){
	return city;
	}
public String getCountry(){
	return country;
	}
public void setUncertainty(float uncertainty){
	this.uncertainty = uncertainty;
	}
public void setTemperatur(float temperature){
	this.temperature = temperature;
	}
public String getYearAsString(){
	return year;	
}
public String getUncertainityAsString(){
	return Float.toString(uncertainty);	
}
public String getTempAsString(){
	return Float.toString(temperature);	
}
public String getLongitudeAsString(){
	return Float.toString(longitude);	
}
public String getLatitudeAsString(){
	return Float.toString(latitude);	
}
}


