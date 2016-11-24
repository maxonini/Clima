package com.climattention.shared;

import java.io.Serializable;

public class AverageData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String country;
	private float averageTemp;
	private int year;
	
	
	
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public float getAverageTemp() {
		return averageTemp;
	}
	public void setAverageTemp(float averageTemp) {
		this.averageTemp = averageTemp;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	
	
	
	
	
	
}