package com.climattention.shared;

import java.io.Serializable;

public class Sorter implements Serializable{

	/**
	 * implements the functions to get and set the attributes for the filtration of the table
	 */
	private static final long serialVersionUID = 1L;
	
	
	private int startYear = -1;
	private int endYear = 1;
	private double maxUncert = -1;
	//private double minUncert = 0;
	private String city = null;
	private String country = null;
	
	public boolean isEmpty(){
		if(startYear == -1 && endYear == 1 && maxUncert == -1 && city == null && country == null){
			return true;
		}
		else return false;
	}
	
	public int getStartYear() {
		return startYear;
	}
	public void setStartYear(int startYear) {
		this.startYear = startYear;
	}
	public int getEndYear() {
		return endYear;
	}
	public void setEndYear(int endYear) {
		this.endYear = endYear;
	}
	public double getMaxUncert() {
		return maxUncert;
	}
	public void setMaxUncert(double maxUncert) {
		this.maxUncert = maxUncert;
	}
	/*public double getMinUncert() {
		return minUncert;
	}
	public void setMinUncert(double minUncert) {
		this.minUncert = minUncert;
	}*/
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	
	

	
	
	
}
