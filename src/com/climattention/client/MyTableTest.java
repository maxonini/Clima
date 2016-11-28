package com.climattention.client;

import static org.junit.Assert.*;

import org.junit.Test;

import com.climattention.shared.Datapoint;

public class MyTableTest {

	@Test
	public void testReloadData() {
		
		MyTable table = new MyTable();
		
		Datapoint p1 = new Datapoint("1.1.95", 37.2f ,0.03f, "Paris", "France",1000f,2008f );
		Datapoint p2 = new Datapoint("1.8.95", 36.2f ,0.43f, "New York", "USA",1056f,2208f );
		Datapoint p3 = new Datapoint("1.4.95", 33.2f ,0.3f, "London", "England",1600f,2041f );
		
		Datapoint[] places = new Datapoint[3];
		places[0] = p1;
		places[1] = p2;
		places[2] = p3;
		table.reloadData(places);
		
		Datapoint[] output = table.getData();
		assertArrayEquals(places, output);
	}

}
