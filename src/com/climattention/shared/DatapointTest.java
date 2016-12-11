package com.climattention.shared;

import static org.junit.Assert.*;

import org.junit.Test;

public class DatapointTest {

	private Datapoint d1 = new Datapoint("1995.1.1", 3f ,0.03f, "Paris", "France","1000f","2008f");
	
	//checks conversion of year form string to int 
	@Test
	public void testGetYearAsInt() {
		int year = d1.getYearAsInt();
		int actual = 1995;
		
		assertEquals(year, actual);
	}
	
	//checks a setter method
	@Test
	public void testSetUncertainty() {
		float uncOld = d1.getUncertainty();
		
		d1.setUncertainty(1f);
		float uncNew = d1.getUncertainty();
		
		assertNotEquals(uncNew,uncOld);
		
	}

}
