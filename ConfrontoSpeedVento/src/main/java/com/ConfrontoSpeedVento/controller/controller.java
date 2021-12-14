
package com.ConfrontoSpeedVento.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.json.JSONObject;
import com.ConfrontoSpeedVento.services.*;
import com.ConfrontoSpeedVento.model.*;

@RestController
public class controller {

	//@Autowired
	//private ImplServiziMeteo serviziMeteo;
	
	@GetMapping(value= "/weatherInfo")
	public ResponseEntity <Object> getWeatherInfo (@RequestParam("nome") String nomeCitta)
	{
		ServiziMeteo serviziMeteo = new ImplServiziMeteo();
		return new ResponseEntity<>(serviziMeteo.getWeatherInfo(nomeCitta).toString(), HttpStatus.OK);
	}

	

	@GetMapping(value = "/save")
	public String save(@RequestParam("nome") String nomeCitta)
	{
			ServiziMeteo serviziMeteo = new ImplServiziMeteo();
		    serviziMeteo.esportaSuFile(nomeCitta);
  
		   return "Il file è stato creato!";
	}
	

	
	@GetMapping(value="/weather")
    public ResponseEntity<Object> getCityWeather(@RequestParam("nome") String nomeCitta) 
	{	
	    ServiziMeteo serviziMeteo = new ImplServiziMeteo();
		Citta city = serviziMeteo.getSpeedW(nomeCitta);
		
		JSONObject obj = new JSONObject();
	    ImplServiziMeteo toJson = new ImplServiziMeteo();
		
		obj = toJson.toJSON(city);
		
		return new ResponseEntity<> (obj.toString(), HttpStatus.OK);
	
    }
	
	@GetMapping(value="/hourlySaving")
	public String hourlySaving(@RequestParam("nome") String nomeCitta)
	{
		ServiziMeteo serviziMeteo = new ImplServiziMeteo();
		serviziMeteo.salvataggioOrario(nomeCitta);
		return("Salvataggio iniziato!");
	}
	

	@GetMapping(value = "/filtro")
	public String filtro(@RequestParam("nome") String nomeCitta,@RequestParam ("oraInizio") String oraInizio, @RequestParam("oraFine") String oraFine)
	{
			ServiziMeteo serviziMeteo = new ImplServiziMeteo();
		    serviziMeteo.importaDaFile(nomeCitta, oraInizio, oraFine);
           
		   return serviziMeteo.importaDaFile(nomeCitta, oraInizio, oraFine).toString();
	}
	
}

