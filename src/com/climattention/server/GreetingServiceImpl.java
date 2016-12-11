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
/**
 * @author Valérie
 *
 */
@SuppressWarnings("serial")
public class GreetingServiceImpl extends RemoteServiceServlet implements GreetingService {

		/* Filters the elements of the table by the filled-in fields
		 * 
		 * @pre	Sorter is not null
		 * @param sorter of type Sorter, contains all the information the user filled in the textboxes to filter data
		 * @return	returns an array of type Datapoint, filled with the filtered data
		 *
		 */
		@Override
		public Datapoint[] getClimateData(Sorter sorter) {
			
			//create Datalist with imported data
			List<Datapoint> dataList = (List<Datapoint>)this.getServletContext().getAttribute("climateData");
			
			//create List with filtered data
			ArrayList<Datapoint> finishedData  = new ArrayList<Datapoint>();
			
			//check whether sorter is set
			if(sorter.isEmpty()){
				return dataList.toArray(new Datapoint[0]);
			}
			
			for (Datapoint data : dataList){
				
				if(isSorterMatching(data, sorter)){
					finishedData.add(data);
				}
			}

			return finishedData.toArray(new Datapoint[0]);
					
		}
		
		
		/**
		 * Compares the criteria in sorter with the data and returns true if it matches
		 * @pre	data and sorter are not null
		 * @param data is compared with the criteria in @param sorter
		 * @param sorter contains criteria for filtration, filled in by the user
		 * @return boolean true is returned if the datapoint matches the criteria of sorter
		 */

		private boolean isSorterMatching(Datapoint data, Sorter sorter){
			
			//checks if uncertainty is within bound
			if(data.getUncertainty() <= (float)sorter.getMaxUncert()|| sorter.getMaxUncert() == -1){
				//check for city
				if(data.getCity().equalsIgnoreCase(sorter.getCity())|| sorter.getCity() == null){
					//check for country
					if(data.getCountry().equalsIgnoreCase(sorter.getCountry()) ||data.getCountry().equalsIgnoreCase(sorter.getSecondCountry()) || sorter.getCountry() == null){
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
