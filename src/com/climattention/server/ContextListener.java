package com.climattention.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.climattention.shared.AverageData;
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
		
		URL url = CSVRead.class.getClassLoader().getResource("GlobalLandTemperaturesByMajorCity_v1.csv");
		String path = url.getPath();
		
		//String path = "climattention/resources/GlobalLandTemperaturesByMajorCity_v1.csv";
		try {
			reader.readCSV(path);
		} catch(Exception e) {
			e.printStackTrace();
		}
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
