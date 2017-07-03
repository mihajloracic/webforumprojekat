package rs.ftn.mr.webforum.daoimpl;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import rs.ftn.mr.webforum.dao.PodforumDao;
import rs.ftn.mr.webforum.db.DbConnection;
import rs.ftn.mr.webforum.entities.Podforum;
import rs.ftn.mr.webforum.entities.User;
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
		String sql = "delete from podforum where id=?";
    	PreparedStatement p = null;
		ResultSet rs = null;
		try {
			p = DbConnection.getConnection()
						.prepareStatement(sql);
			p.setInt(1, podforumId);
			p.execute();

		} catch (SQLException e) {
			System.out.println(e.toString());
		}
		catch(Exception e){
			System.out.println(e.toString());
		}
		finally {
			DbUtils.close(rs, p);
		}
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
	public List<Podforum> Search(String Naslov, String Opis, String moderatorUser) {
		String sql;			
		sql = "select f.* from podforum f left join user u on f.odgovorni_moderator=u.id where f.naziv like ? and f.Opis like ?  and u.user like ?";
    	PreparedStatement p = null;
		ResultSet rs = null;
		try {
			p = DbConnection.getConnection()
						.prepareStatement(sql);
			p.setString(1, "%"+Naslov+"%");
			p.setString(2, "%"+Opis+"%");
			p.setString(3, "%"+moderatorUser+"%");

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
	public List<Podforum> followedByUser(int userId) {
		String sql = "select pf.* from podforum pf join podforum_user_pracenje pup on pf.id=pup.id_podforum where pup.id_user=?";
    	PreparedStatement p = null;
		ResultSet rs = null;
		try {
			p = DbConnection.getConnection()
						.prepareStatement(sql);
			p.setInt(1, userId);
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
	public List<Podforum> followedByUser(User user) {
		
		return followedByUser(user.getId());
	}

	@Override
	public int addFollowedByUser(Podforum podforum, User user) {
		String sql = "INSERT INTO podforum_user_pracenje (id_podforum,id_user)"
				+ "VALUES (?, ?)";
		PreparedStatement p = null;
		try {
			
			p = DbConnection.getConnection().prepareStatement(sql);
			
			p.setInt(1, podforum.getId());
			p.setInt(2, user.getId());
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
