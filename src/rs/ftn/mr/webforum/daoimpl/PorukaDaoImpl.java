package rs.ftn.mr.webforum.daoimpl;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import rs.ftn.mr.webforum.dao.PorukaDao;
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Poruka getByUserId(int idUser) {
		// TODO Auto-generated method stub
		return null;
	}

}
