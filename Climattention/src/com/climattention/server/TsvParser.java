package com.climattention.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.climattention.shared.Datapoint;
import com.google.gwt.thirdparty.guava.common.base.Splitter;

public class TsvParser {

	public List<Datapoint> parse(InputStream inputStream) {
		List<Datapoint> datas= new ArrayList<>();

		try (BufferedReader reader = new BufferedReader(new InputStreamReader(
				inputStream))) {
			String line;
			while ((line = reader.readLine()) != null) {
				datas.add(this.parseData(line));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return datas;
	}
	
	private Datapoint parseData(String line) {

		List<String> columns = Splitter.on('\t').trimResults()
				.splitToList(line);
		// Movie movie = new Movie();
		// movie.setWikiMovieID(Long.valueOf(columns.get(0)));
		// movie.setFreebaseMovieID(columns.get(1));
		// etc.
		String date= String.valueOf(columns.get(0));
		Float AverageTemp = Float.valueOf(columns.get(1));
		Float Uncertainity = Float.valueOf(columns.get(2));
		String City = columns.get(3);
		String Country = columns.get(4);
		Float latitude= Float.valueOf(columns.get(5));
		Float longitude = Float.valueOf(columns.get(6));
		List<String> countries = parseList(columns.get(7));
		List<String> genres = parseList(columns.get(8));

		return new Datapoint(date, AverageTemp, Uncertainity, City,
				Country, latitude, longitude);
	}

	/**
	 * Parse list with format "{"key1":"value1", "key2":"value2", ...}
	 */
	private List<String> parseList(String list) {
		String cleanList = list.trim().replace("{", "").replace("}", "");
		Map<String, String> keyValues = Splitter.on("\", \"")
				.omitEmptyStrings()
				.trimResults()
				.withKeyValueSeparator(':')
				.split(cleanList);
		List<String> values = new ArrayList<>();
		for (String value : keyValues.values()) {
			values.add(value.replace("\"", "").trim());
		}
		return values;
	}
	
	
	

}
