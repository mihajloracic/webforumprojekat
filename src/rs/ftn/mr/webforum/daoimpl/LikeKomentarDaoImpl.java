package rs.ftn.mr.webforum.daoimpl;

import rs.ftn.mr.webforum.dao.LikeKomentarDao;
import rs.ftn.mr.webforum.entities.LikeKomentar;
import rs.ftn.mr.webforum.entities.Komentar;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import rs.ftn.mr.webforum.db.*;
import rs.ftn.mr.webforum.exceptions.DatabaseException;
import rs.ftn.mr.webforum.util.DbUtils;
public class LikeKomentarDaoImpl implements LikeKomentarDao{

	@Override
	public LikeKomentar selectById(int id) {
		String sql = "select * from like_komentar where id=?";
		PreparedStatement p = null;
		ResultSet rs = null;
		try {
			p = DbConnection.getConnection().prepareStatement(sql);
			p.setInt(1, id);
			rs = p.executeQuery();
			LikeKomentar l = new LikeKomentar();
					if (rs.next()) {
				this.fillEntityFromResultSet(l, rs);
			}
			return l;
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

	@Override
	public List<LikeKomentar> selectByKomentar(int idKomentar) {
	  	String sql = "select * from like_komentar where id_komentar=?";
	  	PreparedStatement p = null;
			ResultSet rs = null;
			try {
				p = DbConnection.getConnection()
							.prepareStatement(sql);
				p.setInt(1,idKomentar); 
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
	public LikeKomentar selectByKomentarAndUser(int idKomentar, int idUser) {
		String sql = "select * from like_komentar where id_Komentar=? and id_user=?";
		PreparedStatement p = null;
		ResultSet rs = null;
		LikeKomentar lt = null;
		try {
			p = DbConnection.getConnection().prepareStatement(sql);
			p.setInt(1, idKomentar);
			p.setInt(2, idUser);
			rs = p.executeQuery();
			if (rs.next()) {
				lt = new LikeKomentar();
				this.fillEntityFromResultSet(lt, rs);
			}
			return lt;
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
	private Boolean doAddNew(LikeKomentar lK){		
		String sql = "INSERT INTO LIKE_KOMENTAR (ID_KOMENTAR,ID_USER, LIKE) VALUES (?,?,?)";
		PreparedStatement p = null;
		try {			
			p = DbConnection.getConnection().prepareStatement(sql);			
			p.setInt(1, lK.getIdKomentar());
			p.setInt(2, lK.getIdUser());
			p.setBoolean(3,lK.getLike());
			return p.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {

			e.printStackTrace();
		}
		finally {
			DbUtils.close(p);
		}
		return null;		
	}	
	@Override
	public Boolean addNew(LikeKomentar l) {
		  // ako like ne postoji, dodajem ga, ako postoji radi se update
		  if (selectByKomentarAndUser(l.getIdKomentar(), l.getIdUser()) == null)		  
	         return doAddNew(l);
		  else
			 return update(l); 
	}

	@Override
	public Boolean update(LikeKomentar l) {
		String sql = "UPDATE LIKE_Komentar SET ID_Komentar=?, ID_USER=?, LIKE=? WHERE id_Komentar=? and id_user=? ";
		PreparedStatement p = null;
		try {			
			p = DbConnection.getConnection().prepareStatement(sql);			
			p.setInt(1, l.getIdKomentar());
			p.setInt(2, l.getIdUser());
			p.setBoolean(3,l.getLike());
			p.setInt(4, l.getIdKomentar());
			p.setInt(5, l.getIdUser());
			return p.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {

			e.printStackTrace();
		}
		finally {
			DbUtils.close(p);
		}
		return null;

	}

	@Override
	public Boolean delete(int id) {
		String sql = "DELETE FROM LIKE_Komentar WHERE ID=?";
		PreparedStatement p = null;
		try {			
			p = DbConnection.getConnection().prepareStatement(sql);			
			p.setInt(1,id);
			return p.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {

			e.printStackTrace();
		}
		finally {
			DbUtils.close(p);
		}
		return null;

	}
	private int getLikeDislikeCount(int idKomentar, int likeDislike){
		String sql = "select count(*) broj from like_komentar where id_komentar=? and like=?";
		PreparedStatement p = null;
		ResultSet rs = null;
		try {
			p = DbConnection.getConnection().prepareStatement(sql);
			p.setInt(1, idKomentar);
			p.setInt(2, likeDislike);
			rs = p.executeQuery();
			if (rs.next()) {
				return rs.getInt("broj");
			}
			
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
		return 0;
	}
	
	@Override
	public int getLikeCount(int idKomentar) {
		return getLikeDislikeCount(idKomentar, 1);
	}

	@Override
	public int getLikeCount(Komentar komentar) {
		
		return getLikeCount(komentar.getId());
	}

	@Override
	public int getDislikeCount(int idKomentar) {
		
		return getLikeDislikeCount(idKomentar,0);
	}

	@Override
	public int getDislikeCount(Komentar komentar) {
		
		return getDislikeCount(komentar.getId());
	}
	
	
	protected List<LikeKomentar> processSelectAll(ResultSet rs) throws SQLException {
		List<LikeKomentar> list = new ArrayList<LikeKomentar>();

		while (rs.next()) {
			LikeKomentar l = new LikeKomentar();
			fillEntityFromResultSet(l, rs);
			list.add(l);
		}

		return list;
	}

    
	private void fillEntityFromResultSet(LikeKomentar l, ResultSet rs) throws SQLException {
		l.setId(rs.getInt("id"));
		l.setIdKomentar(rs.getInt("id_komentar"));
		l.setIdUser(rs.getInt("id_user"));
		l.setLike(rs.getBoolean("like"));		
	}


}
