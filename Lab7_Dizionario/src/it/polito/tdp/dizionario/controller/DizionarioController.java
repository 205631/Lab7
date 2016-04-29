package it.polito.tdp.dizionario.controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;

import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.dizionario.model.DizionarioModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class DizionarioController {
	
	private DizionarioModel dm;
	private SimpleGraph<String,DefaultEdge> wordGraph;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField txtNumero;

    @FXML
    private TextField txtParola;

    @FXML
    private Button btnGeneraGrafo;

    @FXML
    private Button btnTrovaVicini;

    @FXML
    private Button btnTrovaTutti;

    @FXML
    private TextArea txtResult;

    @FXML
    private Button btnReset;
    
    public void setModel(DizionarioModel dm){
    	this.dm=dm;
    }

    @FXML
    void doGeneraGrafo(ActionEvent event) {
    	try{
    		Integer.parseInt(txtNumero.getText());
    	}catch(NumberFormatException nfe){
    		txtResult.setText("ERRORE INSERIRE UN NUMERO");
    		return;
    	}
    	
    	wordGraph=dm.generaGrafo(Integer.parseInt(txtNumero.getText()));
    	String risV="";
    	String risE="";
    	for(String s:wordGraph.vertexSet()){
    		risV+=s+"\n";
    	}
    	for(DefaultEdge d:wordGraph.edgeSet()){
    		risE+=d.toString()+"\n";
    	}
    	btnTrovaVicini.setDisable(false);
    	btnTrovaTutti.setDisable(false);
    	
    	txtResult.setText("VERTICI:\n"+risV+"\nARCHI:\n"+risE);
    	
    }

    @FXML
    void doReset(ActionEvent event) {
    	txtNumero.setText("");
    	txtParola.setText("");
    	txtResult.setText("");
    	btnTrovaVicini.setDisable(true);
    	btnTrovaTutti.setDisable(true);
    }

    @FXML
    void doTrovaTutti(ActionEvent event) {
    	txtResult.setText("");
    	if(txtParola.getText().matches("[a-zA-Z]+")==false){
    		txtResult.setText("ERRORE INSERIRE UNA PAROLA");
    		return;
    	}
    	if(wordGraph.containsVertex(txtParola.getText())==false){
    		txtResult.setText("La parola '"+txtParola.getText()+"' non è presente nel grafo!");
    		return;
    	}
    	String ris=dm.trovaTutti(wordGraph,txtParola.getText());
    	if(ris.compareTo("")==0)
    		txtResult.setText("La parola '"+txtParola.getText()+"' non ha vicini!");
    	else
    		txtResult.setText("TUTTI I VERTICI VISITATI:\n"+ris);
    }

    @FXML
    void doTrovaVicini(ActionEvent event) {
    	String ris="";
    	txtResult.setText("");
    	if(txtParola.getText().matches("[a-zA-Z]+")==false){
    		txtResult.setText("ERRORE INSERIRE UNA PAROLA");
    		return;
    	}
    	if(wordGraph.containsVertex(txtParola.getText())==false){
    		txtResult.setText("La parola '"+txtParola.getText()+"' non è presente nel grafo!");
    		return;
    	}
    	ris=dm.trovaVicini(wordGraph,txtParola.getText());
    	if(ris.compareTo("")==0)
    		txtResult.setText("La parola '"+txtParola.getText()+"' non ha vicini!");
    	else
    		txtResult.setText("ARCHI:\n"+ris);
    	
    }

    @FXML
    void initialize() {
        assert txtNumero != null : "fx:id=\"txtNumero\" was not injected: check your FXML file 'Dizionario.fxml'.";
        assert txtParola != null : "fx:id=\"txtParola\" was not injected: check your FXML file 'Dizionario.fxml'.";
        assert btnGeneraGrafo != null : "fx:id=\"btnGeneraGrafo\" was not injected: check your FXML file 'Dizionario.fxml'.";
        assert btnTrovaVicini != null : "fx:id=\"btnTrovaVicini\" was not injected: check your FXML file 'Dizionario.fxml'.";
        assert btnTrovaTutti != null : "fx:id=\"btnTrovaTutti\" was not injected: check your FXML file 'Dizionario.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Dizionario.fxml'.";
        assert btnReset != null : "fx:id=\"btnReset\" was not injected: check your FXML file 'Dizionario.fxml'.";

    }
}
