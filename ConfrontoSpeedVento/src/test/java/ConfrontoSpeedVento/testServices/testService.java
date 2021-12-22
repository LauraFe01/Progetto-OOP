package ConfrontoSpeedVento.testServices;

import java.io.IOException;
import java.util.Vector;

import static org.junit.jupiter.api.Assertions.*;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.ConfrontoSpeedVento.model.Citta;
import com.ConfrontoSpeedVento.model.datoVento;
import com.ConfrontoSpeedVento.services.ImplServiziMeteo;

public class testService {

	private ImplServiziMeteo service;
	Citta city;
	Vector<datoVento> datiVento;


	@BeforeEach
	void setUp() throws Exception {
		service = new ImplServiziMeteo();
		city = new Citta("Roma");
		datiVento = new Vector<datoVento>();
	}

	/**
	 * Serve per distruggere ciò che è stato inizializzato dal metodo setUp.
	 * throws java.lang.Exception
	 */

	@AfterEach
	void tearDown() throws Exception {
	}	

	@Test
	@DisplayName("toJSON corretto")
	void toJSON() throws JSONException {

		city.setNome("Roma");
		city.setIdOpenW(5134295);

		datoVento dato = new datoVento(2.23, 1640079073);
		datiVento.add(dato);
		city.setDatiVento(datiVento);


		JSONObject object = new JSONObject();
		object.put("name", city.getNome());
		object.put("Id", city.getIdOpenW());

		for(int i = 0;i <city.getDatiVento().size();i++)
		{
			object.put("data",(city.getDatiVento()).get(i).getTime());
			object.put("speed",(city.getDatiVento()).get(i).getSpeedVento());
		}

		assertEquals(object.toString(),service.toJSON(city).toString());

	}

}
