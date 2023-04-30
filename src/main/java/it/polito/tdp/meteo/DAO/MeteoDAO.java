package it.polito.tdp.meteo.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.meteo.model.Citta;
import it.polito.tdp.meteo.model.Rilevamento;
import it.polito.tdp.meteo.model.RilevamentoMedio;

public class MeteoDAO {
	
	
	/**
	 * Metodo che legge tutti i rilevamenti nel database, restituendo una lista
	 * @return List<Rilevamento>
	 * @throws RuntimeException
	 */
	public List<Rilevamento> getAllRilevamenti() {
		final String sql = "SELECT Localita, Data, Umidita FROM situazione ORDER BY data ASC";
		List<Rilevamento> rilevamenti = new ArrayList<Rilevamento>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Rilevamento r = new Rilevamento(rs.getString("Localita"), rs.getTimestamp("Data").toLocalDateTime(), rs.getInt("Umidita"));
				rilevamenti.add(r);
			}

			conn.close();
			return rilevamenti;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	
	
	/**
	 * Metodo che legge i rilevamenti dal database i rilevamenti medi per citta' in un mese, restituendo una lista
	 * @param int il mese da selezionare
	 * @return List<Rilevamento>
	 * @throws RuntimeException
	 */
	public List<RilevamentoMedio> getRilevamentiMediMese(int mese) {
		final String sql = "SELECT localita, AVG(umidita) as umiditaMedia "
				+ "FROM situazione "
				+ "WHERE MONTH(data)=? "
				+ "GROUP By localita";
		List<RilevamentoMedio> rilevamenti = new ArrayList<RilevamentoMedio>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, mese);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				RilevamentoMedio r = new RilevamentoMedio(rs.getString("Localita"), mese, rs.getDouble("umiditaMedia"));
				rilevamenti.add(r);
			}

			conn.close();
			return rilevamenti;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	
	
	/**
	 * metodo che legge tutti i rilevamenti di una localita in un mese e li restituisce in un oggetto Citta
	 * @param mese
	 * @param localita
	 * @return
	 */
	public Citta getRilevamentiLocalitaMese(int mese, String localita) {
		final String sql = "SELECT Data, Umidita FROM situazione WHERE MONTH(Data) = ? AND Localita = ? ORDER BY data ASC";
		Citta risultato = new Citta(localita, mese);
		List<Rilevamento> rilevamenti = new ArrayList<Rilevamento>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, mese);
			st.setString(2, localita);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Rilevamento r = new Rilevamento(localita, rs.getTimestamp("Data").toLocalDateTime(), rs.getInt("Umidita"));
				rilevamenti.add(r);
			}
			risultato.setRilevamenti(rilevamenti);

			conn.close();
			return risultato;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	
	
	/**
	 * metodo che legge i nomi di tutte le localita. Per questo esercizio non è strettamente necessario
	 * perché già sappiamo che ci sono solo tre città e conosciamo i loro nomi. Questa soluzione è più
	 * generale, e può essere usata quando non sappiamo esattamente quante e quali città ci siano
	 * @return
	 */
	public List<String> getNomiLocalita() {
		final String sql = "SELECT Localita FROM situazione Group BY Localita";
		List<String> risultato = new ArrayList<String>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				risultato.add(rs.getString("Localita"));
			}
			conn.close();
			return risultato;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	


}
