package com.climattention.client;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.climattention.shared.Datapoint;
import com.google.gwt.visualization.client.visualizations.Table;
import com.google.gwt.visualization.client.visualizations.Table.Options;

public class MyTable extends Composite implements Ivis {
	
	public MyTable(){}
	
	private Datapoint[] data;
	private DataTable dataTable;
	private Table table;
	private Options options;


	@Override
	public Widget getVis(final VerticalPanel vP) {
			Runnable onLoadCallback = new Runnable(){

				@Override
				public void run() {
					dataTable = DataTable.create();
					dataTable.addColumn(ColumnType.STRING, "Country");
					dataTable.addColumn(ColumnType.STRING, "City");
					dataTable.addColumn(ColumnType.NUMBER, "Temperature");
					dataTable.addColumn(ColumnType.NUMBER, "Temperature deviation");
					dataTable.addColumn(ColumnType.DATE, "Date");
					dataTable.addColumn(ColumnType.STRING, "Latitude");
					dataTable.addColumn(ColumnType.STRING, "Longitude");
					
					dataTable.addRows(data.length);
					for (int i = 0; i < data.length; i++){
						dataTable.setValue(i, 0, data[i].getCountry());
						dataTable.setValue(i, 2, data[i].getTemperature());
						dataTable.setValue(i, 1, data[i].getCity());
						dataTable.setValue(i, 3, data[i].getUncertainty());
						dataTable.setValue(i, 4, data[i].getDate());
						dataTable.setValue(i, 5, data[i].getLatitude());
						dataTable.setValue(i, 6, data[i].getLongitude());
					}
					options = Options.create();
					options.setPageSize(15);
					
					table = new Table(dataTable, options);
					
					vP.clear();
					vP.add(table);
					
				}
				
				
				
			};
			VisualizationUtils.loadVisualizationApi(onLoadCallback, Table.PACKAGE);
		return vP;
	}

	@Override
	public void reloadData(Datapoint[] newData) {
		data = newData;		
	}
	
	public Datapoint[] getData(){
		return (Datapoint[])data;
	}
	
	
	
	
}