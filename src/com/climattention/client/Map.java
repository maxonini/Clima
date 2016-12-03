package com.climattention.client;

import com.climattention.shared.Datapoint;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
//import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.visualizations.GeoMap;
import com.kiouri.sliderbar.client.solution.simplehorizontal.SliderBarSimpleHorizontal;


public class Map implements Ivis{
	
	
	private int mapWidth = 900;
	private int mapHeight = 500;
	
	private DataTable dataTable;
	private GeoMap.Options options;
	private GeoMap geomap;
	private FlowPanel sliderPanel = new  FlowPanel();
	private Label halp = new Label("Hier oben ist ein Slider, man sieht ihn aber nicht :(");

	private Datapoint[] climateData;
	
	public Map(){}
	
	public Map(int mapWidth, int mapHeight, Datapoint[] climateData){
		this.mapWidth = mapWidth;
		this.mapHeight = mapHeight;
		this.climateData = climateData;
	}
	
	@Override
	public Widget getVis(final VerticalPanel verticalPanel) {
		Runnable onLoadCallback = new Runnable(){
			
			/**
			 * This method uses he gwt-visualization-1.1.2 library 
			 * 
			 * @return visualized map widget
			 */
			@Override
			public void run() {
			
				dataTable = DataTable.create();
				dataTable.addColumn(ColumnType.STRING, "Country");
				dataTable.addColumn(ColumnType.NUMBER, "Temperature");
				
				dataTable.addRows(climateData.length);
				for (int i = 0; i < climateData.length; i++){
					dataTable.setValue(i, 0, climateData[i].getCountry());
					dataTable.setValue(i, 1, climateData[i].getTemperature());
				}
				
				options = GeoMap.Options.create();
				options.setDataMode(GeoMap.DataMode.REGIONS);
				options.setRegion("world");
				options.setWidth(mapWidth);
				options.setHeight(mapHeight);
				options.setShowZoomOut(true);
				options.setShowLegend(true);
				options.setColors(0x76F7FF,0x64FFCC,0x4EE262,0xFFAB00,0xFF0000);
				geomap = new GeoMap(dataTable, options);
				
				SliderBarSimpleHorizontal slider = new SliderBarSimpleHorizontal(50, "60%", true);
				slider.drawMarks("red", 100);
				sliderPanel.add(slider);
				sliderPanel.add(halp);
				
				
			
				verticalPanel.clear();
				verticalPanel.add(geomap);
				verticalPanel.add(sliderPanel);

				
				System.out.println("dataTable done");
				
			}
		};
		
		VisualizationUtils.loadVisualizationApi(onLoadCallback, GeoMap.PACKAGE);
		return verticalPanel;
	}

	/**
	 * reloads the data and updates the map with the new temperatures
	 * @return void
	 */
	@Override
	public void reloadData(Datapoint[] newData) {
		this.climateData = newData;
	}
	
	public Datapoint[] getData(){
		return this.climateData;
	}


}
