package com.climattention.server;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.climattention.shared.Datapoint;


public class ContextListener implements ServletContextListener {

	private static ServletContext context;
	
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		
		AverageYearCreator avgCreator = new AverageYearCreator();
		
		context= arg0.getServletContext();
		ServletContextHold.getInstance(context);
		
		System.out.print("context Initialized");
		
		List<Datapoint> myData= new ArrayList<Datapoint>(); 
		
		CSVRead reader = new CSVRead();
		//Path might be in need to get fixed, didnt work on my machine with a realtive path
		String path = "/Climattention/resources/GlobalLandTemperaturesByMajorCity_v1.csv";
		reader.readCSV(path);
		myData = (ArrayList<Datapoint>) reader.parseData();
		
		Map<Integer, List<AverageData>> averageDataMap = avgCreator.calculateAveragePerYearAndCountry(myData);
		averageDataMap = Collections.unmodifiableMap(averageDataMap);
		
		arg0.getServletContext().setAttribute(ContextContent.CLIMATE_DATA, myData);
		
		arg0.getServletContext().setAttribute(ContextContent.AVERAGE_PER_YEAR, averageDataMap);

	}
	
	public static ServletContext getContext(){
		return context;
	}
}
