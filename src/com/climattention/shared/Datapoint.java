package com.climattention.shared;

import java.io.Serializable;

public class Datapoint implements Serializable {
	
	public Datapoint(){}

	private static final long serialVersionUID = 1L;
	
	private String date;
	private String year;
	private String country;
	private String city;
	private float temperature;
	private float avgTemperature;
	private float uncertainty;
	private String longitude;
	private String latitude;

	public Datapoint(String date,float temperature,float uncertainty,String city,String country,String latitude,String longitude){
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
	public int getYearAsInt(){
		return Integer.valueOf(date.substring(0, 4));
	}
	public String getLongitude(){
		return longitude;
		}
	public String getLatitude(){
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

}



