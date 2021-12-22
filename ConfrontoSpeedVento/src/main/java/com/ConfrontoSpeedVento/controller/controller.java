
package com.ConfrontoSpeedVento.controller;

import java.io.IOException;


import java.text.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.json.JSONObject;
//import org.json.simple.parser.ParseException;

import com.ConfrontoSpeedVento.services.*;
import com.ConfrontoSpeedVento.exceptions.cityException;
import com.ConfrontoSpeedVento.exceptions.dateException;
import com.ConfrontoSpeedVento.exceptions.vectorNullException;
import com.ConfrontoSpeedVento.model.*;
import com.ConfrontoSpeedVento.stats_filters.*;

/**
 * Classe che gestisce le chiamate al Server
 * @author Edoardo Cecchini
 * @author Laura Ferretti
 */

@RestController
public class controller {

	@Autowired
	private ImplServiziMeteo serviziMeteo;


	@GetMapping(value= "/weatherInfo")
	public ResponseEntity <Object> getWeatherInfo (@RequestParam("nome") String nomeCitta)
	{
		//ServiziMeteo serviziMeteo = new ImplServiziMeteo();
		return new ResponseEntity<>(serviziMeteo.getWeatherInfo(nomeCitta).toString(), HttpStatus.OK);
	}
	 

	/*
	@GetMapping(value = "/save")
	public String save(@RequestParam("nome") String nomeCitta)
	{
			//ServiziMeteo serviziMeteo = new ImplServiziMeteo();
		    serviziMeteo.esportaSuFile(nomeCitta);

		   return "Il file è stato creato!";
	}

	 */

		
	@GetMapping(value="/weather")
    public ResponseEntity<Object> getCityWeather(@RequestParam("nome") String nomeCitta) 
	{	
	    //ServiziMeteo serviziMeteo = new ImplServiziMeteo();
		Citta city = serviziMeteo.getSpeedW(nomeCitta);

		JSONObject obj = new JSONObject();
	    ImplServiziMeteo toJson = new ImplServiziMeteo();

		obj = toJson.toJSON(city);

		return new ResponseEntity<> (obj.toString(), HttpStatus.OK);

    }
	

	/**
	 * Rotta di tipo GET che salva in un file con frequenza oraria i dati relativi alla velocità del vento di una determinata città.
	 * 
	 * @param nomeCitta rappresenta la città di cui si richiede la velocità del vento.
	 * @return un file con le varie informazioni e un messaggio che avverte dell'inzio del salvataggio
	 */

	@GetMapping(value="/hourlySaving")
	public String hourlySaving(@RequestParam("nome") String nomeCitta)
	{
		//ServiziMeteo serviziMeteo = new ImplServiziMeteo();
		serviziMeteo.salvataggioOrario(nomeCitta);
		return("Salvataggio iniziato!");
	}

	/*
	@GetMapping(value = "/filtro")
	public String filtro(@RequestParam("nome") String nomeCitta,@RequestParam ("oraInizio") String oraInizio, @RequestParam("oraFine") String oraFine)
	{
			Filter filter = new Filter(nomeCitta, oraInizio, oraFine);
		    filter.FilterCustomRange();

		   return filter.FilterCustomRange().toString();
	}

	 */

	/**
	 * Rotta di tipo GET che restituisce le statistiche fatte sulla velocità del vento di una determinata città, in una specifica fascia oraria, scelta dall'utente
	 * 
	 * @param nomeCitta rappresenta la città di cui si richiedono le statistiche.
	 * @param oraInizio rappresenta l'ora di inizio della fascia oraria di cui si richiedono le statistiche.
	 * @param oraFine 	rappresenta l'ora di fine della fascia oraria di cui si richiedono le statistiche.
	 * @return la velocità del vento di una città in una determinata fascia di tempo
	 */

	@GetMapping(value= "/stats")
	public ResponseEntity <Object> getStatsInfo (@RequestParam("nome") String nomeCitta,@RequestParam ("oraInizio") String oraInizio, @RequestParam("oraFine") String oraFine)
			throws cityException,dateException, ParseException, vectorNullException
	{	

		cityStats stats = new cityStats();
		stats.setStorageSpeed(nomeCitta, oraInizio, oraFine);

		return new ResponseEntity<>(stats.statsToJSON(nomeCitta,oraInizio,oraFine).toString(), HttpStatus.OK);	

	}


	/**
	 * Rotta di tipo GET che confronta le statistiche relative alla velocità del vento, di due città inserite dall'utente, in una determinata fascia oraria
	 * 
	 * @param nome1 	rappresenta la prima città di cui si richiedono le statistiche da confrontare.
	 * @param nome2 	rappresenta la seconda città di cui si richiedono le statistiche da confrontare.
	 * @param oraInizio rappresenta l'ora di inizio della fascia oraria di cui si richiedono le statistiche da comparare.
	 * @param oraFine 	rappresenta l'ora di fine della fascia oraria di cui si richiedono le statistiche da comparare.
	 * @return il confronto delle le statistiche del vento tra le 2 città inserite, in particolare la velocità del vento massima,minima,media,varianza e la variazione percentuale tra le 2. 
	 */

	@GetMapping(value= "/compare")
	public ResponseEntity <Object> getCompareInfo (@RequestParam("nome1") String nome1,@RequestParam("nome2") String nome2, @RequestParam ("oraInizio") String oraInizio, @RequestParam("oraFine") String oraFine)
			throws cityException, dateException, ParseException, vectorNullException
	{
		statsCompare compare = new statsCompare(nome1,nome2,oraInizio,oraFine);
		return new ResponseEntity<>(compare.StatsCompToJSON().toString(), HttpStatus.OK);
	}


}

