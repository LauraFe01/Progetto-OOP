package ConfrontoSpeedVento.services;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Vector;

//import org.json.simple.JSONArray;
//import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.scheduling.annotation.Scheduled;
import org.json.JSONArray;
import org.json.JSONObject;

import ConfrontoSpeedVento.model.Citta;
import ConfrontoSpeedVento.model.datoVento;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDate;

public class ImplServiziMeteo implements ServiziMeteo{

	String APIkey = "02146a64e3858403deb292abe17b9b68";
	String APIurl = "https://api.openweathermap.org/data/2.5/weather?q=";

	public ImplServiziMeteo()
	{

	}

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
		JSONObject speed = null;

		try {
			URL url = new URL(APIurl + nomeCitta + "&appid" + APIkey);
			URLConnection connection = url.openConnection();
			connection.connect();
			//InputStream in = connection.getInputStream();

			String data = "";
			String line = "";

			InputStreamReader inR = new InputStreamReader( connection.getInputStream() );
			BufferedReader buf = new BufferedReader( inR );

			try {
				while ((line = buf.readLine()) != null) {
					data+= line;
				}
			} finally {
				buf.close();
			}

			speed = (JSONObject) JSONValue.parseWithException(data);


		}catch(IOException | ParseException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
		}

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
		JSONObject time = object.getJSONObject("dt");
		datoVento data = new datoVento();

		Citta city = new Citta(nomeCitta);
		city = getCityInfo(nomeCitta);

		Vector<datoVento> vector = new Vector<datoVento>(weather.length());

		data.setSpeedVento(weather.getDouble("speed"));
		data.setTime(time.getLong("dt"));

		city.setDatiVento(vector);

		return city;
	}

	public Citta getCityInfo(String nomeCitta)
	{
		JSONObject object = getWindSpeed(nomeCitta);

		Citta city = new Citta(nomeCitta);

		JSONObject cityObj = object.getJSONObject("name");

		int id = (int) cityObj.get("id");
		city.setIdOpenW(id);

		return city;
	}

	public JSONObject toJSON(Citta city)
	{
		JSONObject object = new JSONObject();
		object.put("name", city.getNome());
		for(int i = 0;i <city.getDatiVento().size();i++)
		{
			object.put("data",(city.getDatiVento()).get(i).getTime());
			object.put("speed",(city.getDatiVento()).get(i).getSpeedVento());
		}

		return object;
	}

	@Scheduled(fixedRate=3600000)
	public String salvataggioOrario(String nomeCitta)
	{
		String route = System.getProperty("user.dir") + "/" + nomeCitta + "HourlyArchive.txt";
		//boolean exists = file.exists();
		//if(!exists)
		//{
		File file = new File(route);
		//}
		JSONObject speedWind = getWindSpeed(nomeCitta);

		try{
			if(!file.exists()) {
				file.createNewFile();
			}

			FileWriter fileWriter = new FileWriter(file, true);

			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			bufferedWriter.write(speedWind.toString());
			bufferedWriter.write("\n");

			bufferedWriter.close();

		} catch(IOException e) {
			System.out.println(e);
		}
		return "Il file è stato salvato";
	}

}
