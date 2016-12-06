package com.climattention.server;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import com.climattention.client.GreetingService;
import com.climattention.shared.Datapoint;
import com.climattention.shared.Sorter;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;


/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class GreetingServiceImpl extends RemoteServiceServlet implements GreetingService {

		@Override
		public Datapoint[] getClimateData(Sorter sorter) {
			System.out.println("testGetclimateData");
			//create Datalist with imported data
			List<Datapoint> dataList = (List<Datapoint>)this.getServletContext().getAttribute("climateData");
			System.out.println("testGetclimateData2");			
			//create List with filtered data
			ArrayList<Datapoint> finishedData  = new ArrayList<Datapoint>();
			
			//check whether sorter is set
			if(sorter.isEmpty()){
				System.out.println("Filter is empty! hehehehehe");
				return dataList.toArray(new Datapoint[0]);
			}

			for (Datapoint data : dataList){
				
				if(isSorterMatching(data, sorter)){
					finishedData.add(data);
				}
				
			}
			System.out.println("Data is miaow sorted!");
			return finishedData.toArray(new Datapoint[0]);
					
		}
		
		private boolean isSorterMatching(Datapoint data, Sorter sorter){
			
			//checks if uncertainty is within bound
			if(data.getUncertainty() <= sorter.getMaxUncert()|| sorter.getMaxUncert() == -1){
				//check for city
				if(data.getCity() == sorter.getCity()|| sorter.getCity() == null){
					//check for country
					if(data.getCountry() == sorter.getCountry() || sorter.getCountry() == null){
						//check year
						if(data.getYearAsInt() <= sorter.getEndYear()&& data.getYearAsInt() >= sorter.getStartYear()){
							return true;
						}
						else return false;
					}
					else return false;
				}
				else return false;
			}
			else return false;
		}



	
	
	
}
