package rs.ftn.mr.webforum.daoimpl;

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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(User user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int addNew(User user) {
		// TODO Auto-generated method stub
		return 0;
	}

}
