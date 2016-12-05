package com.climattention.client;

import com.climattention.shared.AverageData;
import com.climattention.shared.Datapoint;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public interface Ivis {
	
	public abstract Widget getVis(final VerticalPanel vP);
	public abstract void reloadData(AverageData[] newData);
	

}
