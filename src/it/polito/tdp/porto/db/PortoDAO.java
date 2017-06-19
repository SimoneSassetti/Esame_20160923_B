package it.polito.tdp.porto.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import it.polito.tdp.porto.model.Author;
import it.polito.tdp.porto.model.Paper;

public class PortoDAO {

	/*
	 * Dato l'id ottengo l'autore.
	 */
	public Author getAutore(int id) {

		final String sql = "SELECT * FROM author where id=?";

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, id);

			ResultSet rs = st.executeQuery();

			if (rs.next()) {

				Author autore = new Author(rs.getInt("id"), rs.getString("lastname"), rs.getString("firstname"));
				return autore;
			}

			return null;

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}

	/*
	 * Dato l'id ottengo l'articolo.
	 */
	public Paper getArticolo(int eprintid) {

		final String sql = "SELECT * FROM paper where eprintid=?";

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, eprintid);

			ResultSet rs = st.executeQuery();

			if (rs.next()) {
				Paper paper = new Paper(rs.getInt("eprintid"), rs.getString("title"), rs.getString("issn"),
						rs.getString("publication"), rs.getString("type"), rs.getString("types"));
				return paper;
			}

			return null;

		} catch (SQLException e) {
			 e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}

	public List<Author> getTuttiAutori() {

		final String sql = "SELECT * FROM author";
		List<Author> lista=new ArrayList<Author>();
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while(rs.next()) {
				Author autore = new Author(rs.getInt("id"), rs.getString("lastname"), rs.getString("firstname"));
				lista.add(autore);
			}
			
			conn.close();
			return lista;

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}

	public List<Paper> getTuttiArticoli() {
		final String sql = "SELECT * FROM paper where type='article'";

		List<Paper> lista=new ArrayList<Paper>();
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while(rs.next()) {
				Paper paper = new Paper(rs.getInt("eprintid"), rs.getString("title"), rs.getString("issn"),
						rs.getString("publication"), rs.getString("type"), rs.getString("types"));
				lista.add(paper);
			}
			
			conn.close();
			return lista;

		} catch (SQLException e) {
			 e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}

	public List<Paper> getArticoli(Author n, Map<Integer, Paper> mappa) {
		final String sql = "SELECT distinct p.eprintid\n" + 
				"FROM paper as p,creator as c,author as a\n" + 
				"where p.eprintid=c.eprintid and c.authorid=a.id and a.id=?";

		List<Paper> lista=new ArrayList<Paper>();
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, n.getId());
			ResultSet rs = st.executeQuery();

			while(rs.next()) {
				Paper paper = mappa.get(rs.getInt("eprintid"));
				if(paper!=null)
					lista.add(paper);
			}
			
			conn.close();
			return lista;

		} catch (SQLException e) {
			 e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
		
	}

	public List<Author> getAutoriPerArticolo(Paper n, Map<Integer, Author> mappa) {
		
		final String sql = "SELECT distinct a.id\n" + 
				"FROM paper as p,creator as c,author as a\n" + 
				"where p.eprintid=c.eprintid and c.authorid=a.id and p.eprintid=?";
		
		List<Author> lista=new ArrayList<Author>();
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, n.getEprintid());
			ResultSet rs = st.executeQuery();

			while(rs.next()) {
				Author autore = mappa.get((rs.getInt("id")));
				if(autore!=null)
					lista.add(autore);
			}
			conn.close();
			return lista;

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}
	
}