import java.io.BufferedReader;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.climattention.shared.Datapoint;
import com.google.gwt.thirdparty.guava.common.base.Stopwatch;

public class Serializer {

	public static void main(String[] args) throws IOException,
			ClassNotFoundException {
		Serializer serializer = new Serializer();
		List<Datapoint> allData = new ArrayList<>();
		FileInputStream input = new FileInputStream(
				"resources/movies_80000.tsv");
		allData = serializer.generateDataObjects(input);
		input.close();
		for (Datapoint data: allData) {
			// System.out.println(movie.getTitle());
		}
		try {
			FileOutputStream fout = new FileOutputStream(
					"resources/movies_80000.serialized");
			ObjectOutputStream oos = new ObjectOutputStream(fout);
			for (Datapoint data: allData) {
				oos.writeObject(data);
			}
			oos.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		List<Datapoint> readData= new ArrayList<>();
		ObjectInputStream readAll = new ObjectInputStream(new FileInputStream(
				"resources/movies_80000.serialized"));
		boolean stop = false;
		try {
			while (!stop) {
				Datapoint data = (Datapoint) readAll.readObject();
				readData.add(data);
				
			}
		} catch(EOFException e) {
			// ignore
		}
		readAll.close();
		System.out.format("Done %d", readData.size());

		for (Datapoint data : readData) {
			// System.out.println(movie.getTitle());
		}
	}

	public List<Datapoint> generateDataObjects(InputStream inputStream) {
		List<Datapoint> datas = new ArrayList<>();

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

	public List<Datapoint> parse(InputStream inputStream) {
		List<Datapoint> datas = new ArrayList<>();

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

		List<String> columns = com.google.appengine.labs.repackaged.com.google.common.base.Splitter
				.on('\t').trimResults().splitToList(line);
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
		Datapoint data= new Datapoint(date, AverageTemp, Uncertainity
				, City, Country, latitude, longitude);

		return data;
	}

	/**
	 * Parse list with format "{"key1":"value1", "key2":"value2", ...}
	 */
	private List<String> parseList(String list) {
		String cleanList = list.trim().replace("{", "").replace("}", "");
		Map<String, String> keyValues = com.google.appengine.labs.repackaged.com.google.common.base.Splitter
				.on("\", \"").omitEmptyStrings().trimResults()
				.withKeyValueSeparator(':').split(cleanList);
		List<String> values = new ArrayList<>();
		for (String value : keyValues.values()) {
			values.add(value.replace("\"", "").trim());
		}
		return values;
	}

}
