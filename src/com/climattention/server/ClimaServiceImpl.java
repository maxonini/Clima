package com.climattention.server;

import java.util.List;
import java.util.Map;

import com.climattention.client.ClimaService;
import com.climattention.shared.AverageData;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;


@SuppressWarnings("serial")
public class ClimaServiceImpl extends RemoteServiceServlet implements ClimaService {
	
	
	
	/*
	*Creates a map with the year as key. 
	*The key maps the selected year with the list of AverageData elements corresponding to that year
	*
	*@param year
	*/
	@Override
	public AverageData[] getAverageForYear(int year) {
		Map<Integer, List<AverageData>> yearToAvgData= (Map<Integer, List<AverageData>>) this.getServletContext().getAttribute(ContextContent.AVERAGE_PER_YEAR);
		AverageData[] data = new AverageData[yearToAvgData.get(year).size()];
		yearToAvgData.get(year).toArray(data);
		return data;
	}
	
	
	
	

}
