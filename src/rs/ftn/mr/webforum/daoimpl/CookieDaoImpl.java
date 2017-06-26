package rs.ftn.mr.webforum.daoimpl;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.Cookie;

import rs.ftn.mr.webforum.dao.CookieDao;
import rs.ftn.mr.webforum.db.DbConnection;
import rs.ftn.mr.webforum.entities.User;
import rs.ftn.mr.webforum.util.DbUtils;

public class CookieDaoImpl implements CookieDao {

	@Override
	public void add(Cookie cookie, int userId) {
		String sql = "INSERT INTO user_cookie (cookie,user_id)"
				+ "VALUES (?, ?)";
		PreparedStatement p = null;
		try {
			p = DbConnection.getConnection().prepareStatement(sql);			
			p.setString(1, cookie.getValue());
			p.setInt(2, userId);
			p.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public int getById(Cookie cookie) {
    	String sql = "select * from user_cookie where cookie=?";
    	PreparedStatement p = null;
		ResultSet rs = null;
		try {
			p = DbConnection.getConnection()
						.prepareStatement(sql);
			p.setString(1, cookie.getValue());
			rs = p.executeQuery();			            
			return rs.getInt("user_id");
		} catch (SQLException e) {
			System.out.println(e.toString());
		}
		catch(Exception e){
			System.out.println(e.toString());
		}
		finally {
			DbUtils.close(rs, p);
		}
		return 0;
	}
}
