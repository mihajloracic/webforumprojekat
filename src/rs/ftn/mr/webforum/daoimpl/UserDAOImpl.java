package rs.ftn.mr.webforum.daoimpl;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import rs.ftn.mr.webforum.dao.UserDAO;
import rs.ftn.mr.webforum.db.*;
import rs.ftn.mr.webforum.entities.User;
import rs.ftn.mr.webforum.exceptions.DatabaseException;
import rs.ftn.mr.webforum.util.DbUtils;

public class UserDAOImpl implements UserDAO {

	@Override
	public User selectById(int userId) {

		String sql = "select * from user where id=?";
		PreparedStatement p = null;
		ResultSet rs = null;
		try {
			p = DbConnection.getConnection().prepareStatement(sql);
			p.setInt(1, userId);
			rs = p.executeQuery();
			User u = new User();
			if (rs.next()) {
				this.fillUserFromResultSet(u, rs);
			}
			return u;
		}
		catch (SQLException e) {
			throw new DatabaseException(e);
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
			User u = new User();
			fillUserFromResultSet(u, rs);
			list.add(u);
		}

		return list;
	}

    
	private void fillUserFromResultSet(User u, ResultSet rs) throws SQLException {
		u.setId(rs.getInt("id"));
		u.setUser(rs.getString("user"));
		u.setIme(rs.getString("ime"));
		u.setPrezime(rs.getString("prezime"));
		u.setLozinka(rs.getString("lozinka"));
		u.setUloga(rs.getString("uloga"));
		
		// TODO dodati setovanje ostalih atributa	
	}

	@Override
	public User selectByName(String userName) {

    	String sql = "select * from user where user=?";
    	PreparedStatement p = null;
		ResultSet rs = null;
		try {
			p = DbConnection.getConnection()
						.prepareStatement(sql);
			p.setString(1, userName);
			rs = p.executeQuery();
			User user = new User();
			fillUserFromResultSet(user, rs);            
			return user;
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
	public List selectAll() {

    	String sql = "select * from user";
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

	@Override
	public void delete(int userId) {
		
	}

	@Override
	public void update(User user) {
		String sql = "UPDATE user set uloga=? where id=? ";
		PreparedStatement p = null;
		try {			
			p = DbConnection.getConnection().prepareStatement(sql);			
			p.setString(1,user.getUloga());
			p.setInt(2, user.getId());
		} catch (SQLException e) {
			e.printStackTrace();
		}catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {

			e.printStackTrace();
		}
		finally {
			DbUtils.close(p);
		}
		
	}

	@Override
	public int addNew(User user) {
		// TODO Auto-generated method stub
		String sql = "INSERT INTO user (user,ime,prezime,lozinka,uloga,telefon,email,datum_registracije)"
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement p = null;
		try {
			
			p = DbConnection.getConnection().prepareStatement(sql);
			
			p.setString(1, user.getUser());
			p.setString(2, user.getIme());
			p.setString(3, user.getPrezime());
			p.setString(4, user.getLozinka());
			p.setString(5, "korisnik");
			p.setString(6, user.getTelefon());
			p.setString(7, user.getEmail());
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



	@Override
	public List<User> searchByNicName(String userName) {
    	String sql = "select * from user where user like ?";
    	PreparedStatement p = null;
		ResultSet rs = null;
		try {
			p = DbConnection.getConnection()
						.prepareStatement(sql);
			p.setString(1, "%"+userName+"%");			
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





}
