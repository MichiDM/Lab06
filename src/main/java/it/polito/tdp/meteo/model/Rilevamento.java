package it.polito.tdp.meteo.model;

import java.util.Date;

public class Rilevamento {
	
	private String localita;
	private Date data;
	private int umidita;
	private float umiditaMedia;

	public Rilevamento(String localita, Date data, int umidita) {
		this.localita = localita;
		this.data = data;
		this.umidita = umidita;
	}

	
	public Rilevamento(String localita, float umiditaMedia) {
		this.localita = localita;
		this.umiditaMedia = umiditaMedia;
	}


	public String getLocalita() {
		return localita;
	}

	public void setLocalita(String localita) {
		this.localita = localita;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public int getUmidita() {
		return umidita;
	}

	public void setUmidita(int umidita) {
		this.umidita = umidita;
	}
	
	public float getUmiditaMedia() {
		return umiditaMedia;
	}


	public void setUmiditaMedia(float umiditaMedia) {
		this.umiditaMedia = umiditaMedia;
	}
	

	public String toString1() {
	return localita + " " + umiditaMedia;
		}
	
	public String toString2( ) {
		
		return data + " " + localita + " " + umidita;
	}


	@Override
	public String toString() {
		return String.valueOf(umidita);
	}

	

}
