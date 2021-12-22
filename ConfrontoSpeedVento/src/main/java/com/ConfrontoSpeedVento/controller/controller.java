
package com.ConfrontoSpeedVento.controller;

import java.text.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.json.JSONObject;
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

	/**
	 * Rotta di tipo GET che salva in un file con frequenza oraria i dati relativi alla velocità del vento di una determinata città.
	 * 
	 * @param nomeCitta rappresenta la città di cui si richiede la velocità del vento.
	 * @return un file con nome "nomeCitta"SalvataggioOrario.json che si aggiornerà ogni ora.
	 */

	@GetMapping(value="/hourlySaving")
	public String hourlySaving(@RequestParam("nome") String nomeCitta)
	{
		//ServiziMeteo serviziMeteo = new ImplServiziMeteo();
		serviziMeteo.salvataggioOrario(nomeCitta);
		return("Salvataggio iniziato!");
	}


	/**
	 * Rotta di tipo GET che restituisce le statistiche fatte sulla velocità del vento di una determinata città, in una specifica fascia oraria, scelta dall'utente.
	 * 
	 * @param nomeCitta rappresenta la città di cui si richiedono le statistiche.
	 * @param oraInizio rappresenta l'ora di inizio della fascia oraria di cui si richiedono le statistiche.
	 * @param oraFine 	rappresenta l'ora di fine della fascia oraria di cui si richiedono le statistiche.
	 * @return un JSONObject contenente la velocità del vento massima, minima, media, varianza e variazione percentuale di una città in una determinata fascia di tempo.
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
	 * Rotta di tipo GET che confronta le statistiche relative alla velocità del vento, di due città inserite dall'utente, in una determinata fascia oraria.
	 * 
	 * @param nome1 	rappresenta la prima città di cui si richiedono le statistiche da confrontare.
	 * @param nome2 	rappresenta la seconda città di cui si richiedono le statistiche da confrontare.
	 * @param oraInizio rappresenta l'ora di inizio della fascia oraria di cui si richiedono le statistiche da comparare.
	 * @param oraFine 	rappresenta l'ora di fine della fascia oraria di cui si richiedono le statistiche da comparare.
	 * @return un JSONObject contenente il confronto delle le statistiche del vento tra le 2 città inserite, in particolare la velocità del vento massima,minima,media,varianza e la variazione percentuale tra le 2. 
	 */

	@GetMapping(value= "/compare")
	public ResponseEntity <Object> getCompareInfo (@RequestParam("nome1") String nome1,@RequestParam("nome2") String nome2, @RequestParam ("oraInizio") String oraInizio, @RequestParam("oraFine") String oraFine)
			throws cityException, dateException, ParseException, vectorNullException
	{
		statsCompare compare = new statsCompare(nome1,nome2,oraInizio,oraFine);
		return new ResponseEntity<>(compare.StatsCompToJSON().toString(), HttpStatus.OK);
	}


	/**
	 * Rotta di tipo GET che fornisce tutte le informazioni meteorologiche ricavate dall'API di OpenWeather di una determinata città passata come parametro.
	 * 
	 * @param nomeCitta rappresenta la città di cui si richiede la velocità del vento.
	 * @return un JSONObject contenente tutte le informazioni metereologiche di quella città.
	 */

	@GetMapping(value= "/weatherInfo")
	public ResponseEntity <Object> getWeatherInfo (@RequestParam("nome") String nomeCitta)
	{
		//ServiziMeteo serviziMeteo = new ImplServiziMeteo();
		return new ResponseEntity<>(serviziMeteo.getWeatherInfo(nomeCitta).toString(), HttpStatus.OK);
	}
	

	/**
	 * Rotta di tipo GET che permette di salvare le informazioni attuali sulla velocità del vento di una città scelta dall'utente.
	 * 
	 * @param nomeCitta rappresenta la città di cui si richiede la velocità del vento.
	 * @return un file col nome "nomeCitta_todaysDate.txt" con le informazioni attuali.
	 */

	@GetMapping(value = "/save")
	public String save(@RequestParam("nome") String nomeCitta)
	{
		//ServiziMeteo serviziMeteo = new ImplServiziMeteo();
		serviziMeteo.esportaSuFile(nomeCitta);

		return "Il file è stato creato!";
	}


	/**
	 * Rotta di tipo GET che restituisce le informazioni sulla velocità del vento di una determinata città scelta dall'utente e passata come parametro.
	 * 
	 * @param nomeCitta rappresenta la città di cui si richiede la velocità del vento.
	 * @param city 		oggetto della classe Citta con le informazioni del vento relative alla città passata come parametro.
	 * @param obj 		JSONObject dove inserire le varie informazioni sulla velocità del vento di una determinata città.
	 * @return un JSONObject contenente il nome e l'ID della città richiesta, la velocità del vento e la data di campionamento. 
	 */

	@GetMapping(value="/speedW")
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
	 * Rotta di tipo GET che fornisce solo i valori relativi alla velocità del vento di una determinata città, in una specifica fascia oraria, scelta dall'utente.
	 * @param nomeCitta rappresenta la città di cui si richiedono le statistiche.
	 * @param oraInizio rappresenta l'ora di inizio della fascia oraria di cui si richiedono le statistiche.
	 * @param oraFine 	rappresenta l'ora di fine della fascia oraria di cui si richiedono le statistiche.
	 * @return un JSONObject contenente solo i valori della velocità del vento di una città in una determinata fascia di tempo.
	 */

	@GetMapping(value = "/filtro")
	public String filtro(@RequestParam("nome") String nomeCitta,@RequestParam ("oraInizio") String oraInizio, @RequestParam("oraFine") String oraFine)
	{
		Filter filter = new Filter(nomeCitta, oraInizio, oraFine);
		filter.FilterCustomRange();

		return filter.FilterCustomRange().toString();
	}

}

