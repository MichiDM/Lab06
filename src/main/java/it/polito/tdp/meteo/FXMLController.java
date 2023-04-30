/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.meteo;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.meteo.model.Model;
import it.polito.tdp.meteo.model.Rilevamento;
import it.polito.tdp.meteo.model.RilevamentoMedio;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;

public class FXMLController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="boxMese"
    private ChoiceBox<Integer> boxMese; // Value injected by FXMLLoader

    @FXML // fx:id="btnUmidita"
    private Button btnUmidita; // Value injected by FXMLLoader

    @FXML // fx:id="btnCalcola"
    private Button btnCalcola; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCalcolaSequenza(ActionEvent event) {
    	Integer mese = this.boxMese.getValue();
    	if (mese == null) {
    		this.txtResult.setText("Selezionare un mese!");
    		return;
    	}
    	List<String> sequenzaMigliore = this.model.trovaSequenza(mese);
    	this.txtResult.setText("La migliore sequenza per il mese "+ mese + " è:\n");
    	this.txtResult.appendText(sequenzaMigliore + "\n");
    	this.txtResult.appendText("Il costo della sequenza é " + this.model.getCosto() +"\n");
    }

    
    
    
    @FXML
    void doCalcolaUmidita(ActionEvent event) {
    	Integer mese = this.boxMese.getValue();
    	if (mese == null) {
    		this.txtResult.setText("Selezionare un mese!");
    		return;
    	}
    	List<RilevamentoMedio> rilevamentiMedi = this.model.getUmiditaMedia(mese);
    	this.txtResult.setText("I rilevamenti medi per il mese "+ mese + " sono:\n");
    	for (RilevamentoMedio r : rilevamentiMedi) {
    		this.txtResult.appendText(r.getLocalita() + ":\t" + r.getUmidita() + "\n");
    	}
    }
    
    
    
    
    public void setModel(Model newModel) {
    	this.model = newModel;
    }

    
    
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert boxMese != null : "fx:id=\"boxMese\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnUmidita != null : "fx:id=\"btnUmidita\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCalcola != null : "fx:id=\"btnCalcola\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
        
        for (int mese=1; mese <=12; mese ++) {
    		boxMese.getItems().add(mese);
    	}

    }
}

