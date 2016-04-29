package it.polito.tdp.dizionario.model;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.dizionario.db.ParolaDAO;

public class DizionarioModel {

	List<String> parole;
	ParolaDAO p=new ParolaDAO();
	int f=0;
	int f2=0;
	
	protected SimpleGraph<String, DefaultEdge> wordGraph;
			
	
	
	
	public void cercaParole(int num){
		
		ParolaDAO p=new ParolaDAO();
		
		parole.addAll(p.trovaParole(num));
		
		System.out.println(parole);
		
	}
	
	public SimpleGraph<String,DefaultEdge> generaGrafo(int num){
		wordGraph=new SimpleGraph<String, DefaultEdge>(DefaultEdge.class);
		parole=new ArrayList<String>();
		parole.addAll(p.trovaParole(num));
		//aggiungi i vertici
		for(String s:parole){
			f++;
			wordGraph.addVertex(s);
		}
		System.out.println(f);
		//aggiungi archi con db
		/*for(String s:parole){
			for(String s2:jollyDB(s)){
				if(wordGraph.containsEdge(s, s2)==false && s.compareTo(s2)!=0){
					wordGraph.addEdge(s, s2);
					f2++;
				}
			}
		}*///aggiungi archi senza db
		for(String s:parole){
			for(String s2:jollyNoDB(s,parole)){
				if(wordGraph.containsEdge(s, s2)==false && s.compareTo(s2)!=0){
					wordGraph.addEdge(s, s2);
					f2++;
				}
			}
		}
		return wordGraph;
	}
	
	public List<String> jollyDB(String s){
		List<String> l=new ArrayList<String>();
		List<String> ris=new ArrayList<String>();
		
		for(int i=0;i<s.length();i++){
			StringBuilder sb=new StringBuilder(s);
			sb.setCharAt(i, '_');
			l.add(sb.toString());
		}
		ris.addAll(p.checkParole(l));
		
		return ris;
	}
		
	
	public List<String> jollyNoDB(String s,List<String> lista){
		
		List<String> ris=new ArrayList<String>();
		for(String temp:lista){
			if(temp.compareTo(s)!=0 && controllo(temp,s)==true){
				ris.add(temp);
			}
		}
		
		return ris;
	}
	
	public boolean controllo(String temp,String s){
		
		char[] tempC=temp.toCharArray();
		char[] sC=s.toCharArray();
		int r=0;
		for(int i=0;i<temp.length() || i<s.length();i++){
			if(tempC[i]!=sC[i]){
				r++;
			}
		}
		if(r>=2)
			return false;
		
		return true;
	}
	
}
