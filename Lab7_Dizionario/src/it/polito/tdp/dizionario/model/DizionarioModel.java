package it.polito.tdp.dizionario.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.traverse.DepthFirstIterator;
import org.jgrapht.traverse.GraphIterator;

import it.polito.tdp.dizionario.db.ParolaDAO;

public class DizionarioModel {

	List<String> parole;
	ParolaDAO p=new ParolaDAO();
	int f;
	int f2;
	
	protected SimpleGraph<String, DefaultEdge> wordGraph;
			
	
	
	
	public void cercaParole(int num){
		
		ParolaDAO p=new ParolaDAO();
		
		parole.addAll(p.trovaParole(num));
		
		System.out.println(parole);
		
	}
	
	public SimpleGraph<String,DefaultEdge> generaGrafo(int num){
		f=0;
		f2=0;
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
	
	public String trovaTutti(SimpleGraph<String,DefaultEdge> sg,String s){
		String ris="";
		int i=0;
		List<String> VertixVisited=new ArrayList<String>();
			GraphIterator<String, DefaultEdge> dfv = new DepthFirstIterator<String, DefaultEdge>(sg, s);
			while (dfv.hasNext()) {
				String t= dfv.next();
				if(s.compareTo(t)!=0){
					VertixVisited.add(t);
					i++;
				}
			}
		for(String ss:VertixVisited){
			ris+=ss+"\n";
		}
		System.out.println(i);
		return ris;
	}
	
	public String trovaVicini(SimpleGraph<String,DefaultEdge> sg,String s){
		Set<DefaultEdge> archi=sg.edgesOf(s);
		String ris="";
    	for(DefaultEdge d:archi){
    		if(sg.getEdgeTarget(d).compareTo(s)==0)
    			ris+=sg.getEdgeSource(d)+"\n";
    		else
    			ris+=sg.getEdgeTarget(d)+"\n";
    	}
    	return ris;
	}
}
