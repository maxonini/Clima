package com.climattention.client;


import java.util.ArrayList;

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
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Climattention implements EntryPoint {
	
	public Climattention(){}
	
	private static final double INIT_MIN_UNCERTAIN = 0.0;
	private static final double INIT_MAX_UNCERTAIN = 0.5;
	
	private VerticalPanel mapPanel = new VerticalPanel();
	private VerticalPanel tablePanel = new VerticalPanel();
    private VerticalPanel mainPanel;
	private Map myMap = new Map();
	private MyTable myTable = new MyTable();
	private GreetingServiceAsync greetService = GWT.create(GreetingService .class);
	private ArrayList<Sorter> sorters= new ArrayList<Sorter>();
	private FlexTable currentDisplay;
	
	TextBox uncertaintyFrom;
	TextBox uncertaintyTo;
	TextBox yearFrom;
	TextBox yearTo;
	SuggestBox countryName;
	SuggestBox cityName;
	
	@Override
	public void onModuleLoad() {
		
		
		createUserInterface();
		
		
		Sorter sort = new Sorter();
		sort.setMaxUncert(INIT_MAX_UNCERTAIN);
		sorters.add(sort);
		reloadTable();
		
		
		
		Datapoint myData1[] = new Datapoint[1];
		Datapoint testData1 = new Datapoint("22-12-2015", 34, 34, "chur", "ch", 456, 5678);
		
		
		
		myData1[0] = testData1;
		
		Window.alert("Test 1");
		
		
		myMap = new Map();
		myMap.reloadData(myData1);
		
		myMap.getVis(mapPanel);
		
		Window.alert("On module load finished");
		
	}
	

	private void createUserInterface(){
	
	mainPanel = new VerticalPanel();
	mainPanel.setWidth("100%");
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

	tabPanel.add(mapViewLayout, "Map View");
	tabPanel.add(tableViewLayout, "Table View");
	tabPanel.setWidth("90%");
	
	

	//use the first tab as default 
	tabPanel.selectTab(0);
	
	Window.alert("Test CreateUserInterface");
	
	//Creating MapView Layout
	mapViewLayout = createMap(mapViewLayout);
	
	
	Window.alert("Test After CreateMap");
	
	
	//Creating TableViewLayout
	tableViewLayout= createTable(tableViewLayout);
	
	Label dataSourceLabel = new Label("Source of raw data: Berkeley Earth");
	dataSourceLabel.setStyleName("sourceLabel");

	//Create an anchor to show the link to the external source
	Anchor sourceAnchor = new Anchor("http://www.berkeleyearth.org", "http://www.berkeleyearth.org");
	sourceAnchor.setStyleName("sourceLabel");
	
	
	
	//Create label to show the last update of the data source
	Label updateSourceLabel = new Label("Last data update: 01.08.2013"); //do we have to to mention last date of measurement or last upload of the csv file?
	updateSourceLabel.setStyleName("sourceLabel");

	//Create vertical panel to show the data source and the link one over another and add the label and the anchor to it 
	VerticalPanel sourcePanel = new VerticalPanel();
	sourcePanel.add(dataSourceLabel);
	sourcePanel.add(sourceAnchor);
	sourcePanel.add(updateSourceLabel);

	//Add source panel to the main panel
	mainPanel.add(sourcePanel);
	
	}

	
	private VerticalPanel createMap(VerticalPanel Vertmap){
		
		
		
		
		
		Label mapLabel = new Label("Map");
		mapLabel.setStyleName("titleLabel");
		Vertmap.add(mapLabel);
		
		

		HorizontalPanel showMap = new HorizontalPanel();
		showMap.add(mapPanel); 
		showMap.setSpacing(30);
		
		
		
		Vertmap.add(showMap);
		
		Window.alert("Test CreateMap");
		
		return Vertmap;
		
	
	}
	


	
	
private VerticalPanel createTable(VerticalPanel tableViewLayout){
		
		
		/** Create Horizontal Customize Table
		 * 
		 */
		HorizontalPanel customizePanel = new HorizontalPanel();
		customizePanel.setStyleName("paddedHorizontalPanel");
		customizePanel.setSpacing(25);

		//Create check-box widgets to select attributes
		CheckBox showCountry = new CheckBox("show country");
		CheckBox showCity = new CheckBox("show city");
		CheckBox showDate = new CheckBox("show date");
		CheckBox showTemperature = new CheckBox("show temperature");
		CheckBox showUncertainty = new CheckBox("show uncertainty");
		CheckBox showLongitude= new CheckBox("show longitude");
		CheckBox showLatitude = new CheckBox("show latitude");
		CheckBox showAvg = new CheckBox("show average");
		CheckBox showMax = new CheckBox("show maximum");
		CheckBox showMin = new CheckBox("show minimum");

		//add check-box widgets to the customize panel
		customizePanel.add(showCountry);
		customizePanel.add(showCity);
		customizePanel.add(showDate);
		customizePanel.add(showTemperature);
		customizePanel.add(showUncertainty);
		customizePanel.add(showLongitude);
		customizePanel.add(showLatitude);
		customizePanel.add(showAvg); 
		customizePanel.add(showMax); 
		customizePanel.add(showMin); 

		//set default values for each attribute
		showCountry.setValue(true); 
		showCity.setValue(true); 
		showDate.setValue(true); 
		showTemperature.setValue(true); 
		showUncertainty.setValue(true); 
		showLongitude.setValue(true);
		showLatitude.setValue(true);
		showAvg.setValue(false);
		showMax.setValue(false);
		showMin.setValue(false);
		
		/**
		 * Create Filter Data Table
		 */
		/*Create horizontal panel for the filter options (filters for location, temperature and precision)*/

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

		HorizontalPanel uncertaintyFromFilter = new HorizontalPanel();

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
		uncertaintyFromFilter.add(addUncertaintyFromButton);

		HorizontalPanel uncerataintyToFilter = new HorizontalPanel();

		Label uncertaintyToLabel = new Label("Uncertainty to:");
		uncertaintyToLabel.setStyleName("filterLabel");
		uncertaintyToLabel.setWidth("120px");

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
		uncertaintyFilter.add(uncertaintyFromFilter);
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
		 * 
		 * Create horizontal panel to place the CellTable with climate data into it
		 * 
		 * */

		Label tableLabel = new Label("Climate Data Table");
		tableLabel.setStyleName("titleLabel");
		
	
		HorizontalPanel tableView = new HorizontalPanel();
			
		Button exportCSV = new Button("Export as CSV");
		exportCSV.setWidth("120px");
		
		tableView.add(tablePanel);
		tablePanel.setSpacing(30);
		tableView.add(exportCSV);


		tableViewLayout.add(tableView);
		
		
		/**
		 * Assemble the whole table view panel 
		 * 
		 * */

		Label customizeLabel = new Label("Customize table ");
		customizeLabel.setStyleName("panelLabel");
		tableViewLayout.add(customizeLabel);
		tableViewLayout.add(customizePanel);

		Label filterLabel = new Label("Filter data ");
		filterLabel.setStyleName("panelLabel");
		tableViewLayout.add(filterLabel);
		tableViewLayout.add(filterPanel);
		
		
		tableViewLayout.add(tableLabel);
		tableViewLayout.add(tableView);
		
		
		return tableViewLayout;
	}


	
	private void reloadTable() {
		if (greetService==null) {
			greetService = GWT.create(GreetingService. class);
		}
		
		//set up callback object
		AsyncCallback<Datapoint[]> callback = new AsyncCallback<Datapoint[]>() {

			@Override
			public void onFailure(Throwable caught) {}

			@Override
			public void onSuccess(Datapoint[] result) {
				myTable.reloadData(result);
				myTable.getVis(tablePanel);
			}

		};
		
		greetService.getDatapoints(sorters.toArray(new Sorter[0]), callback);
	}
	
	
	private void addUncertaintyFromSorter(){
		
		
	}
	private void addUncertaintyToSorter(){
		
		
	}
	private void addYearToSorter(){
		
		
	}
	private void addCityNameSorter(){
	
	
	}
	private void addYearFromSorter(){
		
		
	}
	private void addCountryNameSorter(){
	
	
	}
	
	
	
	
	
	
}
	