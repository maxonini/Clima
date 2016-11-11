package com.climattention.client;


public class Datapoint {

private String date;
private int year;
private String country;
private String city;
private float temperature;
private float avgTemperatur;
private float uncertainty;
private float longitude;
private float latitude;

public Datapoint(String date,int year,float temperature,float uncertainty,String city,String country,float latitude,float longitude){
	this.date= date; 
	this.year=year;
	this.temperature = temperature;
	this.uncertainty = uncertainty;
	this.country=country;
	this.city= city;
	this.longitude = longitude;
	this.latitude = latitude;		
	}
public int getYear(){
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
}


