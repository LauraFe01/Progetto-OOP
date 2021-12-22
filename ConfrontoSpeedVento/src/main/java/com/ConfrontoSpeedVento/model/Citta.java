package com.ConfrontoSpeedVento.model;

import java.util.*;

/**
 * Questa classe descrive i vari parametri di ogni città e le corrispondenti velocità del vento.
 * @author Edoardo Cecchini
 * @author Laura Ferretti
 *
 */

/** Costruttore della classe.
 * @param nome               Nome della città.
 * @param idOpenW            ID della città relativo all'API di Open Weather.
 * @param vector             Dati del vento relativi alla città.
 */


public class Citta {
	private String nome;
	private long idOpenW;
	private Vector<datoVento> datiVento;

	public Citta(String nome)
	{
		this.nome=nome;
		this.datiVento = new Vector<datoVento>();
	}

	/**
	 * Metodo che restituisce il nome della città.
	 * @return nome
	 */

	public String getNome() {
		return nome;
	}
	
	/**
	 * Metodo che setta il nome della città.
	 * @param String nome
	 */

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	/**
	 * Metodo che restituisce l'ID della città.
	 * @return idOpenW
	 */

	public long getIdOpenW() {
		return idOpenW;
	}
	
	/**
	 * Metodo che setta l'ID della città.
	 * @param long idOpenW
	 */

	public void setIdOpenW(long idOpenW) {
		this.idOpenW = idOpenW;
	}
	
	/**
     * Metodo che restituisce il vettore di datoVento con le informazioni della velocità del vento della città.
     * @return datiVento.
     */

	public Vector<datoVento> getDatiVento()
	{
		return this.datiVento;
	}
	
	/**
     * Metodo che setta il vettore di datoVento.
     * @param Vector datiVento.
     */

	public void setDatiVento(Vector<datoVento> datiVento) {
		this.datiVento = datiVento; 
	}

}