
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
	
	@GetMapping(value= "/speedWind")
	public ResponseEntity <Object> getWindSpeed (@RequestParam("nome") String nomeCitta)
	{
		ImplServiziMeteo serviziMeteo = new ImplServiziMeteo();
		return new ResponseEntity<>(serviziMeteo.getWindSpeed(nomeCitta).toString(), HttpStatus.OK);
	}

	
	/*
	@RequestMapping(value = "/save", method = RequestMethod.GET)
	public boolean save()
	{
		try {
		    serviziMeteo.esportaSuFile();
		}catch(IOException e) {
			
			return false;
		}
		return true;
	}
	
	*/
	
	@GetMapping(value="/weather")
    public ResponseEntity<Object> getCityWeather(@RequestParam("nome") String cityName) {
		
	    ImplServiziMeteo serviziMeteo = new ImplServiziMeteo();
		Citta city = serviziMeteo.getSpeedW(cityName);
		
		JSONObject obj = new JSONObject();
	    ImplServiziMeteo toJson = new ImplServiziMeteo();
		
		obj = toJson.toJSON(city);
		
		return new ResponseEntity<> (obj.toString(), HttpStatus.OK);
	
    }
	
	
}

