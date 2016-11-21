package com.climattention.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.climattention.shared.DataQueryResult;
import com.climattention.shared.DataQuery;

/**
 * The client-side stub for the RPC service.
 */
@RemoteServiceRelativePath("greet")
public interface GreetingService extends RemoteService {
	
	
	DataQueryResult getDataFromServer(DataQuery query);
	
}
