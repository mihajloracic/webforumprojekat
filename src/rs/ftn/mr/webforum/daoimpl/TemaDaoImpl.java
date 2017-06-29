package rs.ftn.mr.webforum.daoimpl;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import rs.ftn.mr.webforum.dao.TemaDao;
import rs.ftn.mr.webforum.db.DbConnection;
import rs.ftn.mr.webforum.entities.Tema;
import rs.ftn.mr.webforum.util.DbUtils;

public class TemaDaoImpl implements TemaDao{

	@Override
	public Tema selectById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Tema selectByName(String naziv) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List selectAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Tema tema) {
		// TODO Auto-generated method stub
	}

	@Override
	public int addNew(Tema tema) {
		// TODO Auto-generated method stub
		String sql = "INSERT INTO user (id_podforum,naslov,tip,autor,tekst,slika,link,datum_kreiranja)"
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement p = null;
		try {
			
			p = DbConnection.getConnection().prepareStatement(sql);
			
			p.setInt(1, tema.getId_podforum());
			p.setString(2, tema.getNaslov());
			p.setString(3, tema.getTip());
			p.setInt(4, tema.getAutor());
			p.setString(5, tema.getTekst());
			p.setString(6, null); // to do dodat sliku
			p.setString(7, tema.getLink());
			java.util.Date utilDate = new java.util.Date();
			p.setDate(8, new Date(utilDate.getTime()));
			p.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			DbUtils.close(p);
		}
		
		return 0;
	}

}
