package com.climattention.client;

import static org.junit.Assert.*;
import org.junit.Test;

import com.climattention.shared.AverageData;
import com.climattention.shared.Datapoint;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class MapTest{
	
	public MapTest(){}

	@Test
	public void testGetVis() {
		VerticalPanel testPanel = new VerticalPanel();
		Map test = new Map();
		Widget output = test.getVis(testPanel);
		//assertEquals(testPanel, output.getWidgetCount());//not useful yet, but what should be testet? that it is not same?
	}
	
	@Test
	public void testReloadData(){
		Map map = new Map();
		
		Datapoint p1 = new Datapoint("1.1.95", 37.2f ,0.03f, "Paris", "France","1000f","2008f" );
		Datapoint p2 = new Datapoint("1.8.95", 36.2f ,0.43f, "New York", "USA","1056f","2208f" );
		Datapoint p3 = new Datapoint("1.4.95", 33.2f ,0.3f, "London", "England","1600f","2041f" );
		
		Datapoint[] places = new Datapoint[3];
		places[0] = p1;
		places[1] = p2;
		places[2] = p3;
		map.reloadData(places);
		
		Datapoint[] output = map.getData();
		assertArrayEquals(places, output);
	}
	


}
