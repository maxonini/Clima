package com.climattention.shared.Comparation;

import java.util.Comparator;
import com.climattention.shared.Datapoint;


public class DataPointComparator implements Comparator<Datapoint> {


		@Override
		public int compare(Datapoint data1, Datapoint data2) {
			if(data1.getTemperature() == 0) {
				if(data2.getTemperature() == 0) {
					return 0;
				}
				return -1;
			}
			if(data2.getTemperature() == 0) {
				return 1;
			}
			return Float.compare(data1.getTemperature(), data2.getTemperature());
		}
	
	
	
	
}
