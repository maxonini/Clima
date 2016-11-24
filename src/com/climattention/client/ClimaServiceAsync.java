package com.climattention.client;

import com.climattention.shared.AverageData;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ClimaServiceAsync {
	
	void getAverageForYear(int year, AsyncCallback<AverageData[]> callback);

}
