package com.climattention.client;

import com.climattention.shared.Datapoint;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.visualizations.GeoMap;


public class Map implements Ivis{
	
	
	private int mapWidth = 900;
	private int mapHeight = 500;
	
	private DataTable dataTable;
	private GeoMap.Options options;
	private GeoMap geomap;
	
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
			 * This method uses he gwt-visualization-1.1.2 libarary to visualize the climate data on to a map
			 * 
			 * @return visualized map widget
			 */
			@Override
			public void run() {
				
				// Building 
				dataTable = DataTable.create();
				dataTable.addColumn(ColumnType.STRING, "Country");
				dataTable.addColumn(ColumnType.NUMBER, "Temperature");
				//dataTable.addColumn(ColumnType.STRING, "Precise Temperature");
				
				dataTable.addRows(climateData.length);
				for (int i = 0; i < climateData.length; i++){
					dataTable.setValue(i, 0, climateData[i].getCountry());
					dataTable.setValue(i, 1, climateData[i].getTemperature());
					//dataTable.setValue(i, 2, "Chocolate" + climateData[i].getTemperature());
				}
				
				options = GeoMap.Options.create();
				options.setDataMode(GeoMap.DataMode.REGIONS);
				options.setRegion("world");
				options.setWidth(mapWidth);
				options.setHeight(mapHeight);
				options.setShowZoomOut(true);
				options.setShowLegend(true);
				geomap = new GeoMap(dataTable, options);
				
				verticalPanel.clear();
				verticalPanel.add(geomap);
				
				System.out.println("dataTable done");
				
			}
		};
		
		VisualizationUtils.loadVisualizationApi(onLoadCallback, GeoMap.PACKAGE);
		return verticalPanel;
	}

	/**
	 * Removes the current climate data from the map and adds the new to be visualized climate data to the
	 * mapvisualization object. To get he new visualization the method getVisualization(VerticalPanel verticalPanel)
	 * must be called
	 * 
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