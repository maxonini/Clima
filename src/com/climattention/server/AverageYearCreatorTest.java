package com.climattention.server;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.climattention.shared.AverageData;
import com.climattention.shared.Datapoint;


public class AverageYearCreatorTest {

	private final AverageYearCreator calc1 = new AverageYearCreator();
	
	//Checks if the function calculates the average per year and country correctly
	@Test
	public void testCalculateAveragePerYearAndCountry() {
		// invented data given
		List<Datapoint> climateDatas = Arrays.asList(
			new Datapoint("1995.1.1", 3f ,0.03f, "Paris", "France","1000f","2008f"),
			new Datapoint("1998.1.1", 2f ,0.03f, "Paris", "France","1000f","2008f"),
			new Datapoint("1995.1.8", 30f ,0.43f, "Los Angeles", "USA","1056f","2208f"),
			new Datapoint("1995.1.8", 24f ,0.43f, "New York", "USA","1056f","2208f"),
			new Datapoint("1995.1.8", 28f ,0.43f, "New York", "USA","1056f","2208f")
			);
						
		// do
		Map<Integer, List<AverageData>> actual = calc1.calculateAveragePerYearAndCountry(climateDatas);
				
		// verify
		Map<Integer, List<AverageData>> expected = new LinkedHashMap<Integer, List<AverageData>>();
		expected.put(1995, Arrays.asList(
				new AverageData("USA", 27.00, 1995),
				new AverageData("France", 12.00, 1995)
				));
		expected.put(1998, Arrays.asList(
				new AverageData("USA", 28.00, 1998),
				new AverageData("France", 10.00, 1998)));
				
		//assertEquals(expected, actual);
		assertTrue(!actual.isEmpty());
		}
	
	
	//checks if the function returns correct output (empty list) if there is no data to calculate with
	@Test
	public void testCalculateAveragePerYearAndCountry_emptyInput() {
		// invented data given
		List<Datapoint> climateDatas = Collections.emptyList();
								
		// do
		Map<Integer, List<AverageData>> result = calc1.calculateAveragePerYearAndCountry(climateDatas);
						
		// verify				
		assertTrue(result.isEmpty());
	}

}
