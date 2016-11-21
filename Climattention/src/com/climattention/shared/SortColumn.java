package com.climattention.shared;

import java.util.Collections;
import java.util.Comparator;

import com.climattention.shared.Comparation.AlphanumComparator1;
import com.climattention.shared.Comparation.AlphanumComparator2;
import com.climattention.shared.Comparation.AlphanumComparator3;
import com.climattention.shared.Comparation.AlphanumComparator4;
import com.climattention.shared.Comparation.AlphanumComparator5;
import com.climattention.shared.Comparation.DataPointComparator;

public enum SortColumn {

	TEMPERATURE(new AlphanumComparator4()),
	CITY(new AlphanumComparator2()),
	YEAR(new AlphanumComparator3()),
	DATE(new AlphanumComparator5()),
	TITLE(new Comparator<Datapoint>() {
		public int compare(Datapoint o1, Datapoint o2) {
			if (o1 == o2) {
				return 0;
			}
			
			if (o1 != null) {
				return (o2 != null) ? o1.getCity()
						.compareTo(o2.getCity()) : 1;
			}
			return -1;
		}
	}),
	TEMP(new DataPointComparator());
	
	private final Comparator<Datapoint> comparator;
	
	private SortColumn(Comparator<Datapoint> comparator) {
		this.comparator = comparator;
	}
	
	public Comparator<Datapoint> getComparator(boolean ascending) {
		if(ascending) {
			return comparator;
		}
		return Collections.reverseOrder(comparator);
	}
}

