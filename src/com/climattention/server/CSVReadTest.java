package com.climattention.server;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.climattention.shared.Datapoint;

public class CSVReadTest {

	private final CSVRead reader = new CSVRead();
	private List<String[]> myData2 = new ArrayList<String[]>();
	private List<Datapoint> myDataParsed = new ArrayList<Datapoint>();
	private List<Datapoint> myData = Arrays.asList(
			new Datapoint("1.1.95", 3f ,0.03f, "Paris", "France","1000f","2008f"),
			new Datapoint("1.1.98", 2f ,0.03f, "Paris", "France","1000f","2008f"),
			new Datapoint("1.8.95", 30f ,0.43f, "Los Angeles", "USA","1056f","2208f"),
			new Datapoint("1.8.95", 24f ,0.43f, "New York", "USA","1056f","2208f"),
			new Datapoint("1.8.98", 28f ,0.43f, "New York", "USA","1056f","2208f")
			);
	//path need to be adapted to your own
	private final String path = "C:/Users/Jara/Documents/GitHub/Clima/resources/GlobalLandTemperaturesByMajorCity_v1.csv";
	
	
	// checks if myData isnt empty anymore after the call of the readCSV function 
	@Test
	public void testReadCSV() {
		myData2 = reader.readCSV(path);
		
		assertTrue(!myData.isEmpty());
	}
	
	//checks if myData is parsed and myDataParsed isnt empty anymore
	@Test
	public void testParseData() {
	
		myDataParsed = reader.parseData();
		
		assertTrue(!myDataParsed.isEmpty());
	}

}
