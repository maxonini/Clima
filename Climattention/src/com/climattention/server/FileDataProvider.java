package com.climattention.server;

import java.util.List;

import com.climattention.shared.Datapoint;


public interface FileDataProvider {

	List<Datapoint> getData();
}
