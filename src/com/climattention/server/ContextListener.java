package com.climattention.server;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.climattention.shared.Datapoint;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.client.Window;

public class ContextListener implements ServletContextListener {

	private static ServletContext context;
	
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		
		Window.alert("context Initialized");
		System.out.print("context Initialized");
		GWT.log("Context Initialized	");
		
		context= arg0.getServletContext();
		ServletContextHold.getInstance(context);
		
		List<Datapoint> myData= new ArrayList<Datapoint>(); 
		
		CSVRead reader = new CSVRead();
		reader.readCSV("resources/GlobalLandTemperaturesByMajorCity_v1.csv");
		myData = (ArrayList<Datapoint>) reader.parseData();
		
		arg0.getServletContext().setAttribute("myData", myData);

	}
	
	public static ServletContext getContext(){
		return context;
	}

}
