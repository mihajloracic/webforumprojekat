package rs.ftn.mr.webforum.daoimpl;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import rs.ftn.mr.webforum.dao.PorukaDao;
import rs.ftn.mr.webforum.dao.UserDAO;
import rs.ftn.mr.webforum.db.DbConnection;
import rs.ftn.mr.webforum.entities.Poruka;
import rs.ftn.mr.webforum.util.DbUtils;

public class PorukaDaoImpl implements PorukaDao {

	@Override
	public int add(Poruka poruka) {
		// TODO Auto-generated method stub
				String sql = "INSERT INTO poruka (prima,salje,subject,tekst)"
						+ "VALUES (?, ?, ?, ?)";
				PreparedStatement p = null;
				try {
					
					p = DbConnection.getConnection().prepareStatement(sql);
					
					p.setInt(1, poruka.getPrima());
					p.setInt(2, poruka.getSalje());
					p.setString(3, poruka.getSubject());
					p.setString(4, poruka.getTekst());
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
	public Poruka getById(int idPoruke) {
		String sql = "select * from poruka where id=?";
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			statement = DbConnection.getConnection().prepareStatement(sql);
			statement.setInt(1, idPoruke);
			resultSet = statement.executeQuery();
			Poruka m = new Poruka();
			if (resultSet.next()) {
				mapEntityFromResultSet(m, resultSet);
			}
			return m;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtils.close(resultSet, statement);
		}
		return null;
	}

	@Override
	public List getByUserId(int idUser) {
		String sql = "select * from poruka where prima=?";
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			statement = DbConnection.getConnection().prepareStatement(sql);
			statement.setInt(1, idUser);
			resultSet = statement.executeQuery();
			return processSelectAll(resultSet);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtils.close(resultSet, statement);
		}
		return null;
	}
	protected List processSelectAll(ResultSet rs) throws SQLException {
		List<Poruka> list = new ArrayList<Poruka>();

		while (rs.next()) {
			Poruka m = new Poruka();
			mapEntityFromResultSet(m, rs);
			list.add(m);
		}

		return list;
	}
	
	private void mapEntityFromResultSet(Poruka m, ResultSet rs) throws SQLException {
		
		m.setId(rs.getInt("id"));
		m.setPrima(rs.getInt("prima"));
		m.setSalje(rs.getInt("salje"));
		UserDAO userDao = new UserDAOImpl();
		String name = userDao.selectById(m.getSalje()).getUser();
		m.setSenderName(name);
		m.setTekst(rs.getString("tekst"));
		m.setSubject(rs.getString("subject"));
	}

}
