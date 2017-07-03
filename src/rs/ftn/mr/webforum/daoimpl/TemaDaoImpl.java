package rs.ftn.mr.webforum.daoimpl;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import rs.ftn.mr.webforum.dao.LikeTemaDao;
import rs.ftn.mr.webforum.dao.TemaDao;
import rs.ftn.mr.webforum.db.DbConnection;
import rs.ftn.mr.webforum.entities.Podforum;
import rs.ftn.mr.webforum.entities.Tema;
import rs.ftn.mr.webforum.entities.User;
import rs.ftn.mr.webforum.util.DbUtils;

public class TemaDaoImpl implements TemaDao{

	@Override
	public Tema selectById(int id) {
		String sql = "select * from tema where id = ?";
    	PreparedStatement p = null;
		ResultSet rs = null;
		try {
			p = DbConnection.getConnection()
						.prepareStatement(sql);
			p.setInt(1, id);
			rs = p.executeQuery();
			Tema t = new Tema();
			fillTemaFromResultSet(t, rs);
			return t;
			
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
	public Tema selectByName(String naziv) {
		// TODO Auto-generated method stub
		return null;
	}
	
	protected List processSelectAll(ResultSet rs) throws SQLException {
		List list = new ArrayList();

		while (rs.next()) {
			Tema tema = new Tema();
			fillTemaFromResultSet(tema, rs);
			list.add(tema);
		}

		return list;
	}

	
	
	@Override
	public List selectAll(int podforumId) {
		String sql = "select * from tema where id_podforum = ?";
    	PreparedStatement p = null;
		ResultSet rs = null;
		try {
			p = DbConnection.getConnection()
						.prepareStatement(sql);
			p.setInt(1, podforumId);
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
	public void delete(int id) {
		String sql = "delete from tema where id=?";
    	PreparedStatement p = null;
		ResultSet rs = null;
		try {
			p = DbConnection.getConnection()
						.prepareStatement(sql);
			p.setInt(1, id);
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
	public void update(Tema tema) {
		// TODO Auto-generated method stub
	}
	private void fillTemaFromResultSet(Tema tema, ResultSet rs) throws SQLException {
		tema.setId(rs.getInt("id"));
		tema.setId_podforum(rs.getInt("id_podforum"));
		tema.setNaslov(rs.getString("naslov"));
		tema.setTekst(rs.getString("tekst"));
		tema.setLink(rs.getString("link"));
		tema.setSlika(rs.getString("slika"));
		tema.setAutor(rs.getInt("autor"));
		tema.setDatum_kreiranja(rs.getDate("datum_kreiranja"));
		tema.setTip(rs.getString("tip"));
		LikeTemaDao likeTemaDao = new LikeTemaDaoImpl();
		tema.setBrojLike(likeTemaDao.getTemaLikeCount(tema.getId()));
		tema.setBrojDislike(likeTemaDao.getTemaDislikeCount(tema));
	}

	@Override
	public int addNew(Tema tema) {
		// TODO Auto-generated method stub
		String sql = "INSERT INTO tema (id_podforum,naslov,tip,autor,tekst,slika,link,datum_kreiranja)"
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement p = null;
		try {
			
			p = DbConnection.getConnection().prepareStatement(sql);
			
			p.setInt(1, tema.getId_podforum());
			p.setString(2, tema.getNaslov());
			p.setString(3, tema.getTip());
			p.setInt(4, tema.getAutor());
			p.setString(5, tema.getTekst());
			p.setString(6, tema.getSlika()); // to do dodat sliku
			p.setString(7, tema.getLink());
			java.util.Date utilDate = new java.util.Date();
			p.setDate(8, new Date(utilDate.getTime()));
			p.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		finally {
			DbUtils.close(p);
		}
		
		return 0;
	}

	@Override
	public List<Tema> search(String naslov, String sadrzaj, String autor, String podforum) {
		String sql;
		sql= "select t.* from tema t left join user a on t.autor=a.id left join podforum pf on t.id_podforum=pf.id where naslov like ? and tekst like ? and a.user like ? and pf.naziv like ?";
    	PreparedStatement p = null;
		ResultSet rs = null;
		try {
			p = DbConnection.getConnection()
						.prepareStatement(sql);
			p.setString(1, "%"+naslov+"%");
			p.setString(2, "%"+sadrzaj+"%");
			p.setString(3, "%"+autor+"%");
			p.setString(4, "%"+podforum+"%");
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
	public List getLikedByUser(int userId) {
		String sql = "select t.* from tema t join like_tema lt on lt.id_tema=t.id where lt.id_user=?";
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
	public List<Tema> getFromForumsFollowedByUser(User user) {
		String sql;
		sql="select t.* from tema t join podforum pf on t.id_podforum=pf.id join podforum_user_pracenje pup on pf.id=pup.id_podforum where pup.id_user=? order by t.id desc";
		PreparedStatement p = null;
		ResultSet rs = null;
		try {
			p = DbConnection.getConnection()
						.prepareStatement(sql);
			p.setInt(1, user.getId());
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
