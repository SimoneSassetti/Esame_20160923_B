package it.polito.tdp.porto.model;

import java.util.*;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.porto.db.PortoDAO;

public class Model {
	
	private List<Author> autori;
	private Map<Integer,Author> mappaAutori;
	private List<Paper> articoli;
	private Map<Integer,Paper> mappaArticoli;
	private SimpleGraph<Nodo,DefaultEdge> grafo;
	private Map<String,Rivista> riviste;
	
	PortoDAO dao;
	
	public Model(){
		dao=new PortoDAO();
		mappaArticoli=new HashMap<Integer,Paper>();
		mappaAutori=new HashMap<Integer,Author>();
		
		autori=dao.getTuttiAutori();
		for(Author a: autori){
			if(!mappaAutori.containsKey(a.getId())){
				mappaAutori.put(a.getId(), a);
			}
		}
	}
	
	public List<Paper> getPaper(){
		if(articoli==null){
			articoli=dao.getTuttiArticoli();
		}
		return articoli;
	}
	
	public void creaGrafo(){
		this.getPaper();
		riviste=new HashMap<String,Rivista>();
		
		grafo=new SimpleGraph<Nodo,DefaultEdge>(DefaultEdge.class);
		
		Graphs.addAllVertices(grafo, autori);
		Graphs.addAllVertices(grafo, articoli);
		
		//aggiungo gli archi
		for(Nodo n: grafo.vertexSet()){
			if(n instanceof Author){
				List<Paper> temp=dao.getArticoli((Author)n,mappaArticoli);
				((Author) n).addArticoli(temp);
				for(Paper p: temp){
					grafo.addEdge(n, p);
				}
			}
		}
		
		for(Nodo n: grafo.vertexSet()){
			if(n instanceof Paper){
				List<Author> temp=dao.getAutoriPerArticolo((Paper)n,mappaAutori);
				((Paper)n).addAutori(temp);
				String cod=((Paper) n).getIssn();
				if(!riviste.containsKey(cod)){
					Rivista r=new Rivista(cod,((Paper) n).getPublication());
					riviste.put(cod, r);
				}
			}
		}
	}
	
	public List<Rivista> getFrequenza(){
		for(Nodo n: grafo.vertexSet()){
			if(n instanceof Paper){
				String cod=((Paper) n).getIssn();
				if(riviste.containsKey(cod)){
					riviste.get(cod).setConteggio();
					riviste.get(cod).addPaper((Paper) n);
				}
			}
		}
		return new ArrayList(riviste.values());
	}
	
	public List<Rivista> findMinimalSet(){
		
		List<Rivista> parziale =new ArrayList<Rivista>();
		List<Rivista> migliore =new ArrayList<Rivista>();
		
		recursive(parziale,migliore);
		
		return migliore;
	}

	private void recursive(List<Rivista> parziale, List<Rivista> migliore) {
		
		System.out.println(migliore);
		Set<Author> setAutori=new HashSet<Author>(this.autori);
		for(Rivista r: parziale){
			for(Paper p: r.getArticoli()){
				setAutori.removeAll(p.getAutori());
			}
		}
		
		if(setAutori.isEmpty()){
			if(migliore.isEmpty()){
				migliore.addAll(parziale);
			}
			if(parziale.size()<migliore.size()){
				migliore.clear();
				migliore.addAll(parziale);
			}
		}
		
		for(Rivista r: riviste.values()){
			if(parziale.isEmpty() || r.getCodice().compareTo(parziale.get(parziale.size()-1).getCodice())>0){
				parziale.add(r);
				recursive(parziale,migliore);
				parziale.remove(r);
			}
		}
	}
	
	
}
