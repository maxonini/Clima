package com.climattention.server;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import com.climattention.client.GreetingService;
import com.climattention.shared.Datapoint;
import com.climattention.shared.Sorter;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;


/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class GreetingServiceImpl extends RemoteServiceServlet implements GreetingService {

		@Override
		public Datapoint[] getClimateData(Sorter[] sorter) {
			
			//create Datalist with imported data
			List<Datapoint> dataList = (List<Datapoint>)this.getServletContext().getAttribute("climateData");
			
			//create List with filtered data
			ArrayList<Datapoint> finishedData  = new ArrayList<Datapoint>();
			
			//check whether sorter is set
			/*if(sorter.isEmpty()){
				return dataList.toArray(new Datapoint[0]);
			}
				
			for (Datapoint data : dataList){
				
				if()
			}*/
			
			return dataList.toArray(new Datapoint[0]);
					
		}



	
	
	
}
