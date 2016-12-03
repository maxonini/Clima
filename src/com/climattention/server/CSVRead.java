package com.climattention.server;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import com.climattention.shared.Datapoint;
//import com.google.gwt.user.client.Window;
import com.opencsv.CSVParser;
import com.opencsv.CSVReader;

public class CSVRead {
	
	private List<String[]> myData = new ArrayList<String[]>();
	private List<Datapoint> myDataParsed = new ArrayList<Datapoint>();
	
	@SuppressWarnings("resource")
	public List<String[]> readCSV(String path){
		
		System.out.print("Reading started");
		
		try {
			CSVReader reader = new CSVReader(new FileReader(path),CSVParser.DEFAULT_SEPARATOR, CSVParser.DEFAULT_QUOTE_CHARACTER, 1);
			System.out.print("Reader initialized");
			myData = reader.readAll();
			System.out.print("Reading finished");
		} catch (Exception e) {
			System.out.print("Reading failed");
			e.printStackTrace();
		}
		
		
		return myData;
		
	
	}
	
	public List<Datapoint> parseData(){
		for(String[] currArray : myData){
			Datapoint point = new Datapoint(currArray[0], Float.valueOf(currArray[1]), Float.valueOf(currArray[2]), currArray[3], currArray[4], currArray[5], currArray[6]);
			myDataParsed.add(point);
			
		
		}
		return myDataParsed;
	
	}
	
	public void setMyData(List<String[]> tData){
		myData= tData;
		
	}

}
