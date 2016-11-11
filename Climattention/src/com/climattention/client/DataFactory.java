package com.climattention.client;

import java.util.ArrayList;

public class DataFactory {
int minYear= 1981;												//ab welchem Jahr die Daten relevant sind
int maxYear= 2013;												// bis zu welchem Jahr Daten relevant sind
ArrayList<Datapoint> data = new ArrayList<Datapoint>();



public void dataReader(){										// csv Datei Einlesen, Aussortieren und Durchschnittstemperaturen ausrechnen
	try {
        java.io.BufferedReader FileReader=                      // Reader initailisieren
                new java.io.BufferedReader(
                    new java.io.FileReader(
                        new java.io.File("data.csv")				
                    )
                );
       
        String zeile="";										//jede Zeile einlesen als kompleter String
        FileReader.readLine();									// erste Zeile ignorieren
        while(null!=(zeile=FileReader.readLine())){          	
            String[] split=zeile.split(",");                	//bei "," String spliten, mit den einzelnen Splits wird Datapoint instanziert
         Datapoint datapoint = new Datapoint(split[0],stringToInt(split[0],4),Float.valueOf(split[1]),Float.valueOf(split[2]),split[3],split[4],stringToPosition(split[5],4),stringToPosition(split[6],4));                     
         if(this.isDataRelevant(datapoint)){					// Jahreszahl Checken
        	data.add(datapoint); 								// Zur Arrayliste zufügen
         }
        }
       
    } catch (Exception e) {										// lul keine Ahnung, teil eines kopierten Codes
        e.printStackTrace();
    }
		this.dataConcentrate();									// Temperaturdaten werden zu Jahres Temperatur Daten zusammengefasst
  					}

private int stringToInt(String date,int position){				// String zu int, wichtig für den JahresCheck
	return Integer.valueOf(date.substring(0,position));
	}
private Float stringToPosition(String date,int position){		// Längen und Breitengard von String zu float. aus 4,34W wird zb -4,34 (Laut wiki wird das so gehändelt)
	float returnPosition ;
	returnPosition = Float.valueOf(date.substring(0,position));
	if(date.contains("S")||date.contains("W")){
		returnPosition = returnPosition*(-1);
		}
	return returnPosition;
	}
private boolean isDataRelevant(Datapoint datapoint){				//Methode um  Jahr zu Checken
	return datapoint.getYear()>minYear&& datapoint.getYear()<maxYear;
	}
private boolean sameCity(int i,int y){ return data.get(i).getCity().equals(data.get(y).getCity()); //vergleicht 2 Städte aus der ArrayList
	}
private boolean sameYear(int i,int y){ return data.get(i).getYear()==data.get(y).getYear();		// vergleicht 2 Jahreszaheln aus der ArrayList
	}
private void dataConcentrate(){				//methode um die Monatsmessungen in Jahresmessungen zusammenzufassen
	int index = 0;
	int divisor =1;
	float tempSumm;
	float maxUncertainty;
	float temperature;
	tempSumm = data.get(index).getTemperature();
	maxUncertainty= data.get(index).getUncertainty();
	ArrayList<Datapoint> dataConcentrate = new ArrayList<Datapoint>();
	
	for(int i = 1;i<data.size();i++){

		if(sameYear(index,i)&& sameCity(index,i)){
			tempSumm = tempSumm + data.get(i).getTemperature();
			divisor = divisor + 1;
			if (data.get(i).getUncertainty()>maxUncertainty){					//stellt sicher das die grösste Messungenauigkeit übernommen wird
				maxUncertainty=data.get(i).getUncertainty();
			}
			
		}
		else{
				temperature = (tempSumm/divisor);
				Datapoint datapoint = new Datapoint(data.get(index).getDate(),data.get(index).getYear(),temperature,maxUncertainty,data.get(index).getCity(),data.get(index).getCountry(),data.get(index).getLatitude(),data.get(index).getLongitude());
				dataConcentrate.add(datapoint);
				divisor = 1;
				tempSumm = data.get(index).getTemperature();
				maxUncertainty= data.get(index).getUncertainty();
				index =i;
			}	
			
		}
			data=dataConcentrate;
	}
public void test(){						// Testmethode für zwischenSchritte
int y= 0;
System.out.println("City: "+data.get(y).getCity());

for(int i =0;i< data.size();i++){

if(false==this.sameCity(i, y)){System.out.println("City: "+data.get(i).getCity());y=i;}
	
System.out.println("Year: "+ data.get(i).getYear()+" Temperature: "+data.get(i).getTemperature());
	}
}

}



