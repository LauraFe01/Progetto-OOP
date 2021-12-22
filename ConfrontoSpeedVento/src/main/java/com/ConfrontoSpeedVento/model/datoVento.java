package com.ConfrontoSpeedVento.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Classe che rappresenta le informazioni relative al vento di una città, in particolare la velocità e la data di campionamento.
 * @author Edoardo Cecchini
 * @author Laura Ferretti
 *
 */

public class datoVento {

	private double speedVento;
	int time;

	/** Costruttore della classe.
	 * @param speedVento         Velocità del vento
	 * @param dataOra            Data e ora della misurazione del vento
	 */	

	public datoVento(double speedVento, int dataOra)
	{
		this.setSpeedVento(speedVento);
		this.time = dataOra;
	}

	/** Costruttore (vuoto) della classe.
	 */	
	
	public datoVento()
	{
		this.setSpeedVento(0);
	}

	/**
	 * Metodo che restituisce la velocità del vento di una determinata città.
	 * @return speedVento
	 */

	public double getSpeedVento() {
		return speedVento;
	}

	/**
	 * Metodo che setta la velocità del vento di una determinata città.
	 * @param double speedVento
	 */

	public void setSpeedVento(double speedVento) {
		this.speedVento = speedVento;
	}

	/**
	 * Metodo che restituisce la data e l'ora della misurazione del vento, prima del return avviene la conversione dal formato UNIX (formato ottenuto dall'API) al formato standard "yyyy-MM-dd HH:mm:ss". 
	 * @param date         		Data della misurazione
	 * @param jdf          		Oggetto SimpleDateFormat contenente il formato della data
	 * @param java_date    		Stringa contenente la data convertita
	 * @return java_date
	 */

	public String getTime() {
		Date date = new Date(time*1000L); 
		SimpleDateFormat jdf = new SimpleDateFormat("yyyy-MM-dd HH:00:00");
		jdf.setTimeZone(TimeZone.getTimeZone("GMT+1"));
		String java_date = jdf.format(date);
		return (java_date);
	}

	/**
	 * Metodo che setta la data e l'ora della misurazione del vento.
	 * @param int time
	 */

	public void setTime (int time) {
		this.time = time;
	}


}
