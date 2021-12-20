package com.ConfrontoSpeedVento.stats_filters;

import java.text.ParseException;
import java.util.Vector;

import org.json.JSONArray;
import org.json.JSONObject;

import com.ConfrontoSpeedVento.exceptions.cityException;
import com.ConfrontoSpeedVento.exceptions.dateException;
import com.ConfrontoSpeedVento.exceptions.vectorNullException;

public class statsCompare extends cityStats {

	private Vector<Double> storageSpeed1;
	private Vector<Double> storageSpeed2;
	private double max1;
	private double max2;
	private double min1;
	private double min2;
	private double avg1;
	private double avg2;
	private double var1;
	private double var2;
	private String nome1, nome2, dataInizio, dataFine;

	public statsCompare(String nome1, String nome2, String dataInizio, String dataFine)
			throws cityException, dateException, ParseException,vectorNullException
	{
		super();

		storageSpeed1=super.setStorageSpeed(nome1, dataInizio, dataFine);
		storageSpeed2=super.setStorageSpeed(nome2, dataInizio, dataFine);

		this.dataInizio = dataInizio;
		this.dataFine = dataFine;
		this.nome1 = nome1;
		this.nome2 = nome2;
	}


	//ricorda di spiegare cosa succede in caso di divisione per 0
	public double maxCompare()
	{
		double variazioneMax=0.0;

		max1 = super.maxCalculator(storageSpeed1);
		max2 = super.maxCalculator(storageSpeed2);


		variazioneMax =((max1/max2)*100)-100;
		variazioneMax = super.rounding(variazioneMax);
		return variazioneMax;

	}

	public double minCompare()
	{
		double variazioneMin=0.0;

		min1 = super.minCalculator(storageSpeed1);
		min2 = super.minCalculator(storageSpeed2);;


		variazioneMin =((min1/min2)*100)-100;
		variazioneMin = super.rounding(variazioneMin);
		return variazioneMin;
	}

	public double averageCompare()
	{
		double variazioneAvg=0.0;

		avg1 = super.averageCalculator(storageSpeed1);
		avg2 = super.averageCalculator(storageSpeed2);

		variazioneAvg =((avg1/avg2)*100)-100;
		variazioneAvg = super.rounding(variazioneAvg);
		return variazioneAvg;
	}

	public double varianceCompare()
	{
		double variazioneVar=0.0;

		var1 = super.varianceCalculator(storageSpeed1);
		var2 = super.varianceCalculator(storageSpeed2);

		variazioneVar =((var1/var2)*100)-100;
		variazioneVar = super.rounding(variazioneVar);
		return variazioneVar;
	}

	public JSONObject StatsCompToJSON()
	{
		JSONArray nomi =new JSONArray();
		JSONArray dati =new JSONArray();
		JSONObject date =new JSONObject();
		JSONObject mainObj = new JSONObject();
		double variazioneMin= minCompare();
		double variazioneMax= maxCompare();
		double variazioneAvg= averageCompare();
		double variazioneVar= varianceCompare();

		nomi.put(nome1);
		nomi.put(nome2);
		mainObj.put("città", nomi);

		date.put("dal ", dataInizio);
		date.put("al ",dataFine);
		mainObj.put("periodo di confronto ", date);

		JSONObject minV1 = new JSONObject();
		JSONObject minV = new JSONObject();
		
		minV1.put("Valore minimo "+nome1, min1);
		minV1.put("Valore minimo "+nome2, min2);
		minV1.put("Variazione percentuale del valore di "+nome1 + " rispetto a quello di " +nome2, variazioneMin);
		minV.put("Valori minimi velocità vento", minV1);
		dati.put(minV);


		JSONObject maxV1 = new JSONObject();
		JSONObject maxV = new JSONObject();

		maxV1.put("Valore massimo "+nome1, max1 );
		maxV1.put("Valore massimo "+nome2, max2);
		maxV1.put("Variazione percentuale del valore di "+nome1 + " rispetto a quello di " +nome2, variazioneMax + "%");
		maxV.put("Valori massimi velocità vento", maxV1);
		dati.put(maxV);


		JSONObject avgV1 = new JSONObject();
		JSONObject avgV = new JSONObject();
		
		avgV1.put("Valore medio "+nome1, avg1);
		avgV1.put("Valore medio "+nome2, avg2);
		avgV.put("Variazione percentuale del valore di "+nome1 + " rispetto a quello di " +nome2, variazioneAvg );
		avgV.put("Valori medi velocità vento", avgV1);
		dati.put(avgV);
	

		JSONObject varV1 = new JSONObject();
		JSONObject varV = new JSONObject();
		
		varV1.put("Varianza "+nome1, var1);
		varV1.put("Varianza "+nome2, var2);
		varV1.put("Variazione Percentuale del valore di "+nome1 + " rispetto a quello di " +nome2, variazioneVar );
		varV.put("Varianze velocità vento", varV1);
		dati.put(varV);
		mainObj.put("dati", dati);


		return mainObj;

	}

}
