package it.polito.tdp.dizionario.model;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.dizionario.db.ParolaDAO;

public class DizionarioModel {

	List<String> parole=new ArrayList<String>();
	ParolaDAO p=new ParolaDAO();
	
	protected SimpleGraph<String, DefaultEdge> wordGraph = 
			new SimpleGraph<String, DefaultEdge>(DefaultEdge.class);
	
	
	
	public void cercaParole(int num){
		
		ParolaDAO p=new ParolaDAO();
		
		parole.addAll(p.trovaParole(num));
		
		System.out.println(parole);
		
	}
	
	public void generaGrafo(int num){
		
		parole.addAll(p.trovaParole(num));
		//aggiungi i vertici
		for(String s:parole){
			wordGraph.addVertex(s);
		}
		//aggiungi archi
		for(String s:parole){
			for(String s2:jolly(s)){
				if(wordGraph.containsEdge(s, s2)==false && s.compareTo(s2)!=0)
					wordGraph.addEdge(s, s2);
			}
		}
		
		
		
	}
	
	public List<String> jolly(String s){
		List<String> l=new ArrayList<String>();
		
		for(int i=0;i<s.length();i++){
			StringBuilder sb=new StringBuilder(s);
			sb.setCharAt(i, '_');
			l=p.checkParole(sb.toString());
		}
		
		return l;
	}
		
	
}
