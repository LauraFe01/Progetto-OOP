package ConfrontoSpeedVento.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public class controller {

	@Autowired
	private SeriviziMeteo serviziMeteo;
	
	@RequestMapping(value= "/getAncona")
	public ResponseEntity<Object>  getSpeed()
	{
		return ResponseEntity<>(serviziMeteo.toJSON(serviziMeteo));
	}
	
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
	
	
	
}
