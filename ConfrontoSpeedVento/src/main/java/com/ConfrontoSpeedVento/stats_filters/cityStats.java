package com.ConfrontoSpeedVento.stats_filters;

import java.util.Vector;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import org.json.JSONObject;
import com.ConfrontoSpeedVento.exceptions.cityException;
import com.ConfrontoSpeedVento.exceptions.dateException;
import com.ConfrontoSpeedVento.exceptions.vectorNullException;
import com.ConfrontoSpeedVento.exceptions.wrongDateException;

/** Questa classe calcola tutte le statistiche sulla velocità del vento e crea un JSONOBject con esse.
 * @author Edoardo Cecchini
 * @author Laura Ferretti
 */

public class cityStats {
	private double maxValue;
	private double minValue;
	private double average;
	private double variance;

	
	/**
	 * Questo metodo ritorna un vettore con i dati del vento di una città in una determinata fascia oraria.
	 * @param nomeCitta     		Nome della città.
	 * @param dataInizio     		Data e ora di inizio campionamento.
	 * @param dataFine     			Data e ora di fine campionamento. 
	 * 
	 * @return Vector double contenente tutte le informazioni prese da uno storage di una determinata città in un determinato lasso di tempo.
	 * 
	 * @throws cityException in caso di un errato inserimento del nome di una città.
	 * @throws dateException in caso di inserimento di una data non valida.
	 * @throws ParseException in caso di un inserimento di una data in un formato non ammesso.
	 * @throws vectorNullException in caso di un inserimento di una fascia oraria o una città di cui non sono presenti dati salvati. 
	 * @throws wrongDateException in caso in cui la data di inizio sia successiva alla data di fine.
	 */

	public Vector<Double> setStorageSpeed(String nomeCitta, String dataInizio, String dataFine) 
			throws cityException, dateException, ParseException, vectorNullException, wrongDateException
	{

		if( !(nomeCitta.equals("Ancona")) && !(nomeCitta.equals("Roma")) && !nomeCitta.equals("Milano") && !nomeCitta.equals("Pesaro"))
		{
			throw new cityException(nomeCitta + " non è disponibile. Puoi scegliere tra: \"Ancona\", \"Roma\", \"Milano\", \"Pesaro\"");
		}

		Date dateI = null;
		Date dateF = null;
		

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dateI = sdf.parse(dataInizio);
		if (!dataInizio.equals(sdf.format(dateI))) {
			throw new dateException(" La data inserita non è corretta. ");
		}

		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dateF = sdf.parse(dataFine);
		if (!dataFine.equals(sdf1.format(dateF))) {
			throw new dateException(" La data inserita non è corretta. ");
		}
        
		if(dateI.after(dateF))
		{
			throw new wrongDateException(" ERRORE! La data di inizio è successiva alla data di fine. ");
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
	 * Questo metodo trova il valore massimo delle velocità del vento presenti nel vettore.
	 * @param storageSpeed     		Vettore che contine i dati sulle velocità del vento.
	 *
	 * @return double maxValue contenente il valore massimo nel vettore.
	 */

	public double maxCalculator(Vector<Double> storageSpeed)
	{
		maxValue = Collections.max(storageSpeed);
		maxValue = rounding(maxValue);
		return maxValue;
	}

	/**
	 * Questo metodo trova il valore minimo delle velocità del vento presenti nel vettore.
	 * @param storageSpeed     		Vettore che contine i dati sulle velocità del vento.
	 *
	 * @return double minValue contenente il valore minimo nel vettore.
	 */

	public double minCalculator(Vector<Double> storageSpeed)
	{
		minValue = Collections.min(storageSpeed);
		minValue = rounding(minValue);
		return minValue;
	}

	/**
	 * Questo metodo calcola il valore medio delle velocità del vento presenti nel vettore.
	 * @param storageSpeed     		Vettore che contine i dati sulle velocità del vento.
	 *
	 * @return double average contenente la media dei valori presenti nel vettore.
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
	 * @param storageSpeed     		Vettore che contine i dati sulle velocità del vento.
	 *
	 * @return double variance contenente la varianza dei valori presenti nel vettore.
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
	 * Questo metodo crea un JSONObject contenente tutte le statistiche del vento calcolate sulla città inserita, in una determinata fascia oraria.
	 * @param nomeCitta     		Nome della città.
	 * @param dataInizio     		Data e ora di inizio campionamento.
	 * @param dataFine     			Data e ora di fine campionamento.
	 * 
	 * @return JSONObject contenente tutte le informazioni, nome, data e ora inizio e fine campionamento, valore massimo, minimo, media, variazione e varianza percentuale.
	 * 
	 * @throws cityException in caso di un errato inserimento del nome di una città.
	 * @throws dateException in caso di inserimento di una data non valida.
	 * @throws ParseException in caso di un inserimento di una data in un formato non ammesso.
	 * @throws vectorNullException in caso di un inserimento di una fascia oraria o una città di cui non sono presenti dati salvati.
	 * @throws wrongDateException in caso in cui la data di inizio sia successiva alla data di fine.
	 */

	public JSONObject statsToJSON(String nomeCitta, String dataInizio, String dataFine)
			throws cityException, dateException,ParseException, vectorNullException, wrongDateException
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
	 * Questo metodo permette di arrotondare i valori al centesimo
	 * @param val     				Valore da arrotondare
	 * 
	 * @return il valore inserito arrodondato al secondo numero dopo la virgola
	 */

	public double rounding(double val) 
	{
		int rounded = (int) (val*100.0);
		return (double)rounded/100;
	}
}
