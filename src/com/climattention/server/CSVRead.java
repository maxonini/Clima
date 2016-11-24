package com.climattention.server;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import com.climattention.shared.Datapoint;
import com.google.gwt.user.client.Window;
import com.opencsv.CSVReader;

public class CSVRead {
	
	private List<String[]> myData;
	private List<Datapoint> myDataParsed = new ArrayList<Datapoint>();
	
	
	public List<String[]> readCSV(String path){
		
		Window.alert("Reading startet");
		
		try {
			CSVReader reader = new CSVReader(new FileReader(path));
			myData = reader.readAll();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return myData;
		
	
	}
	
	public List<Datapoint> parseData(){
		int j=0;
		for(String[] currArray : myData){
			Datapoint point = new Datapoint(currArray[0], Float.valueOf(currArray[1]), Float.valueOf(currArray[2]), currArray[3], currArray[4], Float.valueOf(currArray[5]), Float.valueOf(currArray[6]));
			myDataParsed.set(j, point);
			j++;
		
		}
		return myDataParsed;
	
	}
	
	public void setMyData(List<String[]> tData){
		myData= tData;
		
	}

}
