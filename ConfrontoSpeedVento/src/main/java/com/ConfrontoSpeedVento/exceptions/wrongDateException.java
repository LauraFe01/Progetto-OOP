package com.ConfrontoSpeedVento.exceptions;

/**
 * Questa classe contiene l'eccezione che viene lanciata nel caso in cui la data di inizio sia successiva alla data di fine.
 * @author Edoardo Cecchini
 * @author Laura Ferretti
 *
 */

public class wrongDateException extends Exception {
	
	/**
	 * Costruttore.
	 * @param messaggio rappresenta il messaggio di errore.
	 */

	public wrongDateException(String messaggio) {
		super(messaggio);
	}
}


