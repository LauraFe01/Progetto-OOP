package com.ConfrontoSpeedVento.services;

import java.util.Vector;

import org.json.JSONObject;

import com.ConfrontoSpeedVento.model.Citta;

public interface ServiziMeteo {
	
	public abstract void esportaSuFile(String nomeCitta);
	public abstract JSONObject getWeatherInfo(String nomeCitta);
	public abstract Citta getSpeedW(String nomeCitta);
	public abstract Citta getCityInfo(String nomeCitta);
	public abstract void salvataggioOrario(String nomeCitta);
	public abstract JSONObject toJSON(Citta city);
}
