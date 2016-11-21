package com.climattention.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class DataQuery implements IsSerializable{

	private String date;
	private int year;
	private String country;
	private String city;
	private float temperature;
	private float avgTemperatur;
	private float uncertainty;
	private float longitude;
	private float latitude;
	private int limit;
	private SortColumn sortColumn;
	private boolean ascending;
	

	public boolean isAscending() {
		return ascending;
	}
	
	public void setLimit(int l){
		limit = l;
	}
	public int getLimit(){
		return limit;
	}
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public float getTemperature() {
		return temperature;
	}
	public void setTemperature(float temperature) {
		this.temperature = temperature;
	}
	public float getAvgTemperatur() {
		return avgTemperatur;
	}
	public void setAvgTemperatur(float avgTemperatur) {
		this.avgTemperatur = avgTemperatur;
	}
	public float getUncertainty() {
		return uncertainty;
	}
	public void setUncertainty(float uncertainty) {
		this.uncertainty = uncertainty;
	}
	public float getLongitude() {
		return longitude;
	}
	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}
	public float getLatitude() {
		return latitude;
	}
	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}

	public SortColumn getSortColumn() {
		return sortColumn;
	}
	
	
	
	
}
