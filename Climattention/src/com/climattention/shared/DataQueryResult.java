package com.climattention.shared;

import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;


public class DataQueryResult implements IsSerializable{

	private List<Datapoint> data;
	/**
	 * The total amount of data that matched the query.
	 */
	private int totalDataCount;

	public List<Datapoint> getData() {
		return data;
	}

	public void setMovies(List<Datapoint> data) {
		this.data= data;
	}

	public int getTotalDataCount() {
		return totalDataCount;
	}

	public void setTotalMovieCount(int totalDataCount) {
		this.totalDataCount = totalDataCount;
	}
	
	
	
}
