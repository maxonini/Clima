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
		public Datapoint[] getDatapoints(Sorter[] sorter) {
			
			
			ServletContext context = ContextListener.getContext();
			List<Datapoint> dataList = (List<Datapoint>)this.getServletContext().getAttribute("climateData");
			
			
			ArrayList<Datapoint> finishedData  = new ArrayList<Datapoint>();
			
			
			
			return dataList.toArray(new Datapoint[0]);
					
		}



	
	
	
}
