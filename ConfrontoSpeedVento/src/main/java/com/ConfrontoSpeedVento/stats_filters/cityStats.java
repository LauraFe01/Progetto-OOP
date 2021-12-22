package com.ConfrontoSpeedVento.stats_filters;

import java.util.Vector;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.TimeZone;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.ConfrontoSpeedVento.exceptions.cityException;
import com.ConfrontoSpeedVento.exceptions.dateException;
import com.ConfrontoSpeedVento.exceptions.vectorNullException;


/** Questa classe calcola tutte le statistiche sulla velocità del vento e permette di inserirle in un file JSON.
 * @author Edoardo Cecchini
 * @author Laura Ferretti
 */

public class cityStats {
	private double maxValue;
	private double minValue;
	private double average;
	private double variance;

	/**
	 * Questo metodo calcola il valore massimo delle velocità del vento presenti nel vettore
	 * @param storageSpeed     		Vettore che contine i dati sulle velocità del vento
	 * @param maxValue				Variabile che contiene la massima velocità del vento presente
	 *
	 * @return double maxValue contenente il valore massimo nel vettore
	 */

	public double maxCalculator(Vector<Double> storageSpeed)
	{
		maxValue = Collections.max(storageSpeed);
		maxValue = rounding(maxValue);
		return maxValue;
	}

	/**
	 * Questo metodo calcola il valore minimo delle velocità del vento presenti nel vettore
	 * @param storageSpeed     		Vettore che contine i dati sulle velocità del vento
	 * @param minValue				Variabile che contiene la minima velocità del vento presente
	 *
	 * @return double minValue contenente il valore minimo nel vettore
	 */

	public double minCalculator(Vector<Double> storageSpeed)
	{
		minValue = Collections.min(storageSpeed);
		minValue = rounding(minValue);
		return minValue;
	}

	/**
	 * Questo metodo calcola il valore medio delle velocità del vento presenti nel vettore
	 * @param storageSpeed     		Vettore che contine i dati sulle velocità del vento
	 * @param somma					Somma di tutti i valori presenti nel vettore 
	 * @param average				Variabile che contiene la media delle velocità del vento presenti
	 *
	 * @return double average contenente la media dei valori presenti nel vettore
	 */

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

	/**
	 * Questo metodo calcola il valore della varianza delle velocità del vento presenti nel vettore
	 * @param storageSpeed     		Vettore che contine i dati sulle velocità del vento
	 * @param temp					Variabile di supporto utile ai calcoli della varianza
	 * @param a						Variabile utilizzata dal for in cui vengono messi a uno a uno i valori di storageSpeed
	 * @param variance				Variabile che contiene la varianza delle velocità del vento presenti
	 * @param average				Variabile che contiene la media delle velocità del vento presenti
	 *
	 * @return double variance contenente la varianza dei valori presenti nel vettore
	 */

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
	
	/**
	 * Questo metodo crea un JSONObject contenente tutte le statistiche calcolate
	 * @param nomeCitta     		Nome della città
	 * @param dataInizio     		Data e ora di inizio campionamento
	 * @param dataFine     			Data e ora di fine campionamento
	 * @param storageSpeed     		Vettore che contine i dati sulle velocità del vento
	 * @param object				JSONObject in cui inserire le informazioni
	 * @param speed_data			JSONObject in cui inserire solo le informazioni del vento (così da creare un JSON ordinato)
	 * @param storageSpeed			Variabile che contiene la varianza delle velocità del vento presenti
	 * 
	 * @return JSONObject contenente tutte le informazioni, nome, data e fine campionamento, valore massimo, minimo, media e varianza percentuale
	 */

	public JSONObject statsToJSON(String nomeCitta, String dataInizio, String dataFine)
			throws cityException, dateException,ParseException, vectorNullException
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

	/**
	 * Questo metodo crea un JSONObject contenente tutte le statistiche calcolate
	 * @param nomeCitta     		Nome della città
	 * @param dataInizio     		Data e ora di inizio campionamento
	 * @param dataFine     			Data e ora di fine campionamento
	 * @param date     				Variabile in cui inserire la data e ora formattata
	 * @param sdf					Oggetto della classe SimpleDateFormat contenente il formato corretto in cui avere la data e ora
	 * @param filter				Oggetto della classe Filter
	 * @param speed_data			JSONObject in cui inserire le informazioni del vento in base al filtro orario inserito dall'utente utilizzando il metodo FilterCustomRange() della classe Filter 
	 * 
	 * 
	 * @return Vector double contenente tutte le informazioni prese da uno storage di una determinata città in un determinato lasso di tempo
	 */
	
	public Vector<Double> setStorageSpeed(String nomeCitta, String dataInizio, String dataFine) 
			throws cityException, dateException, ParseException, vectorNullException
	{

		if( !(nomeCitta.equals("Ancona")) && !(nomeCitta.equals("Roma")) && !nomeCitta.equals("Milano") && !nomeCitta.equals("Pesaro"))
		{
			throw new cityException(nomeCitta + " non è disponibile. Puoi scegliere tra: \"Ancona\", \"Roma\", \"Milano\", \"Pesaro\"");
		}

		Date date = null;

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		date = sdf.parse(dataInizio);
		if (!dataInizio.equals(sdf.format(date))) {
			throw new dateException(" La data inserita non è corretta. ");
		}

		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		date = sdf.parse(dataFine);
		if (!dataFine.equals(sdf1.format(date))) {
			throw new dateException(" La data inserita non è corretta. ");
		}



		Filter filter = new Filter(nomeCitta,dataInizio,dataFine);
		Vector<Double> storageSpeed = filter.FilterCustomRange();

		if(storageSpeed.isEmpty())
		{
			throw new vectorNullException("Dati non disponibili nell'arco di tempo selezionato");
		}

		return storageSpeed;

	}

	/**
	 * Questo metodo permette di arrotondare i valori
	 * @param val     				Valore da arrotondare
	 * @param rounded     			Variabile contenente il valore arrotondato
	 * 
	 * @return il valore inserito arrodondato al centesimo
	 */
	
	public double rounding(double val) 
	{
		int rounded = (int) (val*100.0);
		return (double)rounded/100;
	}
}
