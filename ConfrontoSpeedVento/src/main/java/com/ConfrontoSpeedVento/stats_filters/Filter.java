package com.ConfrontoSpeedVento.stats_filters;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Vector;

import org.json.JSONObject;
import org.json.JSONTokener;

public class Filter {

	private String nomeCitta;
	private String dataInizio;
	private String dataFine;

	public Filter(String nomeCitta, String dataInizio, String dataFine)
	{
		this.nomeCitta= nomeCitta;
		this.dataInizio = dataInizio;
		this.dataFine = dataFine;
	}
	

	public Vector<Double> FilterCustomRange()
	{
		String route = System.getProperty("user.dir") + "/src/main/resources/" + nomeCitta + "SalvataggioOrario.json";

		Vector<Double> datiSpeed=new Vector<Double>();

		boolean flag=false;
		String line = null;

		try {
			FileReader fileReader = new FileReader(route);

			BufferedReader bufferedReader = new BufferedReader(fileReader);

			while((line = bufferedReader.readLine()) != null) {

				JSONTokener tokener = new JSONTokener(line);
				JSONObject object = new JSONObject(tokener);

				if(dataInizio.equals((String)object.get("data")))
				{  
					flag=true;
				}

				if(flag==true)
				{
					datiSpeed.add((double)object.get("speed"));
				}

				if(dataFine.equals((String) object.get("data")))
				{
					flag=false;
				}

			}
			bufferedReader.close(); 

			return datiSpeed;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return datiSpeed;
	}
}
