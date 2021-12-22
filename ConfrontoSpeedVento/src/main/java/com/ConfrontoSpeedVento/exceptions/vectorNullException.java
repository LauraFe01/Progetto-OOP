package com.ConfrontoSpeedVento.exceptions;

/**
 * Questa classe contiene l'eccezione che viene lanciata in caso l'utente inserisce una fascia oraria o una città di cui non sono presenti dati salvati. 
 * @author Edoardo Cecchini
 * @author Laura Ferretti
 *
 */

public class vectorNullException extends Exception {
	
	/**
	 * Costruttore.
	 * @param messaggio rappresenta il messaggio di errore.
	 */

	public vectorNullException(String messaggio) {
		super(messaggio);
	}

}
