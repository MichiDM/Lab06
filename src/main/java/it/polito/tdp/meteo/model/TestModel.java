package it.polito.tdp.meteo.model;

import java.util.List;

public class TestModel {

	public static void main(String[] args) {
		
		Model m = new Model();
		
		// System.out.println(m.getAvgRilevamentiMese(12));
		
		List<Rilevamento> sol = m.citySequence(5);
		
		for (Rilevamento r : sol ) {
			System.out.print(r.getLocalita()+" ");
		}
		

	}

}
