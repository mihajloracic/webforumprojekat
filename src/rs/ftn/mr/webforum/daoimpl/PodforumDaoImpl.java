package rs.ftn.mr.webforum.daoimpl;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import rs.ftn.mr.webforum.dao.PodforumDao;
import rs.ftn.mr.webforum.db.DbConnection;
import rs.ftn.mr.webforum.entities.Podforum;
import rs.ftn.mr.webforum.exceptions.DatabaseException;
import rs.ftn.mr.webforum.util.DbUtils;

public class PodforumDaoImpl implements PodforumDao{

	@Override
	public Podforum selectById(int podforumId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Podforum selectByName(String podforumName) {
		String sql = "select * from podforum where naziv = ?";
    	PreparedStatement p = null;
		ResultSet rs = null;
		try {
			p = DbConnection.getConnection()
						.prepareStatement(sql);
			p.setString(1, podforumName);
			rs = p.executeQuery();
			Podforum podforum = new Podforum();
			fillUserFromResultSet(podforum, rs);
			return podforum;
		} catch (SQLException e) {
			System.out.println(e.toString());
		}
		catch(Exception e){
			System.out.println(e.toString());
		}
		finally {
			DbUtils.close(rs, p);
		}
    	return null;
	}

	@Override
	public List<Podforum> selectAll() {
		String sql = "select * from podforum";
    	PreparedStatement p = null;
		ResultSet rs = null;
		try {
			p = DbConnection.getConnection()
						.prepareStatement(sql);
			rs = p.executeQuery();

			return this.processSelectAll(rs);
		} catch (SQLException e) {
			System.out.println(e.toString());
		}
		catch(Exception e){
			System.out.println(e.toString());
		}
		finally {
			DbUtils.close(rs, p);
		}
    	return null;
	}
	protected List<Podforum> processSelectAll(ResultSet rs) throws SQLException {
		List<Podforum> list = new ArrayList<Podforum>();

		while (rs.next()) {
			Podforum podforum = new Podforum();
			fillUserFromResultSet(podforum, rs);
			list.add(podforum);
		}
		

		return list;
	}

	@Override
	public void delete(int podforumId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Podforum podforum) {
		// TODO Auto-generated method stub
		
	}
	
	private void fillUserFromResultSet(Podforum p, ResultSet rs) throws SQLException {
		p.setId(rs.getInt("id"));
		p.setNaziv(rs.getString("naziv"));
		p.setOpis(rs.getString("opis"));
		p.setOdgovorniModerator(rs.getInt("odgovorni_moderator"));
		p.setSpisakPravila(rs.getString("spisak_pravila"));
		// TODO dodati setovanje ostalih atributa	
	}
	
	@Override
	public int addNew(Podforum podforum) {
		// TODO Auto-generated method stub
		String sql = "INSERT INTO podforum (naziv,opis,spisak_pravila,odgovorni_moderator)"
				+ "VALUES (?, ?, ?, ?)";
		PreparedStatement p = null;
		try {
			
			p = DbConnection.getConnection().prepareStatement(sql);
			
			p.setString(1, podforum.getNaziv());
			p.setString(2, podforum.getOpis());
			p.setString(3, podforum.getSpisakPravila());
			p.setInt(4, podforum.getOdgovorniModerator());
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

	@Override
	public List<Podforum> Search(String Naslov, String Opis, int idModerator) {
		String sql;
		if (idModerator != 0){			
		   sql = "select * from podforum where naziv like '%?%' and Opis like '%?%' and odgovorni_moderator=?";
		}   
		else
		   sql = "select * from podforum where naziv like '%?%' and Opis like '%?%' and odgovorni_moderator=?";			
    	PreparedStatement p = null;
		ResultSet rs = null;
		try {
			p = DbConnection.getConnection()
						.prepareStatement(sql);
			p.setString(1, Naslov);
			p.setString(2, Opis);
			if (idModerator != 0) 
				p.setInt(3, idModerator);
			rs = p.executeQuery();

			return this.processSelectAll(rs);
		} catch (SQLException e) {
			System.out.println(e.toString());
		}
		catch(Exception e){
			System.out.println(e.toString());
		}
		finally {
			DbUtils.close(rs, p);
		}
    	return null;
	}
	@Override
	public List<Podforum> Search(String Naslov, String Opis) {
		return Search(Naslov, Opis,0);
	}


}
