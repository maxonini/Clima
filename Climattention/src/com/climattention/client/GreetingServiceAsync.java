package com.climattention.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.climattention.shared.DataQueryResult;
import com.climattention.shared.DataQuery;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface GreetingServiceAsync {
	
	void getDataFromServer(DataQuery query, AsyncCallback<DataQueryResult> callback);
}
