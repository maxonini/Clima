package com.climattention.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import com.climattention.shared.Datapoint;
import com.climattention.shared.Sorter;
/**
 * The async counterpart of <code>GreetingService</code>.
 */


public interface GreetingServiceAsync {
	
	void getClimateData(Sorter sorter, AsyncCallback<Datapoint[]> callback);
}
