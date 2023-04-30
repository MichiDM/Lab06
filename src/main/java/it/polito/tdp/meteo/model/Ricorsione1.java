package it.polito.tdp.meteo.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Questa prima classe implementa l'algoritmo ricorsivo che esplora tutte le sequenze di NUMERO_GIORNI_TOTALI città, senza imporre 
 * i vincoli che filtrano quali città visitare, e senza poi cercare la sequenza migliore.
 * Questo significa che, essendoci 3 città e cercando sequenze di NUMERO_GIORNI_TOTALI giorni, in totale la ricorsione troverà
 * 3^NUMERO_GIORNI_TOTALI sequenze. Se NUMERO_GIORNI_TOTALI = 15, questo significa 14348907 sequenze, che potrebbe richiedere
 * moooooolto tempo!!!
 * Dal momento che questo metodo serve solo come primo passo verso la soluzione dell'esercizio, nei parametri imposto 
 * NUMERO_GIORNI_TOTALI = 4, in modo che le sequenze totali siano solo 81, in modo poi da poter testare il metodo nel file
 * TestRicorsione.java in tempi ragionevoli.
 * @author carlo
 *
 */
public class Ricorsione1 {
	
	// PARAMETRI DELLA RICORSIONE
	private final static int COST = 100;
	private final static int NUMERO_GIORNI_CITTA_CONSECUTIVI_MIN = 3;
	private final static int NUMERO_GIORNI_CITTA_MAX = 6;
	private final static int NUMERO_GIORNI_TOTALI = 4;
	
	
	/* OUTOPUT. Invece di creare una (o più) variabile dentro la ricorsione che rappresentano la soluzione, di cui 
     * poi facciamo return, possiamo invece creare delle variabili private della classe
     * che aggiorniamo dentro la ricorsione. In questo modo nel metodo ricorsivo possiamo
     * evitare di passare ulteriori argomenti.
     */
	private List<List<String>> tutteSequenze;
	
	
	
	public static int getNumeroGiorniTotali() {
		return NUMERO_GIORNI_TOTALI;
	}



	/**
	 * Metodo che ricerca tutte le sequenze di città da visitare nei primi NUMERO_GIORNI_TOTALI giorni di mese. Questo é il metodo
	 * che al suo interno invoca la ricorsione
	 * @param mese
	 * @param rilevamentiCitta
	 * @return
	 */
	public List<List<String>> trovaSequenza(Map<String,Citta> rilevamentiCitta) {
		//inizializzo le variabili relative al risultato
		this.tutteSequenze = new ArrayList<List<String>>();
		
		cerca(new ArrayList<String>(), rilevamentiCitta); //<- chiamata alla ricorsione
		
		return this.tutteSequenze;
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
		if (parziale.size()==this.NUMERO_GIORNI_TOTALI) {
			tutteSequenze.add(new ArrayList<String>(parziale));
			return;
		}
		
		/*Se il metodo ricorsivo non è terminato, significa che la sequenza ancora non ha la lunghezza giusta
		 * Quindi procedo ad aggiungere un'altra città. In questa versione base, non mettiamo vincoli sulla città
		 * da aggiungere
		 */
		for (Citta c : rilevamentiCitta.values()) {
			parziale.add(c.getNome());			//< aggiungo la citta
			cerca(parziale, rilevamentiCitta);  //< richiamo la ricorsion
			parziale.remove(parziale.size()-1); //< backtracking
		}
		
	}
	
	
}
