package com.ConfrontoSpeedVento.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class datoVento {

	private double speedVento;
	int time;

	public datoVento(double speedVento, int dataOra)
	{
		this.setSpeedVento(speedVento);
		this.time = dataOra;
	}

	public datoVento()
	{
		this.setSpeedVento(0);
	}

	public double getSpeedVento() {
		return speedVento;
	}

	public void setSpeedVento(double speedVento) {
		this.speedVento = speedVento;
	}

	public String getTime() {
		Date date = new Date(time*1000L); 
		SimpleDateFormat jdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
		jdf.setTimeZone(TimeZone.getTimeZone("GMT+1"));
		String java_date = jdf.format(date);
		return (java_date);
	}

	public void setTime (int time) {
		this.time = time;
	}


}
