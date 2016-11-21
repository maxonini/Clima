package com.climattention.server;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

import com.climattention.shared.DataQuery;
import com.climattention.shared.DataQueryResult;
import com.climattention.shared.Datapoint;
import com.climattention.shared.SortColumn;
import com.climattention.shared.Comparation.DataPointComparator;
import com.google.appengine.api.datastore.DatastoreApiHelper;
import com.google.gwt.thirdparty.guava.common.base.Predicate;
import com.google.gwt.thirdparty.guava.common.collect.FluentIterable;



public class FileBasedDatabase implements Database {

	private static final Logger LOGGER = Logger.getLogger(FileBasedDatabase.class.getSimpleName());
	
	private final FileDataProvider dataProvider;
	
	private List<Datapoint> dataListCache = new ArrayList<>();
//	TODO Get list of all languages, countries and genre
//	private List<String> allLanguages = new ArrayList<>();
//	private List <String> allCountries = new ArrayList<>();
//	private List<String> allGenres = new ArrayList<>();
	
	public FileBasedDatabase(FileDataProvider dataProvider) {
		this.dataProvider = dataProvider;
	}
	
	@Override
	public DataQueryResult query(DataQuery query) {
		LOGGER.info("Executing query...");
		parseData();
		LOGGER.info("Data parsed");
		SortColumn sortColumn = query.getSortColumn() != null ? query.getSortColumn() : SortColumn.TITLE;
		
		List<Datapoint> filteredMovies = FluentIterable.from(dataListCache)
				.filter(cityPredicate(query.getCity()))
				.filter(yearPredicate(Integer.toString(query.getYear())))
				.filter(countryPredicate(query.getCountry()))
				.filter(datePredicate(query.getDate()))
				.filter(temperaturePredicate(Float.toString(query.getTemperature())))
				.toSortedList(sortColumn.getComparator(query.isAscending()));
		
		int limit = query.getLimit() == 0 ? Integer.MAX_VALUE : query.getLimit();
		ArrayList<Datapoint> movieListSlice = new ArrayList<>(FluentIterable.from(filteredMovies)
				.skip(query.getYear())
				.limit(limit)
				.toList());
		
		LOGGER.info("Movies filtered");
		DataQueryResult result = new DataQueryResult();
		result.setMovies(movieListSlice);
		result.setTotalMovieCount(filteredMovies.size());
		return result;
	}

	/**
	 * Parse the movie file (only once) and cache it in movieListCache
	 */
	private synchronized void parseData() {
		if(dataListCache.isEmpty()) {
			this.dataListCache.addAll(dataProvider.getMovies());
		}
	}

	private static Predicate<Datapoint> cityPredicate(final String title) {
		return new Predicate<Datapoint>() {
			/**
			 * @return true if the movie matches the given title
			 */
			@Override
			public boolean apply(Datapoint data) {
				if(title == null) {
					return true;
				}
				if(data.getCity() == null) {
					return title == null;
				}
				return data.getCity().toLowerCase().contains(title.toLowerCase());
			}
		};
	}
	private static Predicate<Datapoint> yearPredicate(final String year) {
		return new Predicate<Datapoint>() {
			@Override
			public boolean apply(Datapoint data) {
				if(year == null) {
					return true;
				}
				if(data.getYear() == 0){
					return year == null;
				}
				return Integer.toString(data.getYear()).contains((year));
			}
		};
	}

	private static Predicate<Datapoint> countryPredicate(final String country) {
		return new Predicate<Datapoint>() {
			@Override
			public boolean apply(Datapoint data) {
				if(country == null) {
					return true;
				}
				if(data.getCountry()== null){
					return country == null;
				}
				return data.getCountry().toLowerCase().contains(country.toLowerCase());
			}
		};
	}
	
	private static Predicate<Datapoint> datePredicate(final String date) {
		return new Predicate<Datapoint>() {
			@Override
			public boolean apply(Datapoint data) {
				if(date == null) {
					return true;
				}
				if(data.getDate() == null){
					return date == null;
				}
				return data.getDate().toLowerCase().contains(date.toLowerCase());
			}
		};
	}
	
	private static Predicate<Datapoint> temperaturePredicate(final String temp) {
		return new Predicate<Datapoint>() {
			@Override
			public boolean apply(Datapoint data) {
				if(temp == null) {
					return true;
				}
				if(data.getTempAsString() == null){
					return temp == null;
				}
				return data.getTempAsString().toLowerCase().contains(temp.toLowerCase());
			}
		};
	}
	

}
