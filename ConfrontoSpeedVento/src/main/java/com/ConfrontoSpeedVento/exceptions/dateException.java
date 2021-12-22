package com.ConfrontoSpeedVento.exceptions;

/**
 * Questa classe contiene l'eccezione che viene lanciata in caso di inserimento di una data non valida.
 * @author Edoardo Cecchini
 * @author Laura Ferretti
 *
 */

public class dateException extends Exception {
	
	/**
	 * Costruttore.
	 * @param messaggio rappresenta il messaggio di errore.
	 */

	public dateException(String messaggio) {
		super(messaggio);
	}
}
