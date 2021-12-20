package com.ConfrontoSpeedVento.exceptions;

/**
 * Questa classe  contiene l'eccezione che viene lanciata in caso di un errato inserimento del nome di una città. 
 * @author Edoardo Cecchini
 * @author Laura Ferretti
 *
 */

public class cityException extends Exception{
	
	/**
	 * Costruttore.
	 * @param messaggio rappresenta il messaggio di errore.
	 */
	
	public cityException(String messaggio) {
		super(messaggio);

	}

}
