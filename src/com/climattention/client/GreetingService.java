package com.climattention.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.climattention.shared.Datapoint;
import com.climattention.shared.Sorter;

/**
 * The client-side stub for the RPC service.
 */
@RemoteServiceRelativePath("datapoint")
public interface GreetingService extends RemoteService {
	
	Datapoint[] getDatapoints(Sorter[] sorter);
	
	
	
}
