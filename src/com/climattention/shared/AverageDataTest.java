package com.climattention.shared;

import static org.junit.Assert.*;

import org.junit.Test;

public class AverageDataTest {

	private AverageData d1 = new AverageData("France", 15.20, 2011);
	
	
	// a few checks if getter and setters are working
	
	@Test
	public void testGetCountry() {
		String result = d1.getCountry();
		
		assertEquals(result, "France");
		
	}
	
	@Test public void testSetAverageTemp() {
		d1.setAverageTemp(10);
		double avgNew = d1.getAverageTemp();
	
		assertNotEquals(avgNew, 15.20, 0);
	}

}
