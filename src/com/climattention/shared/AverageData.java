package com.climattention.shared;

import java.io.Serializable;

public class AverageData implements Serializable {
	
	public AverageData(){}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String country;
	private double averageTemp;
	private int year;
	
	public AverageData(String c, double t, int y){
		country = c;
		averageTemp = t;
		year = y;
	}
	
	
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public double getAverageTemp() {
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
