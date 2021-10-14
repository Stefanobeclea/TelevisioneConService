package it.prova.dao.televisore;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import it.prova.dao.AbstractMySQLDAO;
import it.prova.model.Televisore;

public class TelevisoreDAOImpl extends AbstractMySQLDAO implements TelevisoreDAO {
	
	@Override
	public void setConnection(Connection connection) {
		this.connection = connection;		
	}

	@Override
	public List<Televisore> list() throws Exception {
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		ArrayList<Televisore> result = new ArrayList<Televisore>();
		Televisore userTemp = null;

		try (Statement ps = connection.createStatement(); ResultSet rs = ps.executeQuery("select * from televisore")) {

			while (rs.next()) {
				userTemp = new Televisore();
				userTemp.setMarca(rs.getString("marca"));
				userTemp.setModello(rs.getString("modello"));
				userTemp.setDataProduzione(rs.getDate("dataProduzione"));
				userTemp.setId(rs.getLong("ID"));
				result.add(userTemp);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	@Override
	public Televisore get(Long idInput) throws Exception {
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		if (idInput == null || idInput < 1)
			throw new Exception("Valore di input non ammesso.");

		Televisore result = null;
		try (PreparedStatement ps = connection.prepareStatement("select * from televisore where id=?")) {

			ps.setLong(1, idInput);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					result = new Televisore();
					result.setMarca(rs.getString("marca"));
					result.setModello(rs.getString("modello"));
					result.setDataProduzione(rs.getDate("dataProduzione"));
					result.setId(rs.getLong("ID"));
				} else {
					result = null;
				}
			} // niente catch qui

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	@Override
	public int update(Televisore input) throws Exception {
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		if (input == null || input.getId() == null || input.getId() < 1)
			throw new Exception("Valore di input non ammesso.");

		int result = 0;
		try (PreparedStatement ps = connection.prepareStatement(
				"UPDATE televisore SET marca=?, modello=?,  dataProduzione=? where id=?;")) {
			ps.setString(1, input.getMarca());
			ps.setString(2, input.getModello());
			// quando si fa il setDate serve un tipo java.sql.Date
			ps.setDate(3, new java.sql.Date(input.getDataProduzione().getTime()));
			ps.setLong(4, input.getId());
			result = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	@Override
	public int insert(Televisore input) throws Exception {
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		if (input == null)
			throw new Exception("Valore di input non ammesso.");

		int result = 0;
		try (PreparedStatement ps = connection.prepareStatement(
				"INSERT INTO televisore (marca, modello, dataProduzione) VALUES (?, ?, ?);")) {
			ps.setString(1, input.getMarca());
			ps.setString(2, input.getModello());
			// quando si fa il setDate serve un tipo java.sql.Date
			ps.setDate(3, new java.sql.Date(input.getDataProduzione().getTime()));
			result = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	@Override
	public int delete(Televisore input) throws Exception {
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		if (input == null || input.getId() == null || input.getId() < 1)
			throw new Exception("Valore di input non ammesso.");

		int result = 0;
		try (PreparedStatement ps = connection.prepareStatement("DELETE FROM televisore WHERE ID=?")) {
			ps.setLong(1, input.getId());
			result = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	@Override
	public List<Televisore> findByExample(Televisore example) throws Exception {
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		if (example == null)
			throw new Exception("Valore di input non ammesso.");

		ArrayList<Televisore> result = new ArrayList<Televisore>();
		Televisore userTemp = null;

		String query = "select * from televisore where 1=1 ";
		if (example.getMarca() != null && !example.getMarca().isEmpty()) {
			query += " and marca like '" + example.getMarca() + "%' ";
		}
		if (example.getModello() != null && !example.getModello().isEmpty()) {
			query += " and modello like '" + example.getModello() + "%' ";
		}

		if (example.getDataProduzione() != null) {
			query += " and dataProduzione ='" + new java.sql.Date(example.getDataProduzione().getTime()) + "' ";
		}

		try (Statement ps = connection.createStatement()) {
			ResultSet rs = ps.executeQuery(query);

			while (rs.next()) {
				userTemp = new Televisore();
				userTemp.setMarca(rs.getString("marca"));
				userTemp.setModello(rs.getString("modello"));
				userTemp.setDataProduzione(rs.getDate("dataProduzione"));
				userTemp.setId(rs.getLong("ID"));
				result.add(userTemp);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

}
