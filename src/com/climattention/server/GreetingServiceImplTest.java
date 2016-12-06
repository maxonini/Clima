package com.climattention.server;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.climattention.client.ClimaService;
import com.climattention.client.GreetingService;
import com.climattention.client.GreetingServiceAsync;
import com.climattention.shared.Datapoint;
import com.climattention.shared.Sorter;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class GreetingServiceImplTest {

	@Test
	public void testGetClimateData() {
		
		Sorter sorter = new Sorter();
		sorter.setCountry("india");
		
		GreetingServiceAsync greetTest = GWT.create(ClimaService.class);
		assertNotNull(greetTest);
		
		AsyncCallback<Datapoint[]> callback = new AsyncCallback<Datapoint[]>() {

			@Override
			public void onFailure(Throwable caught) {
			}

			@Override
			public void onSuccess(Datapoint[] result) {
			}
		};
		
		//Datapoint[] dataOutput = greetTest.getClimateData(sorter, callback);
		//assertNotNull(dataOutput[0]);
	}

}
