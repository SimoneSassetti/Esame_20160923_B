package it.polito.tdp.porto;

import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.porto.model.Model;
import it.polito.tdp.porto.model.Rivista;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class PortoController {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private TextField txtMatricolaStudente;

	@FXML
	private TextArea txtResult;

	@FXML
	void doFrequenzaRiviste(ActionEvent event) {
		model.creaGrafo();
		
		List<Rivista> lista=model.getFrequenza();
		Collections.sort(lista);
		
		txtResult.appendText("Numero di articoli pubblicati su ciascuna rivista:\n");
		for(Rivista r: lista){
			txtResult.appendText(r.toString()+": "+r.getConteggio()+"\n");
		}
	}

	@FXML
	void doVisualizzaRiviste(ActionEvent event) {
		txtResult.clear();
		
		List<Rivista> temp=model.findMinimalSet();
		txtResult.appendText("Insieme minimo di riviste:\n");
		txtResult.appendText(temp.toString());
	}

	@FXML
	void initialize() {
		assert txtMatricolaStudente != null : "fx:id=\"txtMatricolaStudente\" was not injected: check your FXML file 'DidatticaGestionale.fxml'.";
		assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'DidatticaGestionale.fxml'.";
	}
	
	Model model;
	public void setModel(Model model) {
		this.model=model;
	}
}
