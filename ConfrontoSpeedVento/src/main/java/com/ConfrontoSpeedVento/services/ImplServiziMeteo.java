package com.ConfrontoSpeedVento.services;

import java.io.BufferedReader;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import javax.net.ssl.HttpsURLConnection;

//import org.json.simple.JSONArray;
//import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.ConfrontoSpeedVento.model.Citta;
import com.ConfrontoSpeedVento.model.datoVento;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDate;

/** Questa classe è l'implementazione dell'interfaccia ServiziMeteo.
 * @author Edoardo Cecchini
 * @author Laura Ferretti
 */

@Service
public class ImplServiziMeteo implements ServiziMeteo{

	/**
	 * @param APIkey chiave necessaria per accedere all'API.
	 * @param APIurl url necessario per accedere all'API.
	 */
	
	String APIkey = "02146a64e3858403deb292abe17b9b68";
	String APIurl = "https://api.openweathermap.org/data/2.5/weather?q=";

	/**
	 * Questo metodo crea un file nella ???.
	 * @param nomeCitta     Nome della città
	 * @param city 	   		Oggetto citta 
	 * @param nomeFile		Nome del file in cui vengono salvate le informazioni
	 * @param todaysDate	Data attuale
	 * @param route			Percorso dove salvare il file
	 * 
	 */

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
	 * @param nomeCitta     Nome della città
	 * @param speed 	   	JSONObject contenente la velocità del vento di una città
	 * @param url			Unione tra l'APIurl e l'APIkey che crea l'url finale per il collegamento con l'API
	 * @param rt			Variabile della classe RestTemplate necessaria per leggere il file JSON ottenuto dall'API
	 * @param route			Percorso dove salvare il file
	 * 
	 * @return un JSONObject contenente le informazioni meteo sul vento della città scelta.
	 */
	
	public JSONObject getWeatherInfo(String nomeCitta)
	{
		JSONObject speed;
		//String data = "";
		//try {
		String url = APIurl + nomeCitta + "&appid=" +  APIkey;

		RestTemplate rt = new RestTemplate();

		speed = new JSONObject(rt.getForObject(url, String.class));
		/*
			URLConnection connection = url.openConnection();
			connection.connect();
			InputStream in = connection.getInputStream();

			String data = "";
			String line = "";


			InputStreamReader inR = new InputStreamReader( in );
			BufferedReader buf = new BufferedReader( inR );

			//try {
				while ((line = buf.readLine()) != null) {
					data+= line;

				}
			//} finally {
				buf.close();
     		//}


			speed = (JSONObject) JSONValue.parseWithException(data);
			return speed;

		}catch(IOException | ParseException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
	    	e.printStackTrace();
		}
		 */
		return speed;


	}
	
	/**
	 * Questo metodo attraverso l'APIurl e APIkey ottiene le informazioni meteo (velocità del vento) dall'API di Open Weather.
	 * @param nomeCitta     Nome della città
	 * @param object 	   	JSONObject che contiene le informazioni prese dall'API
	 * @param weather		JSONObject che contiene le informazioni prese relative al vento
	 * @param rt			Variabile della classe RestTemplate necessaria per leggere il file JSON ottenuto dall'API
	 * @param route			Percorso dove salvare il file
	 * 
	 * @return un JSONObject contenente le informazioni meteo sul vento della città scelta.
	 */

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
