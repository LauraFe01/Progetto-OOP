package com.ConfrontoSpeedVento.stats_filters;

import org.json.JSONArray;
import org.json.JSONObject;

public class cityCompare {

	private double min1, min2;
	private double max1, max2;
	private double average1, average2;
	private double variance1, variance2;
	
	
	public JSONObject statsCompare (String nome1, String nome2, String oraInizio, String oraFine)
	{
		cityStats stats = new cityStats();
		JSONObject obj1;
		JSONObject obj2;
		obj1= stats.statsCalculator(nome1, oraInizio, oraFine);
	    obj2 = stats.statsCalculator(nome2, oraInizio, oraFine);
	    
	    JSONObject object1, object2;
	    JSONObject mainObj= new JSONObject();
	    JSONObject compare=new JSONObject();
	    JSONArray nomi =new JSONArray();
	   /*
	    object = obj1.getString("Nome città");
	    object = obj1.getString("Data inizio");
	    object= obj1.getString("Data fine");
	    */
	    
	    object1 = obj1.getJSONObject("Statistiche velocità vento");
	    min1=object1.getDouble("Valore minimo");
	    max1=object1.getDouble("Valore massimo");
	    average1=object1.getDouble("Valore medio");
	    variance1=object1.getDouble("Varianza");
	    
	    object2 = obj2.getJSONObject("Statistiche velocità vento");
	    min2=object2.getDouble("Valore minimo");
	    max2=object2.getDouble("Valore massimo");
	    average2=object2.getDouble("Valore medio");
	    variance2=object2.getDouble("Varianza");
	    
	    
	    JSONObject compareNomi= new JSONObject();
	    
	    compare.put(nome1,nome2);
	    nomi.put(compare);
	    mainObj.put("nomi", nomi);
	    
	    JSONObject compareMin= new JSONObject();
	    JSONObject minV1 = new JSONObject();
	    
	    minV1.put("Valore minimo "+nome1, min1);
	    minV1.put("Valore minimo "+nome2, min2);
	    mainObj.put("Valori minimi", minV1);
	    
	    
	    JSONObject compareMax= new JSONObject();
	    JSONObject maxV1 = new JSONObject();
	    
	    maxV1.put("Valore massimo "+nome1, max1);
	    maxV1.put("Valore massimo "+nome2, max2);
	    mainObj.put("Valori massimi", maxV1);
	    
	    
	    JSONObject compareAverage= new JSONObject();
	    JSONObject avgV1 = new JSONObject();

	    
	    avgV1.put("Valore medio "+nome1, average1);
	    avgV1.put("Valore medio "+nome2, average2);
	    mainObj.put("Valori medi", avgV1);
	    
	    JSONObject compareVariance= new JSONObject();
	    JSONObject varV1 = new JSONObject();

	    
	    varV1.put("Varianza "+nome1, variance1);
	    varV1.put("Varianza"+nome2, variance2);
	    mainObj.put("Varianze", varV1);
	    
	   
	    return mainObj;
	    
	}
	
}
