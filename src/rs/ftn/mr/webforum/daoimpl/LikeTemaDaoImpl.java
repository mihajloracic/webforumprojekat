package rs.ftn.mr.webforum.daoimpl;

import java.util.List;

import rs.ftn.mr.webforum.dao.LikeTemaDao;
import rs.ftn.mr.webforum.entities.LikeTema;
import rs.ftn.mr.webforum.entities.Tema;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import rs.ftn.mr.webforum.db.*;
import rs.ftn.mr.webforum.exceptions.DatabaseException;
import rs.ftn.mr.webforum.util.DbUtils;


public class LikeTemaDaoImpl implements LikeTemaDao {

	@Override
	public LikeTema selectById(int id) {
		String sql = "select * from like_tema where id=?";
		PreparedStatement p = null;
		ResultSet rs = null;
		try {
			p = DbConnection.getConnection().prepareStatement(sql);
			p.setInt(1, id);
			rs = p.executeQuery();
			LikeTema lt = new LikeTema();
			if (rs.next()) {
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

	@Override
	public LikeTema selectByTemaAndUser(int idTema, int idUser) {
		String sql = "select * from like_tema where id_tema=? and id_user=?";
		PreparedStatement p = null;
		ResultSet rs = null;
		LikeTema lt = null;
		try {
			p = DbConnection.getConnection().prepareStatement(sql);
			p.setInt(1, idTema);
			p.setInt(2, idUser);
			rs = p.executeQuery();
			if (rs.next()) {
				lt = new LikeTema();
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

// selectBy se koristi za selectByTema i selectByUser	
	private List<LikeTema> selectBy(int id, String sql){
    	PreparedStatement p = null;
		ResultSet rs = null;
		try {
			p = DbConnection.getConnection()
						.prepareStatement(sql);
			p.setInt(1,id); 
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
	public List<LikeTema> selectByTema(int idTema) {
	  	String sql = "select * from like_tema where id_tema=?";
	  	return selectBy(idTema,sql);

	}

	@Override
	public List<LikeTema> selectByUser(int idUser) {
	  	String sql = "select * from like_tema where id_user=?";
	  	return selectBy(idUser,sql);

	}

	private Boolean doAddNew(LikeTema lt){		
		String sql = "INSERT INTO LIKE_TEMA (ID_TEMA,ID_USER, LIKE) VALUES (?,?,?)";
		PreparedStatement p = null;
		try {			
			p = DbConnection.getConnection().prepareStatement(sql);			
			p.setInt(1, lt.getIdTema());
			p.setInt(2, lt.getIdUser());
			p.setBoolean(3,lt.getLike());
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
	public Boolean addNew(LikeTema lt) {
	  // ako like ne postoji, dodajem ga, ako postoji radi se update
	  if (selectByTemaAndUser(lt.getIdTema(), lt.getIdUser()) == null)		  
         return doAddNew(lt);
	  else
		 return update(lt); 	  
	}

	@Override
	public Boolean update(LikeTema lt) {
		String sql = "UPDATE LIKE_TEMA SET ID_TEMA=?, ID_USER=?, LIKE=? WHERE id_tema=? and id_user=? ";
		PreparedStatement p = null;
		try {			
			p = DbConnection.getConnection().prepareStatement(sql);			
			p.setInt(1, lt.getIdTema());
			p.setInt(2, lt.getIdUser());
			p.setBoolean(3,lt.getLike());
			p.setInt(4, lt.getIdTema());
			p.setInt(5, lt.getIdUser());
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
		String sql = "DELETE FROM LIKE_TEMA WHERE ID=?";
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
	
	private int getTemaLikeDislikeCount(int idTema, int likeDislike){
		String sql = "select count(*) broj from like_tema where id_tema=? and like=?";
		PreparedStatement p = null;
		ResultSet rs = null;
		try {
			p = DbConnection.getConnection().prepareStatement(sql);
			p.setInt(1, idTema);
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
	public int getTemaLikeCount(int idTema) {
        return getTemaLikeDislikeCount(idTema,1); 		
	}

	@Override
	public int getTemaDislikeCount(int idTema) {
        return getTemaLikeDislikeCount(idTema,0); 	
	}

	@Override
	public int getTemaLikeCount(Tema tema) {
		return getTemaLikeCount(tema.getId());
	}

	@Override
	public int getTemaDislikeCount(Tema tema) {
		return getTemaDislikeCount(tema.getId());
	}

	
	protected List<LikeTema> processSelectAll(ResultSet rs) throws SQLException {
		List<LikeTema> list = new ArrayList<LikeTema>();

		while (rs.next()) {
			LikeTema lt = new LikeTema();
			fillEntityFromResultSet(lt, rs);
			list.add(lt);
		}

		return list;
	}

    
	private void fillEntityFromResultSet(LikeTema lt, ResultSet rs) throws SQLException {
		lt.setId(rs.getInt("id"));
		lt.setIdTema(rs.getInt("id_tema"));
		lt.setIdUser(rs.getInt("id_user"));
		lt.setLike(rs.getBoolean("like"));		
	}



}
