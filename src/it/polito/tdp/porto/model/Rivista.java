package it.polito.tdp.porto.model;

import java.util.*;

public class Rivista implements Comparable<Rivista>{
	
	private String codice;
	private String nome;
	private int conteggio;
	
	private List<Paper> articoli;
	
	public Rivista(String codice, String nome) {
		super();
		this.codice = codice;
		this.nome = nome;
		articoli=new ArrayList<Paper>();
	}
	public String getCodice() {
		return codice;
	}
	public void setCodice(String codice) {
		this.codice = codice;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}	
	public int getConteggio() {
		return conteggio;
	}
	public void setConteggio() {
		this.conteggio++;
	}
	public void addPaper(Paper p){
		if(!articoli.contains(p))
			articoli.add(p);
	}
	public List<Paper> getArticoli(){
		return articoli;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codice == null) ? 0 : codice.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Rivista other = (Rivista) obj;
		if (codice == null) {
			if (other.codice != null)
				return false;
		} else if (!codice.equals(other.codice))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return nome;
	}
	@Override
	public int compareTo(Rivista r) {
		return -(this.conteggio-r.conteggio);
	}
}
