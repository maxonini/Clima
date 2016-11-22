package com.climattention.client;


import com.climattention.shared.DataQuery;
import com.climattention.shared.DataQueryResult;
import com.climattention.shared.Datapoint;
import com.climattention.shared.SortColumn;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.cellview.client.ColumnSortEvent.AsyncHandler;
import com.google.gwt.user.cellview.client.ColumnSortList.ColumnSortInfo;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.AsyncDataProvider;
import com.google.gwt.view.client.HasData;
import com.kiouri.sliderbar.client.solution.simplehorizontal.SliderBarSimpleHorizontal;


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Climattention implements EntryPoint {
	
	private boolean isInMapMode = false;
	
	private HorizontalPanel hPanelSlider = new HorizontalPanel();
	
	private final GreetingServiceAsync greetService = GWT.create(GreetingService.class);
	
	private int startYear;
	private int endYear;
	private int currentYear;
	
	private DataQuery currentQuery = new DataQuery();
	//private ListBox dropBox = new ListBox(true);

	private VerticalPanel vPanel = new VerticalPanel();
	private ScrollPanel scrollPanelTable = new ScrollPanel();
	private VerticalPanel map = new VerticalPanel();
	private FlowPanel searchMenu = new FlowPanel();

	private HorizontalPanel city = new HorizontalPanel();
	private HorizontalPanel year = new HorizontalPanel();
	private HorizontalPanel country = new HorizontalPanel();
	private HorizontalPanel date = new HorizontalPanel();
	private HorizontalPanel temperature = new HorizontalPanel();
	private HorizontalPanel search = new HorizontalPanel();
	private HorizontalPanel upperPanel=new HorizontalPanel();
	private FlowPanel sliderPanel = new FlowPanel();

	private TextBox cityField = new TextBox();
	private TextBox yearField = new TextBox();
	private TextBox countryField = new TextBox();
	private TextBox languageField = new TextBox();
	private Button searchButton = new Button("Search");
	private Button exportButton = new Button("Export");
	private Button updateMapButton = new Button("Update Map");
	
	private Map worldMap;
	private CheckBox toggleUncertainty=new CheckBox();
	private Label toggleUncertaintylbl=new Label("Toggle Uncertainty");
	private HorizontalPanel mapOptionsPanel=new HorizontalPanel();
	private Label totalDataFound = new Label();
	private Label totalDataVisualized = new Label();
	private VerticalPanel mapInfo=new VerticalPanel();
	private HorizontalPanel toggleUncertaintyContainer=new HorizontalPanel();

	private Table table = new Table();
	
	private AsyncDataProvider<Datapoint> tableDataProvider = new AsyncDataProvider<Datapoint>() {
		@Override
		protected void onRangeChanged(HasData<Datapoint> display) {
			updateData(display.getVisibleRange().getStart(), display
					.getVisibleRange().getLength());
		}
	};
	
	private Label cityLabel = new Label("City");
	
	private TabLayoutPanel tabPanel = new TabLayoutPanel(2.5, Unit.EM);
	  
	private Label sourceLabel = new Label();
	
  /**
   * Entry point method.
   */
  public void onModuleLoad() {
	  System.out.println("Module starts loading... ");
	  
	  //Slider
	  SliderBarSimpleHorizontal slider = new SliderBarSimpleHorizontal(100, "30", true);
	  sliderPanel.add(slider);
	  
	  searchMenu.setWidth("100%");
	  upperPanel.add(searchMenu);
	  
	  city.setStyleName("flowPanel_inline");
	  search.setStyleName("flowPanel_inline");
	  searchButton.setStyleName("flowPanel_inline");
	  exportButton.setStyleName("rightTop");
	  
	  city.add(cityLabel);
	  city.add(cityField);
	  search.add(searchButton);

	  searchMenu.add(city);
	  searchMenu.add(search);
	  
	  //MAP UI ELEMENTS
	  toggleUncertaintylbl.addStyleName("mapOptionsPanelContent");
	  toggleUncertainty.addStyleName("mapOptionsPanelContent");
	  toggleUncertaintyContainer.add(toggleUncertaintylbl);
	  toggleUncertaintyContainer.add(toggleUncertainty);
	  toggleUncertaintyContainer.setStyleName("mapOptionsPanelContent");
		
	  mapInfo.add(totalDataFound);
	  mapInfo.add(totalDataVisualized);
	  mapInfo.addStyleName("mapOptionsPanelContent");
		
	  mapOptionsPanel.add(toggleUncertaintyContainer);
	  mapOptionsPanel.add(mapInfo);
		
	  mapOptionsPanel.addStyleName("mapOptionsPanel");
	  mapOptionsPanel.setWidth("300px");

	  scrollPanelTable.add(table);

	  //hPanelSlider.add(updateMapButton);
	  updateMapButton.setStyleName("rightTop");
	  
	  upperPanel.add(exportButton);
	  upperPanel.add(updateMapButton);
	  upperPanel.add(hPanelSlider);
	  upperPanel.add(mapOptionsPanel);
	  
	  for (int i = startYear; i < endYear + 1; i++) {
			String year = Integer.toString(i);
		}
	  

	  hPanelSlider.addStyleName("mapOptionsPanelContent");
	  hPanelSlider.setVisible(false);
	  updateMapButton.setVisible(false);
	  mapOptionsPanel.setVisible(false);
	
	  tabPanel.setHeight("800px");
	  tabPanel.setAnimationDuration(1000);
	  tabPanel.getElement().getStyle().setMarginBottom(10.0, Unit.PX);

	  tabPanel.add(scrollPanelTable, "table");
	  tabPanel.add(new ScrollPanel(map), "map");

	  vPanel.add(searchMenu);
	  vPanel.add(upperPanel);
	  vPanel.add(tabPanel);
	  vPanel.add(sliderPanel);
	  vPanel.setWidth("100%");
	  
	  
	  // Associate the Main panel with the HTML host page.
	  RootPanel.get("clima").add(vPanel);
		
	  searchButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				searchData();
			}
		});
		searchAtEnter(cityField);
		searchAtEnter(yearField);
		searchAtEnter(countryField);
		searchAtEnter(languageField);
		
		sourceLabel.setText("Source: All data is taken form the datafile GlobalLandTemperaturesByMajorCity_v1");
	  	  
		vPanel.add(sourceLabel);
	  
	  	tableDataProvider.addDataDisplay(table.getTable());
	    AsyncHandler columnSortHandler = new AsyncHandler(table.getTable());
	    table.getTable().addColumnSortHandler(columnSortHandler);

	  
	  
	  worldMap = new Map(700,1200, currentQuery,greetService);
		map.add(worldMap);
		
		worldMap.setExcludeUncertainData(toggleUncertainty);
		worldMap.setTotalDataFound(totalDataFound);
		worldMap.setTotalDataVisualized(totalDataVisualized);
		//map.add(updateMapButton);
		
		RootPanel.get().add(vPanel);
		/*yearUP.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if(currentYear==endYear){
					currentYear=startYear;	
				}
				else{
					currentYear++;
				}
				dropBox.setSelectedIndex(currentYear - startYear);
				worldMap.getCurrentQuery().setYear(currentYear);
				worldMap.UpdateWorldMap();
			}
		});

		yearDOWN.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if(currentYear==startYear){
					currentYear=endYear;
				}
				else{
					currentYear--;
				}
				dropBox.setSelectedIndex(currentYear - startYear);	
				worldMap.getCurrentQuery().setYear(currentYear);
				worldMap.UpdateWorldMap();
			}
		});*/

		
		updateMapButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				worldMap.getCurrentQuery().setCountry("");
				worldMap.UpdateWorldMap();
			}
		});

		exportButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				String exportUrl = Window.Location.createUrlBuilder().setPath("exportcsv")
						.setParameter("city", currentQuery.getCity()).setParameter("year", Integer.toString(currentQuery.getYear()))
						.setParameter("country", currentQuery.getCountry())
//						.setParameter("language", currentQuery.getLanguage())
//						.setParameter("duration", currentQuery.getLength())
//						.setParameter("genre", currentQuery.getGenre())
						.buildString();
				
				Window.Location.replace(exportUrl);
				
			}
		});
		tabPanel.addSelectionHandler(new SelectionHandler<Integer>(){
			  public void onSelection(SelectionEvent<Integer> event){
			   int tabId = event.getSelectedItem();
			   if(tabId==0)
			   {
				   isInMapMode=false;
				   toggleMapMode(); 
			   }
			   else
			   {
				   isInMapMode=true;
				   toggleMapMode();
			   }
			 }
			});

	}

	private void searchData() {
		currentQuery.setCity(cityField.getText());
		//currentQuery.setYear(yearField.getText());
		currentQuery.setCountry(countryField.getText());
		//currentQuery.setLanguage(languageField.getText());
		//currentQuery.setGenre(genreField.getText());
		updateData(0, table.getTable().getVisibleRange().getLength());
	}

	private void updateData(int start, int length) {
		//currentQuery.setOffset(start);
		currentQuery.setLimit(length);
		ColumnSortInfo columnSortInfo = table.getTable().getColumnSortList().get(0);
		SortColumn sortColumn = SortColumn.valueOf(columnSortInfo.getColumn().getDataStoreName());
		//currentQuery.setAscending(columnSortInfo.isAscending());
		//currentQuery.setSortColumn(sortColumn);
		greetService.getDataFromServer(currentQuery,
				new AsyncCallback<DataQueryResult>() {
					public void onFailure(Throwable caught) {
						System.out.println("Failed: " + caught.toString());
					}

					public void onSuccess(DataQueryResult result) {
						System.out.println("Success: return "
								+ result.getData().size() + " of "
								+ result.getTotalDataCount() + " movies.");

						if (result.getData().size() == 0) {
							Window.alert("No movies found that match selected criteria");
						}
						tableDataProvider.updateRowData(
								currentQuery.getYear(), result.getData());
						tableDataProvider.updateRowCount(
								result.getTotalDataCount(), true);
					}
				});
	}

	private void searchAtEnter(TextBox filter) {
		filter.addKeyDownHandler(new KeyDownHandler() {
			public void onKeyDown(KeyDownEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					if(!isInMapMode){
						searchData();
						currentYear=Integer.parseInt(yearField.getText());
						
					
					}
					else{
						currentYear=Integer.parseInt(yearField.getText());				
						worldMap.getCurrentQuery().setCountry("");
						worldMap.UpdateWorldMap();
						System.out.println(Integer.parseInt(yearField.getText()));
					}
				}
			}
		});
	}
	
	  
  private void toggleMapMode()
	{
		if(isInMapMode)
		{
			mapOptionsPanel.setVisible(true);
			hPanelSlider.setVisible(true);
			city.setVisible(false);
			search.setVisible(false);
			updateMapButton.setVisible(true);
			/*if(yearField.getText()!=""){
				worldMap.getCurrentQuery().setYear(yearField.getText());
				worldMap.UpdateWorldMap();
			}*/
		}
		else
		{
			mapOptionsPanel.setVisible(false);
			hPanelSlider.setVisible(false);
			city.setVisible(true);
			search.setVisible(true);
			updateMapButton.setVisible(false);
		}
	}
}
