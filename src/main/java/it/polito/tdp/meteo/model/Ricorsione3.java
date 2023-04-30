package it.polito.tdp.meteo.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Questa prima classe implementa l'algoritmo ricorsivo che esplora tutte le sequenze di NUMERO_GIORNI_TOTALI città, imponendo 
 * i vincoli richiesti dal testo dell'esercizio e restituendo la sequenza migliore.
 * @author carlo
 *
 */
public class Ricorsione3 {
	
	// PARAMETRI DELLA RICORSIONE
	private final static int COST = 100;
	private final static int NUMERO_GIORNI_CITTA_CONSECUTIVI_MIN = 3;
	private final static int NUMERO_GIORNI_CITTA_MAX = 6;
	private final static int NUMERO_GIORNI_TOTALI = 15;
	
	
	/* OUTOPUT. Invece di creare una (o più) variabile dentro la ricorsione che rappresentano la soluzione, di cui 
     * poi facciamo return, possiamo invece creare delle variabili private della classe
     * che aggiorniamo dentro la ricorsione. In questo modo nel metodo ricorsivo possiamo
     * evitare di passare ulteriori argomenti.
     */
	private List<String> bestSequenza; 	//< sequenza migliore
	private int bestCosto;			//< costo della sequenza migliore
	
	
	/*
	 * VARIABILI UTILI PER VINCOLI
	 */
	private Map<Citta, Integer> giorniTotaliInCitta; //< variabile di aiuto per tenere traccia dei giorni totali trascorsi in una citta
	
	
	
	
	public static int getNumeroGiorniTotali() {
		return NUMERO_GIORNI_TOTALI;
	}

	public int getBestCosto() {
		return bestCosto;
	}
	

	/**
	 * Metodo che ricerca le migliore sequenze di città di NUMERO_GIORNI_TOTALI, rispettando i vincoli richiesti 
	 * nell'esercizio
	 * @param mese
	 * @param rilevamentiCitta
	 * @return
	 */
	public List<String> trovaSequenza(Map<String, Citta> rilevamentiCitta) {
		//inizializzo le variabili relative al risultato
		this.bestSequenza = new ArrayList<String>();
		this.bestCosto = 0;
		
		//inizializzo le variabili contatore per i vincoli. In questo
		//caso ho scelto di usare una mappa che associa ad ogni citta il numero di giorni
		//trascorsi in un mese.
		giorniTotaliInCitta = new HashMap<Citta, Integer>();
		for(Citta c : rilevamentiCitta.values()) {
			giorniTotaliInCitta.put(c, 0);
		}
		
		cerca(new ArrayList<String>(), rilevamentiCitta); //<- chiamata alla ricorsione
		return this.bestSequenza;
	}

	
	/**
	 * Questo è il metodo ricorsivo usato da trova sequenza. Questa è la versione che trova tutte le possibili sequenze
	 * di città per i primi NUMERO_GIORNI_TOTALI giorni del mese, senza vincoli e senza considerare il costo
	 * @param parziale
	 * @param rilevamentiCitta
	 */
	private void cerca(List<String> parziale, Map<String, Citta> rilevamentiCitta) {
		
		//Condizione di terminazione: la sequenza parizale contiene già 15 giorni, quindi
		// è completa e non è necessario continuare ad esplorare questo ramo della ricorsione
		if (parziale.size()==Ricorsione3.NUMERO_GIORNI_TOTALI) {
			
			//controllo il costo. Se migliore di quello attualmente presente, aggiorno la soluzione
			int costo = calcolaCosto(parziale, rilevamentiCitta);
			if ( (this.bestCosto == 0) || (costo<this.bestCosto) ) {
				this.bestSequenza = new ArrayList<String>(parziale);
				this.bestCosto = costo;
			}
			return;
		}
		
		/*Se il metodo ricorsivo non è terminato, significa che la sequenza ancora non ha la lunghezza giusta
		 * Quindi procedo ad aggiungere un'altra città. In questa versione mettiamo dei vincoli sulla città
		 * da aggiungere
		 */
		for (Citta c : rilevamentiCitta.values()) {
			if(vincoloMinPermanenzaCitta(c, parziale) && vincoloMaxGiorniCitta(c)) { // impongo i vincoli
				parziale.add(c.getNome());			//< aggiungo la citta
				this.increaseContatoreMaxGiorniCitta(c);
				cerca(parziale, rilevamentiCitta);  //< richiamo la ricorsion
				parziale.remove(parziale.size()-1); //< backtracking
				this.decreaseContatoreMaxGiorniCitta(c);
			}
		}
		
	}
	
	
	/**
	 * Metodo che controlla il vincolo sul minimo numero di giorni consecutivi in cui una città deve essere visitata.
	 * Restituisce true se la città finora è stata visitata meno di NUMERO_GIORNI_CITTA_CONSECUTIVI_MIN,
	 * false altrimenti.
	 * @param c
	 * @return
	 */
	private boolean vincoloMinPermanenzaCitta(Citta c, List<String> parziale) {
		// se ancora non ho visitato nessuna città, ritorno true.
		if(parziale.size() < 1 ) {
			return true;
		}
		
		//quindi, verifico quale sia l'ultima città visitata e per quanti giorni consecutivamente
		int contatore = 0;
		String nomeUltimaCitta = parziale.get(parziale.size()-1);
		for (int i = 0; i < parziale.size()-1; i++ ) {
			if (parziale.get(parziale.size()-i-1) == nomeUltimaCitta) {
				contatore ++;
			} else {
				break;
			}
		}

		return (contatore >= NUMERO_GIORNI_CITTA_CONSECUTIVI_MIN) || 
				((contatore < NUMERO_GIORNI_CITTA_CONSECUTIVI_MIN) && (nomeUltimaCitta == c.getNome()) );
	}

	
	
	
	/**
	 * Metodo che controlla il vincolo sul massimo numero di giorni in cui una città può essere visitata.
	 * Restituisce true se la città finora è stata visitata meno di NUMERO_GIORNI_CITTA_MAX,
	 * false altrimenti.
	 * @param c
	 * @return
	 */
	private boolean vincoloMaxGiorniCitta(Citta c) {
		return this.giorniTotaliInCitta.get(c) < NUMERO_GIORNI_CITTA_MAX;
	}
	
	
	/**
	 * Metodo che incrementa il contatore di giorni totali in cui una città è stata visitata
	 * @param c
	 */
	private void increaseContatoreMaxGiorniCitta(Citta c) {
		int contatore = this.giorniTotaliInCitta.get(c);
		this.giorniTotaliInCitta.put(c, ++contatore);
	}
	
	
	/**
	 * Metodo che decrementa il contatore di giorni totali in cui una città è stata visitata
	 * @param c
	 */
	private void decreaseContatoreMaxGiorniCitta(Citta c) {
		int contatore = this.giorniTotaliInCitta.get(c);
		this.giorniTotaliInCitta.put(c, --contatore);
	}
	
	
	/**
	 * Metodo che calcola il costo di una sequenza
	 * @param c
	 */
	private int calcolaCosto(List<String> parziale, Map<String, Citta> rilevamenti) {
		int costo = 0;
		
		for(int giorno = 0; giorno < parziale.size(); giorno++) {
			/* Dalla mappa dei rilevamenti mensili per città, seleziono quello della città
			 * visitata al dì `giorno`.
			 */
			Citta cittaSelezionata = rilevamenti.get(parziale.get(giorno)); 
			// Poi ne estraggo il rilevamento corrispondente al dì `giorno`...
			Rilevamento rilevamentoGiorno = cittaSelezionata.getRilevamenti().get(giorno);
			//e quindi recupero l'umidità.
			costo += rilevamentoGiorno.getUmidita();
			
			//infine, aggiungo il costo di spostamento, se al giorno precedente si era su un'altra città
			
			if (giorno>0 && !parziale.get(giorno).equals(parziale.get(giorno-1))) {
				costo+=COST;
			}
		}
		
		return costo;
	}
	
}
