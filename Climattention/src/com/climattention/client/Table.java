package com.climattention.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.cellview.client.SimplePager.TextLocation;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.climattention.shared.DataQuery;
import com.climattention.shared.Datapoint;
import com.climattention.shared.SortColumn;

public class Table extends Composite {
	
	CellTable<Datapoint> table = new CellTable<Datapoint>();
	VerticalPanel vPanel = new VerticalPanel();
	SimplePager pager;

	TextColumn<Datapoint> dateColumn = new TextColumn<Datapoint>() {
		@Override
		public String getValue(Datapoint object) {
			return String.valueOf(object.getDate());
		}
	};

	TextColumn<Datapoint> yearColumn = new TextColumn<Datapoint>() {
		@Override
		public String getValue(Datapoint object) {
			return  object.getYearAsString();
		}
	};

	TextColumn<Datapoint> countryColumn = new TextColumn<Datapoint>() {
		@Override
		public String getValue(Datapoint object) {
			return object.getCountry();
		}
	};

	TextColumn<Datapoint> cityColumn = new TextColumn<Datapoint>() {
		@Override
		public String getValue(Datapoint object) {
			return object.getCity();
		}
	};

	TextColumn<Datapoint> temperatureColumn = new TextColumn<Datapoint>() {
		@Override
		public String getValue(Datapoint object) {
			return object.getTempAsString();
		}
	};

	//TextColumn<Datapoint> avgTemperatureColumn = new TextColumn<Datapoint>() {
		//@Override
		//public String getValue(Datapoint object) {
			//return object.
		//}
	//};

	TextColumn<Datapoint> uncertainityColumn = new TextColumn<Datapoint>() {
		@Override
		public String getValue(Datapoint object) {
			return object.getUncertainityAsString();

		}
	};

	TextColumn<Datapoint> longitudeColumn = new TextColumn<Datapoint>() {
		@Override
		public String getValue(Datapoint object) {

			return object.getLongitudeAsString();

		}
	};

	TextColumn<Datapoint> latitudeColumn = new TextColumn<Datapoint>() {
		@Override
		public String getValue(Datapoint object) {
			return object.getLatitudeAsString();

		}
	};

	public Table() {

		initWidget(this.vPanel);

		table.addColumn(dateColumn, "Date");
		table.addColumn(yearColumn, "Year");
		table.addColumn(countryColumn, "Country");
		table.addColumn(cityColumn, "City");
		table.addColumn(temperatureColumn, "Temperature");
		table.addColumn(uncertainityColumn, "Uncertainity");
		table.addColumn(longitudeColumn, "Longitude");
		table.addColumn(latitudeColumn, "Latitude");
		

		dateColumn.setSortable(true);
		dateColumn.setDataStoreName(SortColumn.CITY.name());
		yearColumn.setSortable(true);
		yearColumn.setDataStoreName(SortColumn.YEAR.name());
		temperatureColumn.setSortable(true);
		temperatureColumn.setDataStoreName(SortColumn.TEMPERATURE.name());
		//releaseDateColumn.setSortable(true);
		//releaseDateColumn.setDataStoreName(SortColumn.RELEASE_DATE.name());
		//boxOfficeRevenueColumn.setSortable(true);
		//boxOfficeRevenueColumn.setDataStoreName(SortColumn.BOX_OFFICE_REVENUE.name());
		//runtimeColumn.setSortable(true);
		//runtimeColumn.setDataStoreName(SortColumn.RUNTIME.name());

		table.setVisibleRange(0, 20);

		table.getColumnSortList().push(cityColumn);

		SimplePager.Resources pagerResources = GWT
				.create(SimplePager.Resources.class);
		pager = new SimplePager(TextLocation.CENTER, pagerResources, false, 0,
				true);
		pager.setDisplay(table);

		vPanel.add(table);
		vPanel.add(pager);

	}
	
	public CellTable<Datapoint> getTable() {
		return table;
	}




	
}
	

