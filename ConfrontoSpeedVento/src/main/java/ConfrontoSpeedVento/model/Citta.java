package ConfrontoSpeedVento.model;

import java.util.*;

public class Citta {
	private String nome;
	//private long idOpenW;
	private Vector<datoVento> datiVento;

	/*public Citta(String nome,long idOpenW)
	{
		this.nome=nome;
		this.idOpenW=idOpenW;
		this.datiVento = new Vector<datoVento>();
	}
	*/
	public Citta(String nome)
	{
		this.nome=nome;
		this.datiVento = new Vector<datoVento>();
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public long getIdOpenW() {
		return idOpenW;
	}

	public void setIdOpenW(long idOpenW) {
		this.idOpenW = idOpenW;
	}

	public Vector<datoVento> getDatiVento()
	{
		return this.datiVento;
	}

	public void setDatiVento(Vector<datoVento> datiVento) {
		this.datiVento = datiVento; 
	}

}
