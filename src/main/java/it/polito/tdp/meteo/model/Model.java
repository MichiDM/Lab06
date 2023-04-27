package it.polito.tdp.meteo.model;

import java.util.ArrayList;


import java.util.HashSet;
import java.util.List;

import java.util.Set;

import it.polito.tdp.meteo.DAO.MeteoDAO;

public class Model {
	
	private final static int COST = 100;
	private final static int NUMERO_GIORNI_CITTA_CONSECUTIVI_MIN = 3;
	private final static int NUMERO_GIORNI_CITTA_MAX = 6;
	private final static int NUMERO_GIORNI_TOTALI = 15;
	
	
	
	private MeteoDAO meteoDAO;

	public Model() {
		
		this.meteoDAO = new MeteoDAO();

	}

	
	public List<Rilevamento> getAvgRilevamentiMese(int mese) {
		return meteoDAO.getAvgRilevamentiMese(mese);
	}
	
	
	
	public List<Rilevamento> citySequence(int month){
		
		
		List<Rilevamento> parziale = new ArrayList<>();
		List<Rilevamento> soluzione = new ArrayList<>();
		int minimo = 999999;
	    Solve(month, 1, parziale, soluzione, minimo);
			 
	    return soluzione;

		
		
	}
	
	/* posiziona una città al giorno 'livello' compatibilmente con le città
	 * già presenti nei giorni precedenti ('parziale')
	 */
	public void Solve(int month, int level, List<Rilevamento> parziale, List<Rilevamento> soluzione, int minimo){
		
		
//      caso terminale
		if(level > NUMERO_GIORNI_TOTALI) {
					            
			if (CompatibileCompleta(parziale)) {
				int costo = Costo(parziale);
				if (costo < minimo) {
					minimo = costo;
					soluzione = new ArrayList<Rilevamento>(parziale);
					return;
				}		
			}	
			return;
		}
		
		// Genera le soluzioni possibili per questo giorno
		for(Rilevamento r : meteoDAO.getAllRilevamentiMeseGiorno(month, level)) {
			
			if(CompatibileParziale(r, parziale)){
				parziale.add(r);
				Solve(month, level+1, parziale, soluzione, minimo);
				parziale.remove(parziale.size()-1);
				}		
		}		
	}
				

		
	
	
	private boolean CompatibileParziale(Rilevamento r, List<Rilevamento> parziale) {
		
		boolean compatibile = true;
		int cnt = 0;
		
//		if (parziale.size() == 1) {
//			if ( !(parziale.get(0).getLocalita().compareTo(r.getLocalita())==0) ) {
//				compatibile = false;
//			}			
//		}
		
		if (parziale.size() >= NUMERO_GIORNI_CITTA_MAX) {
			for (Rilevamento ri : parziale) {
				if (ri.getLocalita().compareTo(r.getLocalita())==0) {
					cnt++;
				}
			}
		}
		
		if (cnt>5) {
			compatibile = false;
		}							
		return compatibile;	
	}
        
		
	
	private boolean CompatibileCompleta(List<Rilevamento> soluzioneCompleta) {
		
		boolean compatibile1 = false;
		boolean compatibile2 = true;
		int run = 1;
		List<Integer> runs = new ArrayList<>();
		List<Boolean> runsbool = new ArrayList<>();
		
		if (checkAllcities(soluzioneCompleta)) {
				compatibile1 = true;
			}	
		
	    for (int i = 0; i < soluzioneCompleta.size(); ++i) {   // n is the size of array
	        if (i + 1 < soluzioneCompleta.size() &&
	        		soluzioneCompleta.get(i).getLocalita().compareTo(soluzioneCompleta.get(i+1).getLocalita())==0) {
	            run++;        // increment run if consecutive elements are equal
	        } else {
	        	runs.add(run);
	            run = 1;      // reset run if they are not equal
	        }
	    }
		
		for (Integer r : runs) {
			if (r >= NUMERO_GIORNI_CITTA_CONSECUTIVI_MIN && r <= NUMERO_GIORNI_CITTA_MAX) {
				runsbool.add(true);
			}
			else {
				runsbool.add(false);
			}
		}
		
		for(boolean b : runsbool) {
			if(!b) {
				compatibile2 =  false;
			}
		}
		
		
		
		if (compatibile1 && compatibile2) {
			return true;
		}
		
		return false;	
	}
	
	
	private boolean checkAllcities(List<Rilevamento> sequence) {
		
		Set<String> insieme = new HashSet<>();
		for (Rilevamento r : sequence) {
			insieme.add(r.getLocalita());
		}
		
		if (insieme.contains("Genova") && insieme.contains("Torino") && insieme.contains("Milano"))
			return true;
		
		
		
		return false;
		
		
	}
	
	private Integer Costo(List<Rilevamento> soluzione) {
		
		int run = 0;
		int costoFisso = COST*run;
		int costoVariabile = 0;
		
		for (int i = 0; i < soluzione.size(); ++i) {   
	        if (i + 1 < soluzione.size() &&
	        		soluzione.get(i).getLocalita() == soluzione.get(i+1).getLocalita()) {
	            run = 0;      
	        } else {
	            run++;     
	        }
	    }
		
		for (Rilevamento r : soluzione) {
			
			costoVariabile += r.getUmidita();
		}
		
		int costoTotale = costoFisso + costoVariabile;
		
		return costoTotale;
			
	}
}
