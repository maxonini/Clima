package com.climattention.server;

import static org.junit.Assert.*;

import org.junit.Test;

public class DatapointSimpleTest {

	private final String c = "France";
	private final String c1 = "USA";
	private final int y = 2011;
	private DatapointSimple d1 = new DatapointSimple(c,y);
	private DatapointSimple d2 = new DatapointSimple(c1,y);
	private final String result = "CountryYear [country=France, year=2011]";

	
	@Test
	public void testToString() {
		  String actual = d1.toString();
		
		assertEquals(actual,result);
	}
	
	@Test
	public void testEquals() {
		
		assertTrue(!d2.equals(d1));
	}

}
