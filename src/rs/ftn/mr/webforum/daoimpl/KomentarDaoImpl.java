package rs.ftn.mr.webforum.daoimpl;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import rs.ftn.mr.webforum.dao.KomentarDao;
import rs.ftn.mr.webforum.db.DbConnection;
import rs.ftn.mr.webforum.entities.Komentar;
import rs.ftn.mr.webforum.entities.Podforum;
import rs.ftn.mr.webforum.util.DbUtils;

public class KomentarDaoImpl implements KomentarDao {

	@Override
	public Podforum selectById(int komentarId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Podforum selectByName(String komentarName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List selectAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List selectByParentIdPost(int parentId, int postId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(int komentarId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(Komentar komentar) {
		// TODO Auto-generated method stub

	}

	@Override
	public int addNew(Komentar komentar) {
		// TODO Auto-generated method stub
				String sql = "INSERT INTO komentar (id_tema,autor,datum_komentara, id_parent_komentar,tekst_komentar,izmenjen,obrisan)"
						+ "VALUES (?, ?, ?, ?, ?, ?, ?)";
				PreparedStatement p = null;
				try {
					
					p = DbConnection.getConnection().prepareStatement(sql);
					
					p.setInt(1, komentar.getId_tema());
					p.setInt(2, komentar.getAutor());
					java.util.Date utilDate = new java.util.Date();
					p.setDate(3, new Date(utilDate.getTime()));
					p.setInt(4, komentar.getId_parent_komentar());
					p.setString(5, komentar.getTekst_komentar());
					p.setBoolean(6, komentar.isIzmenjen());
					p.setBoolean(7, komentar.isObirsan());
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
