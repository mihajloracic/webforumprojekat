package rs.ftn.mr.webforum.daoimpl;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import rs.ftn.mr.webforum.dao.PodforumDao;
import rs.ftn.mr.webforum.db.DbConnection;
import rs.ftn.mr.webforum.entities.Podforum;
import rs.ftn.mr.webforum.entities.User;
import rs.ftn.mr.webforum.exceptions.DatabaseException;
import rs.ftn.mr.webforum.util.DbUtils;

public class PodforumDaoImpl implements PodforumDao{

	@Override
	public User selectById(int podforumId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User selectByName(String podforumName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List selectAll() {
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
	protected List processSelectAll(ResultSet rs) throws SQLException {
		List list = new ArrayList();

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

}
