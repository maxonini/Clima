package com.climattention.server;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.climattention.shared.Datapoint;
import com.javabeans.test.shared.Movie;

public class SerializedFileDataProvider implements FileDataProvider {

	private static final Logger LOGGER = Logger.getLogger(SerializedFileDataProvider.class.getSimpleName());
	private final InputStream serializedMoviesFile;

	public SerializedFileDataProvider(InputStream serializedMoviesFile) {
		this.serializedMoviesFile = serializedMoviesFile;
	}

	@Override
	public List<Datapoint> getData() {
		List<Datapoint> datas = new ArrayList<>();
		LOGGER.log(Level.INFO, "Reading serialized movies.");
		try (ObjectInputStream objectStream = new ObjectInputStream(serializedMoviesFile)) {
			try {
				while (true) {
					Datapoint data = (Datapoint) objectStream.readObject();
					datas.add(data);

				}
			} catch (EOFException e) {
				// ignore
			}
			LOGGER.log(Level.INFO, "Read " + datas.size() + " movies.");
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return datas;
	}
}
