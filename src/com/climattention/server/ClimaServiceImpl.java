package com.climattention.server;

import com.climattention.client.ClimaService;
import com.climattention.shared.AverageData;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;


@SuppressWarnings("serial")
public class ClimaServiceImpl extends RemoteServiceServlet implements ClimaService {

	@Override
	public AverageData[] getAverageForYear(int year) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	

}
