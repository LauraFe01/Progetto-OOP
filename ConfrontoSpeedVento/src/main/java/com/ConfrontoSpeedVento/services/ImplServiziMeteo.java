package com.ConfrontoSpeedVento.services;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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
import org.springframework.web.client.RestTemplate;
import org.json.JSONArray;
import org.json.JSONObject;

import com.ConfrontoSpeedVento.model.Citta;
import com.ConfrontoSpeedVento.model.datoVento;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDate;

public class ImplServiziMeteo implements ServiziMeteo{

	String APIkey = "02146a64e3858403deb292abe17b9b68";
	String APIurl = "https://api.openweathermap.org/data/2.5/weather?q=";

	public ImplServiziMeteo()
	{

	}

	//Fatto, funziona ma ricontrolla, secondo me non serve
	public void esportaSuFile(String nomeCitta)
	{
		Citta city = getSpeedW(nomeCitta);

		//SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
		//String today = date.format(new Date());
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

	/*public void importaDaFile()
	{

		JSONParser parser = new JSONParser();
		try {
			Object obj = parser.parse(new FileReader("/Users/Archivio.json"));

			JSONObject jsonObject = (JSONObject) obj;

			JSONArray List = (JSONArray) jsonObject.get("Company List");

			Iterator<JSONObject> iterator = List.iterator();
			while (iterator.hasNext()) {
				System.out.println(iterator.next());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	 */
	public JSONObject getWindSpeed(String nomeCitta)
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
	 * Questo metodo utilizza getCityWeather per andare a selezionare le previsioni meteo ristrette (temperatura
	 * massima, minima, percepita e visibilità).
	 */

	public Citta getSpeedW(String nomeCitta)
	{

		JSONObject object = getWindSpeed(nomeCitta);
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
		JSONObject object = getWindSpeed(nomeCitta);
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
		//boolean exists = file.exists();

		String route = System.getProperty("user.dir") + "/" + nomeCitta + "SalvataggioOrario.json";
		File file = new File(route);

		TimerTask timerTask = new TimerTask(){

			@Override
			public void run() {
				//JSONObject speedWind = getWindSpeed(nomeCitta);
				
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
		timer.schedule(timerTask, 0, 60000);
	}

}
