package com.climattention.client;

import static org.junit.Assert.*;

import org.junit.Test;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class MapTest {

	@Test
	public void testGetVis() {
		VerticalPanel testPanel = new VerticalPanel();
		Map test = new Map();
		Widget output = test.getVis(testPanel);
		assertEquals(testPanel, output.getWidgetCount());//not useful yet, but what should be testet? that it is not same?
	}
	
	@Test
	public void testReloadData(){
		fail("Not yet implemented");
		// maybe not necessary to test
	}

}
