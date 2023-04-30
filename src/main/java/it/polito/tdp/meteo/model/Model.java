package it.polito.tdp.meteo.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polito.tdp.meteo.DAO.MeteoDAO;

public class Model {
	
	private MeteoDAO dao;
	private Ricorsione3 ricorsione;
	
	
	

	public Model() {
		this.dao = new MeteoDAO();
		this.ricorsione = new Ricorsione3();
	}



	public List<RilevamentoMedio> getUmiditaMedia(int mese) {
		return this.dao.getRilevamentiMediMese(mese);
	}
	
	
	
	public List<String> trovaSequenza(int mese) {
		// Lettura di tutti i rilevamenti nel primo mese, da passare al metodo ricorsivo
		List<String> nomiLocalita = dao.getNomiLocalita();
		Map<String,Citta> rilevamentiLocalitaMese = new HashMap<String, Citta>();
		for (String s : nomiLocalita) {
			rilevamentiLocalitaMese.put(s, dao.getRilevamentiLocalitaMese(mese, s));
		}
		
		//ricorsione
		return ricorsione.trovaSequenza(rilevamentiLocalitaMese);			
	}
	
	
	public int getCosto() {
		return ricorsione.getBestCosto();			
	}
	

}
