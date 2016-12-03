package com.climattention.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.climattention.shared.AverageData;
import com.climattention.shared.Datapoint;

public class AverageYearCreator {
	
	
	
	
	
private double round(double value, int places) {
    if (places < 0) throw new IllegalArgumentException();
    
    long factor = (long) Math.pow(10, places);
    value = value * factor;
    long tmp = Math.round(value);
    return (double) tmp / factor;
}

public Map<Integer, List<AverageData>> calculateAveragePerYearAndCountry(List<Datapoint> list){
	
	Map<DatapointSimple, List<Double>> DatapointSimpleToList = groupByDatapointSimple(list);
	Map<DatapointSimple, Double> DatapointSimpleToAverage = aggregateByDatapointSimple(DatapointSimpleToList);
	
	return groupPerYear(DatapointSimpleToAverage);
}


private Map<Integer, List<AverageData>> groupPerYear(Map<DatapointSimple, Double> DatapointSimpleToAverage) {
	
	Map<Integer,List<AverageData>> result = new LinkedHashMap<Integer, List<AverageData>>();
	
	for (Map.Entry<DatapointSimple, Double> entry : DatapointSimpleToAverage.entrySet()){
		String country = entry.getKey().getCountry();
		double avgTemp = round(entry.getValue(), 2); //as double with 2 digits after decimal point
		int year = entry.getKey().getYear();
		AverageData averageData = new AverageData(country, avgTemp, year);
		
		if(!result.containsKey(entry.getKey().getYear())){
			result.put(entry.getKey().getYear(), new ArrayList<AverageData>());
		}
		result.get(entry.getKey().getYear()).add(averageData);
	}
	return result;
}

private Map<DatapointSimple, List<Double>> groupByDatapointSimple(List<Datapoint> list) {
	
	Map<DatapointSimple, List<Double>> result = new LinkedHashMap<DatapointSimple, List<Double>>();
	for (Datapoint data: list){
		int year = data.getYearAsInt();
		String country = data.getCountry();
		DatapointSimple DatapointSimple = new DatapointSimple(country, year);
		
		if(!result.containsKey(DatapointSimple)){
			result.put(DatapointSimple, new ArrayList<Double>());
		} 
		result.get(DatapointSimple).add((double) data.getTemperature());
	}	
	return result;
}
private double calculateAvg(List<Double> list) {
	double sum = 0d;
	for(double value : list){
		sum += value;
	}
	return sum/list.size();
}
private Map<DatapointSimple, Double> aggregateByDatapointSimple(
		Map<DatapointSimple, List<Double>> DatapointSimpleToList) {
	 
	Map<DatapointSimple, Double> result = new LinkedHashMap<DatapointSimple, Double>();
	for(Map.Entry<DatapointSimple, List<Double>> entry : DatapointSimpleToList.entrySet()){
		double average = calculateAvg(entry.getValue());
		result.put(entry.getKey(), average);
	}
	return result;
}




}
