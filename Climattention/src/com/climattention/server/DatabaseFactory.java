package com.climattention.server;

public class DatabaseFactory {

	// private static Database instance = new FileBasedDatabase(new
	// TsvFileMovieDataProvider(Thread.currentThread()
	// .getContextClassLoader().getResourceAsStream("movies_80000.tsv")));

	private static Database instance = new FileBasedDatabase(new SerializedFileDataProvider(Thread.currentThread()
			.getContextClassLoader().getResourceAsStream("movies_80000.serialized")));

	/**
	 * Returns the database instance.
	 * 
	 * @return the database instance
	 */
	public static Database getInstance() {
		return instance;
	}
}
