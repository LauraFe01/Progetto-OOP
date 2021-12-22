package com.ConfrontoSpeedVento.stats_filters;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Vector;

import org.json.JSONObject;
import org.json.JSONTokener;

import com.ConfrontoSpeedVento.model.datoVento;

/** Questa classe gestisce il filtro orario in cui possono essere visualizzate le informazioni relative alla velocità del vento delle città
 * @author Edoardo Cecchini
 * @author Laura Ferretti
 */


public class Filter {

	private String nomeCitta;
	private String dataInizio;
	private String dataFine;
	//private Vector<Double> speed;
	
	/** Costruttore della classe.
	 * @param nomeCitta             Nome della città
	 * @param dataInizio            Data e ora di inizio del filtro
	 * @param dataFine             	Data e ora di fine del filtro
	 */

	public Filter(String nomeCitta, String dataInizio, String dataFine)
	{
		this.nomeCitta= nomeCitta;
		this.dataInizio = dataInizio;
		this.dataFine = dataFine;
	}
	
	/**
	 * Questo metodo legge un file e restituisce le statistiche del vento di una città nella fascia oraria richiesta
	 * @param route     	Percorso in cui risiede il file da leggere
	 * @param datiSpeed 	Vector contenente le velocità del vento
	 * @param flag			Boolean che diventa true se all'interno del file trova la data di inizio inserita e false quando trova la data di fine
	 * @param line			Variabile di supporto utilizzata per leggere il file linea per linea
	 * @param tokener		Oggetto della classe JSONTokener che estrae dalla stinga un token
	 * @param object		JSONObject contenente il token
	 * 
	 * @return un Vector contenente i dati delle velocità del vento di una città nella fascia oraria specificata.
	 */
	
	public Vector<Double> FilterCustomRange()
	{
		String route = System.getProperty("user.dir") + "/src/main/resources/" + nomeCitta + "SalvataggioOrario.json";

		Vector<Double> datiSpeed=new Vector<Double>();

		boolean flag=false;
		String line = null;

		try {
			FileReader fileReader = new FileReader(route);

			BufferedReader bufferedReader = new BufferedReader(fileReader);

			while((line = bufferedReader.readLine()) != null) {

				JSONTokener tokener = new JSONTokener(line);
				JSONObject object = new JSONObject(tokener);

				if(dataInizio.equals((String)object.get("data")))
				{  
					flag=true;
				}

				if(flag==true)
				{
					datiSpeed.add((double)object.get("speed"));
				}

				if(dataFine.equals((String) object.get("data")))
				{
					flag=false;
				}

			}
			bufferedReader.close(); 

			return datiSpeed;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return datiSpeed;
	}

}
