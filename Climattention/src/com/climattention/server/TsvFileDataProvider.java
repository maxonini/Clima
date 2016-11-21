package com.climattention.server;

import java.io.InputStream;
import java.util.List;

import com.climattention.shared.Datapoint;
import com.javabeans.test.shared.Movie;

public class TsvFileDataProvider implements FileDataProvider {

	private final InputStream moviesTsvFile;

	public TsvFileDataProvider(InputStream moviesTsvFile) {
		this.moviesTsvFile = moviesTsvFile;
	}
	
	@Override
	public List<Datapoint> getData() {
		return new TsvParser().parse(this.moviesTsvFile);
	}
}
