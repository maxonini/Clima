package com.climattention.client;

import com.climattention.shared.AverageData;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("averageYear")
public interface ClimaService extends RemoteService{
	
	AverageData[] getAverageForYear(int year);

}
