package com.climattention.client;

import java.util.ArrayList;
import java.util.List;

import com.climattention.shared.DataQuery;
import com.climattention.shared.DataQueryResult;
import com.climattention.shared.Datapoint;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.visualizations.GeoMap;
import com.google.gwt.visualization.client.visualizations.GeoMap.Options;

import com.climattention.shared.CountryCounter;


public class Map extends VerticalPanel{
	private DataTable dataset;
	private String[] countries = {"Afghanistan","Angola","Australia","Bangladesh","Brazil","Burma","Caute D'Ivoire","Canada","Chile","China","Colombia",
			"Congo","Dominican Republic","Egypt","Ethiopia","France","Germany","India","Indonesia","Iran","Iraq","Italy","Japan","Kenya","Mexico",
			"Morocco","Nigeria","Pakistan","Peru","Philippines","Russia","Saudi Arabia","Senegal","Singapore","Somalia","South Africa","South Korea",
			"Spain","Sudan","Syria","Taiwan","Tanzania","Thailand","Turkey","Ukraine","United Kingdom","United States","Vietnam","Zimbabwe"
	};
	private CheckBox excludeUncertainData;
	private DataQuery currentQuery;
	private GreetingServiceAsync greetingService;
	private GeoMap worldMap;
	private int mapHeight;
	private int mapWidth;

	private boolean isInitialized = false;
	private List<Datapoint> dataList = new ArrayList<Datapoint>();
	private List<CountryCounter> countryList = new ArrayList<CountryCounter>();

	private int totalDataFound;
	private int totalDataVisualized;
	private Label totalDataFoundlbl;
	private Label totalDataVisualizedlbl;
	
	
	public Map(int height, int width, DataQuery currentQuery, GreetingServiceAsync GreetingService) {
		this.greetingService = GreetingService;
		this.mapHeight = height;
		this.mapWidth = width;
		this.currentQuery = currentQuery;
		worldMap = new GeoMap();
		createWorldMap();
	}
	
	// Called at Start
		public void createWorldMap() {
			updateDataList();

		}

		// Draws the Map with given Data
		public void DrawMap() {
			if (!isInitialized) {
				worldMap = new GeoMap(dataset, getMapOptions());
				this.add(worldMap);
				isInitialized = true;
			} else {
				worldMap.draw(dataset, getMapOptions());
			}
		}
		
		// Creates the Dataset for the Chart, based on the List<CountryCounter
		public void createDataSet() {
			if (!isInitialized) {
				initializeCountryList();
				// DEBUG:
				// System.out.println("initializing CountryList Successfull");
			}
			prepareDataSet();
			// DEBUG: System.out.println("prepareDataSet Successfull");

			if (!isInitialized) {
				dataset = DataTable.create();
				dataset.addColumn(ColumnType.STRING, "ADDRESS", "address");
				dataset.addColumn(ColumnType.NUMBER, "Number of Data", "number");
				dataset.addRows(251);

			}

			int i = 0;
			for (CountryCounter c : countryList) {

				dataset.setValue(i, 0, c.getCountryName());
				dataset.setValue(i, 1, c.getNumberOfData());
				i++;

			}
			DrawMap();
		}

		// Called only once, assigns to each CountryCounter the Name
		public void initializeCountryList() {
			for (String countryName : countries) {
				CountryCounter c = new CountryCounter(countryName);
				countryList.add(c);
			}

		}

		// Iterates over all Movies in the MovieList and counts them per Country
		public void prepareDataSet() {

			System.out.println("DataListSize: " + dataList.size());

			totalDataFound = 0;
			totalDataVisualized = 0;

			if (dataList.size() != 0) {
				for (Datapoint data: dataList) {
					boolean isInList = false;

					totalDataFound++;

					if (excludeUncertainData.getValue() && data.getCountry().contains("United States")) {
						/*
						 * DO NOTHING, this is to improve the maps visualization
						 * solution,if the User is not interested in US-Movies
						 */
					} else

					{
						// handling common exceptions first
						if (data.getCountry().contains("Soviet") == true) {
							for (CountryCounter c : countryList) {
								if (c.getCountryName().equals("Russia")) {
									c.increaseNumberOfData();
									isInList = true;
									break;

								}
							}

						}
						if (data.getCountry().contains("England") == true) {
							for (CountryCounter c : countryList) {
								if (c.getCountryName().equals("United Kingdom")) {
									c.increaseNumberOfData();
									isInList = true;
									break;
								}
							}

						}
						if (data.getCountry().contains("Scotland") == true) {
							for (CountryCounter c : countryList) {
								if (c.getCountryName().equals("United Kingdom")) {
									c.increaseNumberOfData();
									isInList = true;
									break;
								}
							}

						}
						if (data.getCountry().contains("German") == true) {
							for (CountryCounter c : countryList) {
								if (c.getCountryName().equals("Germany")) {
									c.increaseNumberOfData();
									isInList = true;
									break;
								}
							}

						}

						for (CountryCounter countryCounter : countryList) {
							if (data.getCountry().contains(countryCounter.getCountryName())) {
								countryCounter.increaseNumberOfData();
								isInList = true;
								break;
							} else {
								// DEBUG: System.out.println("Country not maching");
							}
						}
						totalDataVisualized++;
						if (isInList != true) {
							System.out.println("Country not in List: " + data.getCountry());
							totalDataVisualized--;
						}

					}
				}
			} else {
				System.out.println("MovieList is Empty");
			}
			totalDataFoundlbl.setText("Total Movies Found: " + totalDataFound);
			totalDataVisualizedlbl.setText("Total Movies Visualized: " + totalDataVisualized);
		}
		
		public Options getMapOptions() {

			Options options = Options.create();
			options.setDataMode(GeoMap.DataMode.REGIONS);
			options.setHeight(mapHeight);
			options.setWidth(mapWidth);
			options.setShowLegend(true);
			options.setColors(0x99FFCC, 0x73caff, 0x794dff, 0xff26e4, 0xff0000);
			options.setRegion("world");

			return options;
		}

		@Override
		protected void onLoad() {
			super.onLoad();
			this.add(worldMap);
		}
		
		// Resets the existing Lists, starts new Query
		public void UpdateWorldMap() {
			dataList.clear();

			for (CountryCounter c : countryList) {
				c.reset();
			}
			updateDataList();
		}

		// Query to the Server
		private void updateDataList() {
			currentQuery.setLimit(3000);

			if (currentQuery.getYear() == 0) {
				if (isInitialized) {
					Window.alert("You have not chosen any criteria! By default, only movies of 1930 will be shown");
				}
				currentQuery.setYear(1930);
			}

			greetingService.getDataFromServer(currentQuery, new AsyncCallback<DataQueryResult>() {
				public void onFailure(Throwable caught) {
					// DEBUG: System.out.println("Failed"+ caught.toString());
				}

				public void onSuccess(DataQueryResult result) {
					if (result.getData().size() == 0) {
						Window.alert("No movies found that match selected criteria");
					}
					// DEBUG: System.out.println("Done loading");
					dataList = result.getData();
					createDataSet();

				}
			});

		}

		// ****GETTER AND SETTER***//
		public void setCurrentQuery(DataQuery currentQuery) {
			this.currentQuery = currentQuery;
		}

		public DataQuery getCurrentQuery() {
			return currentQuery;
		}

		public Widget getWorldMap() {

			return worldMap;
		}

		public void setExcludeUS(CheckBox excludeUncertainData1) {
			this.excludeUncertainData = excludeUncertainData1;
		}

		public void setTotalDataFound(Label lbl) {
			totalDataFoundlbl = lbl;
		}

		public void setTotalDataVisualized(Label lbl) {
			totalDataVisualizedlbl = lbl;
		}

}
