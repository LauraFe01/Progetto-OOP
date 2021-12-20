package com.ConfrontoSpeedVento.exceptions;

/**
 * Questa classe  contiene l'eccezione che viene lanciata in caso di un errore nell'inserimento delle date (data non presente nel database). 
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
