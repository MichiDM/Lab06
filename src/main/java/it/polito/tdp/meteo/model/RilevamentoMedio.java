package it.polito.tdp.meteo.model;

import java.util.Date;

public class RilevamentoMedio {
	
	private String localita;
	private int mese;
	private double umidita;
	
	public RilevamentoMedio(String localita, int mese, double umidita) {
		this.localita = localita;
		this.mese = mese;
		this.umidita = umidita;
	}

	public String getLocalita() {
		return localita;
	}

	public int getMese() {
		return mese;
	}

	public double getUmidita() {
		return umidita;
	}

	// @Override
	// public String toString() {
	// return localita + " " + data + " " + umidita;
	// }

	@Override
	public String toString() {
		return String.valueOf(umidita);
	}

	

}
