package it.polito.tdp.meteo.DAO;

import java.util.List;
import java.util.Set;

import it.polito.tdp.meteo.model.Rilevamento;

public class TestMeteoDAO {

	public static void main(String[] args) {
		
		MeteoDAO dao = new MeteoDAO();

		//List<Rilevamento> list = dao.getAllRilevamenti();

		// STAMPA: localita, giorno, mese, anno, umidita (%)
		//for (Rilevamento r : list) {
		//	System.out.format("%-10s %2td/%2$2tm/%2$4tY %3d%%\n", r.getLocalita(), r.getData(), r.getUmidita());
		//}
		
		//List<Rilevamento> lista = dao.getAllRilevamentiLocalitaMese(1, "Genova");
		//for (Rilevamento r : lista) {
		//	System.out.format("%-10s %2td/%2$2tm/%2$4tY %3d%%\n", r.getLocalita(), r.getData(), r.getUmidita());
		//}
		
		//List<Rilevamento> lista2 = dao.getAvgRilevamentiMese(1);
		//for (Rilevamento r : lista2) {
		//	System.out.println(r.toString1());
		//}
	
//		System.out.println(dao.getAllRilevamentiLocalitaMese(5, "Milano"));
//		System.out.println(dao.getAvgRilevamentiLocalitaMese(5, "Milano"));
//		
//		System.out.println(dao.getAllRilevamentiLocalitaMese(5, "Torino"));
//		System.out.println(dao.getAvgRilevamentiLocalitaMese(5, "Torino"));
		
		
		List<Rilevamento> lista3 = dao.getAllRilevamentiMeseGiorno(1,1);
		for (Rilevamento r : lista3) {
			System.out.println(r.toString2());
		}
		
		Set<String> insieme = dao.getAllcities();
		for (String s : insieme) {
			System.out.println(s);
		}
		

	}

}
