package ConfrontoSpeedVento.model;

import java.util.Date;

public class datoVento {

	private double speedVento;
	Date time;
	
	public datoVento(double speedVento, long dataOra)
	{
		this.setSpeedVento(speedVento);
		this.time = new Date(dataOra);
	}

	public double getSpeedVento() {
		return speedVento;
	}

	public void setSpeedVento(double speedVento) {
		this.speedVento = speedVento;
	}
	
	public String getTime() {
		return time.toString();
	}

	public void setTime (Date time) {
		this.time = time;
	}
	
}
