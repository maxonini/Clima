package com.climattention.shared;


import static org.junit.Assert.*;


import org.junit.Test;

public class SorterTest {
 
	private Sorter sorter = new Sorter();
	
	
	//checks if sorter after setting values isnt empty anymore
	@Test
	public void testIsEmpty() {
		//set the attributes of sorter (normally done by entering attributes into textboxes)
		sorter.setStartYear(2000);
		sorter.setEndYear(2016);
		sorter.setMaxUncert(0.03);
		
		assertTrue(!sorter.isEmpty());
	}

	@Test
	public void testClean() {
		int expected = -1;
		sorter.clear();
		
		int startYearSet = sorter.getStartYear();
		
		assertEquals(startYearSet, expected);
	}
}
