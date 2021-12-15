package com.ConfrontoSpeedVento.stats_filters;

import java.util.Vector;
import java.util.Collections;

import org.json.JSONObject;

public class cityStats {
	private Vector<Double> storageSpeed;
	private double maxValue;
	private double minValue;
	private double average;
	private double variance;



	public JSONObject statsCalculator(String nomeCitta, String dataInizio, String dataFine)
	{
		double somma = 0.0;
		Filter filter = new Filter(nomeCitta,dataInizio,dataFine);
		storageSpeed = filter.FilterCustomRange();
		
		maxValue = Collections.max(storageSpeed);
		minValue = Collections.min(storageSpeed);

		for(int i = 0; i<storageSpeed.size() ; i++)
		{
			somma += storageSpeed.get(i);
		}

		average = somma / storageSpeed.size();

		double temp = 0;
		for(double a :storageSpeed)
		{
			temp += (a-average)*(a-average);
		}
		
		variance = temp/(storageSpeed.size()-1);
		
		JSONObject object = new JSONObject();
        JSONObject speed_data = new JSONObject();
        
        speed_data.put("Valore massimo", maxValue);
        speed_data.put("Valore minimo", minValue);
        speed_data.put("Valore medio", average);
        speed_data.put("Varianza", variance);
        object.put("Nome città", nomeCitta);
        object.put("Data inizio", dataInizio);
        object.put("Data fine", dataFine);
        object.put("Statistiche velocità vento", speed_data);
          
        return object;
	}

}
