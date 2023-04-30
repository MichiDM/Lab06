package it.polito.tdp.meteo.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polito.tdp.meteo.DAO.MeteoDAO;

public class TestRicorsione {

	public static void main(String[] args) {
		Ricorsione1 ricorsione1 = new Ricorsione1();
		Ricorsione2 ricorsione2 = new Ricorsione2();
		Ricorsione3 ricorsione3 = new Ricorsione3();
		MeteoDAO dao = new MeteoDAO();
		
		// Lettura di tutti i rilevamenti nel primo mese, per fare una prova
		List<String> nomiLocalita = dao.getNomiLocalita();
		Map<String,Citta> rilevamentiLocalitaMese = new HashMap<String, Citta>();
		for (String s : nomiLocalita) {
			rilevamentiLocalitaMese.put(s, dao.getRilevamentiLocalitaMese(5, s));
		}
		
		
		
		// TEST RICORSIONE 1: ricorsione senza vincole e senza scegliere il costo migliore
		System.out.println("TEST RICORSIONE 1\n");
		List<List<String>> tutteSequenze = ricorsione1.trovaSequenza(rilevamentiLocalitaMese);
		System.out.println("Ho trovato tutte le sequenze dei primi " + Ricorsione1.getNumeroGiorniTotali() + " giorni");
		System.out.println("In totale sono " + tutteSequenze.size() + " sequenze");
		System.out.println("Le prime 10 sequenze sono:");
		for(int i = 0; i <10; i++) {
			System.out.println(tutteSequenze.get(i));
		}
		System.out.println("\n");
		
		// TEST RICORSIONE 2: ricorsione senza vincole e senza scegliere il costo migliore
		System.out.println("TEST RICORSIONE 2\n");
		List<List<String>> sequenzeVincolate = ricorsione2.trovaSequenza(rilevamentiLocalitaMese);
		System.out.println("Ho trovato tutte le sequenze VINCOLATE dei primi " + Ricorsione2.getNumeroGiorniTotali() + " giorni");
		System.out.println("In totale sono " + sequenzeVincolate.size() + " sequenze");
		System.out.println("Le prime 10 sequenze sono:");
		for(int i = 0; i <10; i++) {
			System.out.println(sequenzeVincolate.get(i));
		}
		
		
		// TEST RICORSIONE 3: ricorsione senza vincole e senza scegliere il costo migliore
		System.out.println("TEST RICORSIONE 3\n");
		List<String> sequenzaMigliore = ricorsione3.trovaSequenza(rilevamentiLocalitaMese);
		System.out.println("Ho trovato tutte la migliore sequenza per il mese 12:");
		System.out.println(sequenzaMigliore);
		System.out.println("Il costo della sequenza è: " + ricorsione3.getBestCosto());
	}

}
