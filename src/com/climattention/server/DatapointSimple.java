package com.climattention.server;

public class DatapointSimple {
	
	
	
	private final String country;
	private final int year;
	
	public DatapointSimple(String country, int year){
		this.country = country;
		this.year = year;
	}
	
	public String getCountry() {
		return country;
	}
	public int getYear() {
		return year;
	}

	@Override
	public String toString() {
		return "CountryYear [country=" + country + ", year=" + year + "]";
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((country == null) ? 0 : country.hashCode());
		result = prime * result + year;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DatapointSimple other = (DatapointSimple) obj;
		if (country == null) {
			if (other.country != null)
				return false;
		} else if (!country.equals(other.country))
			return false;
		if (year != other.year)
			return false;
		return true;
	}

}
