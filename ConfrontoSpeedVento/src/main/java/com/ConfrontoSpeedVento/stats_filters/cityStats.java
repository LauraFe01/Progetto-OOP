package com.ConfrontoSpeedVento.stats_filters;

import java.util.Vector;
import java.util.Collections;

import org.json.JSONObject;

public class cityStats {
	private double maxValue;
	private double minValue;
	private double average;
	private double variance;



	public double maxCalculator(Vector<Double> storageSpeed)
	{
		maxValue = Collections.max(storageSpeed);
		maxValue = rounding(maxValue);
		return maxValue;
	}
	
	public double minCalculator(Vector<Double> storageSpeed)
	{
		minValue = Collections.min(storageSpeed);
		minValue = rounding(minValue);
		return minValue;
	}

	public double averageCalculator(Vector<Double> storageSpeed)
	{
		double somma = 0.0;

		for(int i = 0; i<storageSpeed.size() ; i++)
		{
			somma += storageSpeed.get(i);
		}

		average = somma / storageSpeed.size();
		average = rounding(average);
		
		return average;
	}

	public double varianceCalculator(Vector<Double> storageSpeed)
	{
		double temp = 0;
		for(double a :storageSpeed)
		{
			temp += (a-average)*(a-average);
		}

		variance = temp/(storageSpeed.size()-1);
		variance = rounding(variance);
		
		return variance;
	}

	public JSONObject statsToJSON(String nomeCitta, String dataInizio, String dataFine)
	{

		JSONObject object = new JSONObject();
		JSONObject speed_data = new JSONObject();
		Vector<Double> storageSpeed = setStorageSpeed(nomeCitta,dataInizio,dataFine);
		speed_data.put("Valore massimo", maxCalculator(storageSpeed));
		speed_data.put("Valore minimo", minCalculator(storageSpeed));
		speed_data.put("Valore medio", averageCalculator(storageSpeed));
		speed_data.put("Varianza", varianceCalculator(storageSpeed));
		object.put("Nome città", nomeCitta);
		object.put("Data inizio", dataInizio);
		object.put("Data fine", dataFine);
		object.put("Statistiche velocità vento", speed_data);

		return object;
	}

	public Vector<Double> setStorageSpeed(String nomeCitta, String dataInizio, String dataFine) 
	{
		Filter filter = new Filter(nomeCitta,dataInizio,dataFine);
		Vector<Double> storageSpeed = filter.FilterCustomRange();
		return storageSpeed;
	}
	
	public double rounding(double val) 
	{
		int rounded = (int) (val*100.0);
		return (double)rounded/100;
	}
}
