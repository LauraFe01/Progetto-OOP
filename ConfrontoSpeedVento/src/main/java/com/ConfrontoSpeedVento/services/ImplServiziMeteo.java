package com.ConfrontoSpeedVento.services;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.json.JSONObject;
import com.ConfrontoSpeedVento.model.Citta;
import com.ConfrontoSpeedVento.model.datoVento;
import java.io.PrintWriter;
import java.time.LocalDate;

/** Questa classe è l'implementazione dell'interfaccia ServiziMeteo.
 * @author Edoardo Cecchini
 * @author Laura Ferretti
 */

@Service
public class ImplServiziMeteo implements ServiziMeteo{

	/**
	 * APIkey chiave necessaria per accedere all'API.
	 * APIurl url necessario per accedere all'API.
	 */

	String APIkey = "02146a64e3858403deb292abe17b9b68";
	String APIurl = "https://api.openweathermap.org/data/2.5/weather?q=";

	/**
	 * Questo metodo crea un file contenente il nome della città, data, ora e la velocità del vento nel momento in cui viene richiamata.
	 * @param nomeCitta     Nome della città.
	 */

	@Override
	public void esportaSuFile(String nomeCitta)
	{
		Citta city = getSpeedW(nomeCitta);

		LocalDate todaysDate = LocalDate.now();

		String nomeFile = nomeCitta+"_"+todaysDate;

		String route = System.getProperty("user.dir")+nomeFile+".txt";

		try {


			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(route)));

			JSONObject salvataggio = new JSONObject();

			salvataggio = this.toJSON(city);

			out.println(salvataggio.toString());

			out.close();


		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	/**
	 * Questo metodo attraverso l'APIurl e APIkey ottiene le informazioni meteo (velocità del vento) dall'API di Open Weather.
	 * @param nomeCitta     Nome della città.
	 * 
	 * @return un JSONObject contenente le informazioni meteo sul vento della città scelta.
	 */

	@Override
	public JSONObject getWeatherInfo(String nomeCitta)
	{
		JSONObject speed;

		String url = APIurl + nomeCitta + "&appid=" +  APIkey;

		RestTemplate rt = new RestTemplate();

		speed = new JSONObject(rt.getForObject(url, String.class));

		return speed;
	}


	/**
	 * Questo metodo cerca all'interno del JSONObject il dato attuale sulla velocità del vento e la data di campionamento.
	 * @param nomeCitta     Nome della città.
	 * 
	 * @return un oggetto Citta con il proprio vettore contenente i dati sul vento settato.
	 */

	@Override
	public Citta getSpeedW(String nomeCitta)
	{

		JSONObject object = getWeatherInfo(nomeCitta);
		JSONObject weather = object.getJSONObject("wind");
		int time = 0;

		datoVento data = new datoVento();

		Citta city = new Citta(nomeCitta);
		city = getCityInfo(nomeCitta);


		for(int i=0; i<object.length(); i++)
		{
			time = (int) object.get("dt");
		}

		data.setTime(time);
		data.setSpeedVento(weather.getDouble("speed"));

		Vector<datoVento> vector = new Vector<datoVento>(weather.length());
		vector.add(data);

		city.setDatiVento(vector);

		return city;
	}


	/**
	 * Questo metodo cerca all'interno del JSONObject il nome della città e il relativo ID.
	 * @param nomeCitta     Nome della città.
	 * 
	 * @return un oggetto Citta con il nome e l'ID settati.
	 */

	@Override
	public Citta getCityInfo(String nomeCitta)
	{
		JSONObject object = getWeatherInfo(nomeCitta);
		Citta city = new Citta(nomeCitta);
		int id=0;
		String cityObj=null;

		for(int i=0; i<object.length(); i++)
		{
			cityObj = (String) object.get("name");

			id = (int) object.get("id");
		}

		city.setNome(cityObj);
		city.setIdOpenW(id);

		return city;
	}


	/**
	 * Questo metodo crea un JSONObject con i dati contenuti in city.
	 * @param city     		Oggetto città da cui prendere le informazioni.
	 *
	 * @return il JSONObject objcet contenente tutti i dati.
	 */

	@Override
	public JSONObject toJSON(Citta city)
	{
		JSONObject object = new JSONObject();
		object.put("name", city.getNome());
		object.put("Id", city.getIdOpenW());

		for(int i = 0;i <city.getDatiVento().size();i++)
		{
			object.put("data",(city.getDatiVento()).get(i).getTime());
			object.put("speed",(city.getDatiVento()).get(i).getSpeedVento());
		}

		return object;
	}


	/**
	 * Questo metodo crea (o aggiorna se già esistente) un file nominato: nomeCitta + "SalvataggioOrario.json", contenente tutte le informazioni richieste di una determinata città all'interno della cartella resources, il campionamento avviene con fascia oraria. 
	 * @param nomeCitta     	Nome della città.
	 */

	@Override
	public void salvataggioOrario(String nomeCitta)
	{

		String route = System.getProperty("user.dir") + "/src/main/resources/" + nomeCitta + "SalvataggioOrario.json";
		File file = new File(route);

		TimerTask timerTask = new TimerTask(){

			@Override
			public void run() {

				Citta city = getSpeedW(nomeCitta);

				JSONObject obj;

				obj = toJSON(city);


				try{
					if(!file.exists()) {
						file.createNewFile();
					}

					FileWriter fileWriter = new FileWriter(file, true);

					BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
					bufferedWriter.write(obj.toString());
					bufferedWriter.write("\n");

					bufferedWriter.close();

				} catch(IOException e) {
					System.out.println(e);
				}
			}
		};

		Timer timer = new Timer();
		timer.schedule(timerTask, 0, 3600000); 
	}

}
