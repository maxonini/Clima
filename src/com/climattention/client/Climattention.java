package com.climattention.client;


import java.util.ArrayList;

import com.climattention.shared.AverageData;
import com.climattention.shared.Datapoint;
import com.climattention.shared.Sorter;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.kiouri.sliderbar.client.solution.simplehorizontal.SliderBarSimpleHorizontal;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Climattention implements EntryPoint {
	
	public Climattention(){}
	
	private static final double INIT_MIN_UNCERTAIN = 0.0;
	private static final double INIT_MAX_UNCERTAIN = 0.5;
	private static final int STARTING_YEAR = 2000;
	
	private  FlowPanel sliderPanel = new  FlowPanel();

	
	private VerticalPanel mapPanel = new VerticalPanel();
	private VerticalPanel tablePanel = new VerticalPanel();
    private VerticalPanel mainPanel;
	private Map myMap = new Map();
	private MyTable myTable = new MyTable();
	private ClimaServiceAsync climaService = GWT.create(ClimaService.class);
	private GreetingServiceAsync greetService = GWT.create(GreetingService .class);
	private ArrayList<Sorter> sorters= new ArrayList<Sorter>();
	//private FlexTable currentDisplay;
	
	TextBox uncertaintyFrom;
	TextBox uncertaintyTo;
	TextBox yearFrom;
	TextBox yearTo;
	SuggestBox countryName;
	SuggestBox cityName;
	
	@Override
	public void onModuleLoad() {
		
		// SliderGenerierung
//		final SliderBarSimpleHorizontal slider = new SliderBarSimpleHorizontal(163, "80%", true);
//		slider.drawMarks("grey", 100);
//		
//		Label sliderYear1850=new Label("************1850 ** 1860 ** 1870 ** 1880 ** 1890 ** 1900 ** 1910 ** 1920 ** 1930 ** 1940 ** 1950 ** 1960 ** 1970 ** 1980 ** 1990 ** 2000 ** 2013************");
//		
//		final Label sliderYear = new Label(String.valueOf(slider.getValue()+1850));
//		
//		Button addSliderButton = new Button("Aktualisieren");
//		addSliderButton.addClickHandler(new ClickHandler() {
//			@Override
//			public void onClick(ClickEvent event){
//				sliderYear.setText(String.valueOf(slider.getValue()+1850));
//			}
//		});
//		
//		
//		sliderPanel.add(slider);
//		sliderPanel.add(sliderYear1850);
//		
//		sliderPanel.add(addSliderButton);
//		
//	    sliderPanel.add(sliderYear);
		
		
		
		// sorter for filtration of table
		Window.alert("   1   On module load finished.");	
		createStartingSorters();
		Window.alert(" 2   On module load finished.");
		reloadTable();
		Window.alert("On module load finished.");
		createUserInterface();
		// data point for testing
	
		
		Window.alert("On module load finished.");
		
	}
	
	/**
	 * Initialize the default sort factors
	 */
	private void createStartingSorters(){
		Sorter sort = new Sorter();
		sort.setMaxUncert(INIT_MAX_UNCERTAIN);
		//sort.setMinUncert(INIT_MIN_UNCERTAIN);
		sort.setStartYear(STARTING_YEAR);
		sort.setEndYear(STARTING_YEAR);
		sorters.add(sort);
		
		
		
	}
	
	

	private void createUserInterface(){
	
		mainPanel = new VerticalPanel();
		mainPanel.setWidth("75%");
		
		/*attach the main panel to the root panel*/
		RootPanel.get().add(mainPanel);
	

		/**
		 * Create the tab panels of UI
		 * 
		 */
		//Create the tab panel which is contained in the main vertical panel
		TabPanel tabPanel = new TabPanel();
		mainPanel.add(tabPanel);
	
		//Create two tabs of the tab panel to switch between the map/table view 
		VerticalPanel mapViewLayout = new VerticalPanel();
		VerticalPanel tableViewLayout = new VerticalPanel();
		
		tabPanel.add(tableViewLayout, "Table View");
		tabPanel.add(mapViewLayout, "Map View");
		tabPanel.setWidth("90%");
		
		
	
		//use the first tab as default 
		tabPanel.selectTab(0);
		
		
		//Creating MapView Layout
		mapViewLayout = createMap(mapViewLayout);
		
		
		//Window.alert("Test After CreateMap");
		
		
		//Creating TableViewLayout
		tableViewLayout= createTable(tableViewLayout);
		

	
		//Create an anchor to show the link to the external source
		Anchor sourceAnchor = new Anchor("Data source ", "http://www.ifi.uzh.ch/en/rerg/courses/hs16/se.html");
		sourceAnchor.setStyleName("source");
		
	
		//Create vertical panel to show the data source and the link one over another and add the label and the anchor to it 
		VerticalPanel sourcePanel = new VerticalPanel();
		sourcePanel.add(sourceAnchor);

	
		//Add source panel to the main panel
		mainPanel.add(sliderPanel);
		mainPanel.add(sourcePanel);
	
	}

		/**
	 * 
	 * Takes the map panel, sets up the RPC with the default year
	 * Should get the year from the Slider
	 * 
	 * @param 
	 
	 * @return vertMap
	 */
	private VerticalPanel createMap(VerticalPanel vertMap){
		
		//Load map with the default startingYear
		//TODO: Connect slider
		reloadMap(STARTING_YEAR);
		
		
		Label mapLabel = new Label("Map");
		mapLabel.setStyleName("titleLabel");
		vertMap.add(mapLabel);
		
		HorizontalPanel showMap = new HorizontalPanel();
		showMap.add(mapPanel); 
		showMap.setSpacing(30);
		
		
		

		FlexTable viewMap = new FlexTable();
		
		viewMap.setWidget(0,0,mapPanel); 
		viewMap.getColumnFormatter().setWidth(1, "100px");
		viewMap.getColumnFormatter().setWidth(2, "100px");

		viewMap.getCellFormatter().setAlignment(0, 1, HasHorizontalAlignment.ALIGN_RIGHT,HasVerticalAlignment.ALIGN_TOP);
		viewMap.getCellFormatter().setAlignment(0, 2, HasHorizontalAlignment.ALIGN_RIGHT,HasVerticalAlignment.ALIGN_TOP);
		
		vertMap.add(viewMap);
		
		return vertMap;
		
	
	}
	
	/**
 * 
 * 
 * Sets up the Table panel, including all checkBoxes and TextFields
 * needed for sorting data.
 * 
 * 
 * @param tableViewLayout
 * @return tableViewLayout
 */
	private VerticalPanel createTable(VerticalPanel tableViewLayout){
		
		// Create Horizontal Customize Table
		HorizontalPanel customizePanel = new HorizontalPanel();
		customizePanel.setStyleName("paddedHorizontalPanel");
		
		//Create check-box widgets to select attributes of table 
		CheckBox showCountry = new CheckBox("show country");
		CheckBox showCity = new CheckBox("show city");
		CheckBox showDate = new CheckBox("show date");
		CheckBox showTemperature = new CheckBox("show temperature");
		CheckBox showUncertainty = new CheckBox("show uncertainty");
		CheckBox showLongitude= new CheckBox("show longitude");
		CheckBox showLatitude = new CheckBox("show latitude");
		CheckBox showAvg = new CheckBox("show average");
		CheckBox showMax = new CheckBox("show maximum");

		//add check-box widgets to the customize panel
		customizePanel.add(showCountry);
		customizePanel.add(showCity);
		customizePanel.add(showDate);
		customizePanel.add(showTemperature);
		customizePanel.add(showUncertainty);
		customizePanel.add(showLongitude);
		customizePanel.add(showLatitude);
		/*customizePanel.add(showAvg); 
		customizePanel.add(showMax); 
		customizePanel.add(showMin); */

		//set default values for each attribute
		showCountry.setValue(true); 
		showCity.setValue(true); 
		showDate.setValue(true); 
		showTemperature.setValue(true); 
		showUncertainty.setValue(true); 
		showLongitude.setValue(true);
		showLatitude.setValue(true);
		showAvg.setValue(true);
		showMax.setValue(true);
		
	
		//Create horizontal panel for the filter options (filters for location, temperature and precision)
		HorizontalPanel filterPanel = new HorizontalPanel();
		filterPanel.setStyleName("paddedHorizontalPanel");
		filterPanel.setSpacing(25);

		// Create vertical panel for the city and country filters
		VerticalPanel locationFilter = new VerticalPanel();

		//Create country filter panel
		HorizontalPanel countryFilter = new HorizontalPanel();

		Label countryLabel = new Label("Select country");
		countryLabel.setWidth("100px");
		countryLabel.setStyleName("filterLabel");

		countryName = new SuggestBox();
		Button addCountryButton = new Button("Add");
		addCountryButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event){
				addCountryNameSorter();
			}
		});

		// Assemble countryFilter panel
		countryFilter.add(countryLabel);
		countryFilter.add(countryName);
		countryFilter.add(addCountryButton);

		// Create city filter
		HorizontalPanel cityFilter = new HorizontalPanel();

		Label cityLabel = new Label("Select city");
		cityLabel.setWidth("100px");
		cityLabel.setStyleName("filterLabel");

		cityName = new SuggestBox();
		Button addCityButton = new Button("Add");
		addCityButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event){
				addCityNameSorter();
			}
		});

		// Assemble cityFilter panel
		cityFilter.add(cityLabel);
		cityFilter.add(cityName);
		cityFilter.add(addCityButton);


		//Assemble country filter panel
		locationFilter.add(countryFilter);
		locationFilter.add(cityFilter);


		// Create year range filter
		VerticalPanel yearRangeFilter = new VerticalPanel();

		HorizontalPanel yearFromFilter = new HorizontalPanel();

		Label yearFromLabel = new Label("Year from:");
		yearFromLabel.setStyleName("filterLabel");
		yearFromLabel.setWidth("100px");

		yearFrom = new TextBox();
		Button addYearFromButton = new Button("Add");
		addYearFromButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event){
				addYearFromSorter();
			}
		});

		yearFromFilter.add(yearFromLabel);
		yearFromFilter.add(yearFrom);
		yearFromFilter.add(addYearFromButton);

		HorizontalPanel yearToFilter = new HorizontalPanel();

		Label yearToLabel = new Label("Year to:");
		yearToLabel.setStyleName("filterLabel");
		yearToLabel.setWidth("100px");

		yearTo = new TextBox();
		Button addYearToButton = new Button("Add");
		addYearToButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event){
				addYearToSorter();
			}
		});

		yearToFilter.add(yearToLabel);
		yearToFilter.add(yearTo);
		yearToFilter.add(addYearToButton);

		// Assemble year range filter panel
		yearRangeFilter.add(yearFromFilter);
		yearRangeFilter.add(yearToFilter);


		// Create uncertainty filter
		VerticalPanel uncertaintyFilter = new VerticalPanel();

		/*HorizontalPanel uncertaintyFromFilter = new HorizontalPanel();

		Label uncertaintyFromLabel = new Label("Uncertainty from:");
		uncertaintyFromLabel.setStyleName("filterLabel");
		uncertaintyFromLabel.setWidth("120px");

		uncertaintyFrom = new TextBox();
		uncertaintyFrom.setText(Double.toString(INIT_MIN_UNCERTAIN));
		Button addUncertaintyFromButton = new Button("Add");
		addUncertaintyFromButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event){
				addUncertaintyFromSorter();
			}
		});

		uncertaintyFromFilter.add(uncertaintyFromLabel);
		uncertaintyFromFilter.add(uncertaintyFrom);
		uncertaintyFromFilter.add(addUncertaintyFromButton);*/

		HorizontalPanel uncerataintyToFilter = new HorizontalPanel();

		Label uncertaintyToLabel = new Label("Maximum Uncertainty:");
		uncertaintyToLabel.setStyleName("filterLabel");
		uncertaintyToLabel.setWidth("160px");

		uncertaintyTo = new TextBox();
		uncertaintyTo.setValue(Double.toString(INIT_MAX_UNCERTAIN));
		Button addUncertaintyToButton = new Button("Add");
		addUncertaintyToButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event){
				addUncertaintyToSorter();
			}
		});



		uncerataintyToFilter.add(uncertaintyToLabel);
		uncerataintyToFilter.add(uncertaintyTo);
		uncerataintyToFilter.add(addUncertaintyToButton);

		// Assemble uncertainty filter panel
		//uncertaintyFilter.add(uncertaintyFromFilter);
		uncertaintyFilter.add(uncerataintyToFilter);

		//Creates the button that resets the filters
		Button resetFilterButton = new Button("Reset filter");
		resetFilterButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				//filters.clear();
			}
		});
		
		//Assemble filter panel
		filterPanel.add(locationFilter);
		filterPanel.add(yearRangeFilter);
		filterPanel.add(uncertaintyFilter);
		filterPanel.add(resetFilterButton);
		
		/**
		 * Create horizontal panel to place the CellTable with climate data into it
		 * 
		 * */

		Label tableLabel = new Label("Climate Data Table");
		tableLabel.setStyleName("centered");
		
	
		HorizontalPanel tableView = new HorizontalPanel();
			
		Button exportCSV = new Button("Export as CSV");
		HorizontalPanel exportPanel = new HorizontalPanel();
		exportPanel.add(exportCSV);
		exportPanel.setStyleName("exportCSV");
		
		tableView.add(tablePanel);
		tablePanel.setSpacing(30);
		tableView.add(exportPanel);


		tableViewLayout.add(tableView);
		
		Label customizeLabel = new Label("Customize table ");
		customizeLabel.setStyleName("centered");
		tableViewLayout.add(customizeLabel);
		tableViewLayout.add(customizePanel);

		Label filterLabel = new Label("Filter data ");
		filterLabel.setStyleName("centered");
		tableViewLayout.add(filterLabel);
		tableViewLayout.add(filterPanel);
		
		
		tableViewLayout.add(tableLabel);
		tableViewLayout.add(tableView);
		
		
		return tableViewLayout;
	}


	/**
	 * 
	 * Creates the RPC for the data, gets the whole data since there are no
	 * sorting options yet
	 * 
	 */
	private void reloadTable() {
		if (greetService==null) {
			greetService = GWT.create(GreetingService. class);
		}
		
		//set up callback object
		AsyncCallback<Datapoint[]> callback = new AsyncCallback<Datapoint[]>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("RPC failed");
			}

			@Override
			public void onSuccess(Datapoint[] result) {
				Window.alert("RPC successfull");
				myTable.reloadData(result);
				myTable.getVis(tablePanel);
			}

		};
		
		greetService.getClimateData(sorters.toArray(new Sorter[0]), callback);
	}
	
	
	/**
	 * Creates the RPC for the map data, gets the data avaiable for
	 * the year given as parameter
	 * 
	 * @param year
	 */
	private void reloadMap(int year){
		
		climaService.getAverageForYear(year,new AsyncCallback<AverageData[]>() {

			@Override
			public void onFailure(Throwable caught) {
				
				
			}

			@Override
			public void onSuccess(AverageData[] result) {
				myMap.reloadData(result);
				myMap.getVis(mapPanel);
				
			}
		} );
		
		
	}
	
	
	
	/** 
	 * generates a new filter by adding the value "UncertaintyFrom" set in textbox as minUncertainty to sorters 
	 * works only if number is entered
	*/
/*	
	private void addUncertaintyFromSorter(){
		if(uncertaintyFrom.getText()!= null) {
			Sorter newSorter;
			if(sorters.size() == 0) {
				newSorter = new Sorter();
				newSorter.setMinUncert(Double.parseDouble(uncertaintyFrom.getText()));
				sorters.add(newSorter);
			} else {
				newSorter = sorters.get(0);	
				newSorter.setMinUncert(Double.parseDouble(uncertaintyFrom.getText()));
			}
			reloadTable();
		} 
		
		
	}*/
	
	/** 
	 * generates a new filter by adding the value "UncertaintyTo" set in textbox as maxUncertainty to sorters 
	*/
	private void addUncertaintyToSorter(){
		if(uncertaintyTo.getText() != null) {
			Sorter newSorter;
			if(sorters.size() == 0) {
				newSorter = new Sorter();
				newSorter.setMaxUncert(Double.parseDouble(uncertaintyTo.getText()));
				sorters.add(newSorter);
			} else {
				newSorter = sorters.get(0); 
				newSorter.setMaxUncert(Double.parseDouble(uncertaintyTo.getText()));
			}
			reloadTable();
		}
		
	}
	
	/** 
	 * generates a new filter by adding the value "YearFrom" set in textbox to sorters 
	*/
	private void addYearFromSorter(){
		if(yearFrom.getText() != null) {
			Sorter newSorter;
			if(sorters.size()== 0){
				newSorter = new Sorter();
				newSorter.setStartYear(Integer.parseInt(yearFrom.getText()));
				sorters.add(newSorter);
			} else {
				newSorter = sorters.get(0);
				newSorter.setStartYear(Integer.parseInt(yearFrom.getText()));
			}
			reloadTable();
			
		}
		
		
	}	
	
	/** 
	 * generates a new filter by adding the value "YearTo" set in textbox to sorters 
	*/
	private void addYearToSorter(){
		if (yearTo.getText()!=null) {
			Sorter newSorter;
			if (sorters.size()==0) {
				newSorter = new Sorter();
				newSorter.setEndYear(Integer.parseInt(yearTo.getText()));
				sorters.add(newSorter);
			} else {
				newSorter = sorters.get(0);
				newSorter.setEndYear(Integer.parseInt(yearTo.getText()));
			}
			reloadTable();
}
		
	}
	
	/** 
	 * generates a new filter by adding the value "cityName" set in textbox to sorters 
	*/
	
	private void addCityNameSorter(){
		if (cityName.getText()!=null) {
			Sorter newFilter = new Sorter();
			newFilter.setCity(cityName.getText());
			sorters.add(newFilter);
			reloadTable();
}
		
	}
	
	/** 
	 * generates a new filter by adding the value "countryName" set in textbox to sorters 
	*/
	private void addCountryNameSorter(){
		if (countryName.getText()!=null) {
			Sorter newFilter = new Sorter();
			newFilter.setCountry(countryName.getText());
			sorters.add(newFilter);
			reloadTable();
}
	
	}
	
	
	
	
	
	
}
	
