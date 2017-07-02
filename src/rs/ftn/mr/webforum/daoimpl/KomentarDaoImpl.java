package rs.ftn.mr.webforum.daoimpl;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;

import rs.ftn.mr.webforum.dao.KomentarDao;
import rs.ftn.mr.webforum.dao.LikeKomentarDao;
import rs.ftn.mr.webforum.db.DbConnection;
import rs.ftn.mr.webforum.entities.Komentar;
import rs.ftn.mr.webforum.util.DbUtils;
import rs.ftn.mr.webforum.exceptions.DatabaseException;
public class KomentarDaoImpl implements KomentarDao {

	@Override
	public Komentar selectById(int komentarId) {
		String sql = "select * from komentar where id=?";
		PreparedStatement p = null;
		ResultSet rs = null;
		try {
			p = DbConnection.getConnection().prepareStatement(sql);
			p.setInt(1, komentarId);
			rs = p.executeQuery();
			Komentar komentar = new Komentar();
			if (rs.next()) {
				this.fillEntityFromResultSet(komentar, rs);
			}
			return komentar;
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
	public List<Komentar> selectByIdTema(int idTema) {
		String sql = "select * from komentar where id_tema=? and id_parent_komentar=-1";
		PreparedStatement p = null;
		ResultSet rs = null;
		try {
			p = DbConnection.getConnection().prepareStatement(sql);
			p.setInt(1, idTema);
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

	
	/* (non-Javadoc)
	 * @see rs.ftn.mr.webforum.dao.KomentarDao#selectByParentIdPost(int, int)
	 */
	@Override
	public List<Komentar>  selectByParentIdPost(int parentId, int temaId) {
		String sql = "select * from komentar where id_parent_komentar=? and id_tema=?";	
		PreparedStatement p = null;
		ResultSet rs = null;
		try {
			p = DbConnection.getConnection().prepareStatement(sql);
			p.setInt(1, parentId);
			p.setInt(2, temaId);
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
	public void delete(int komentarId) {

		String sql = "update komentar set obrisan=1 where id=?";
		PreparedStatement p = null;
		try {
			
			p = DbConnection.getConnection().prepareStatement(sql);
			p.setInt(1, komentarId); 
			p.execute();
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
	public void update(Komentar komentar) {

		String sql = "update komentar set id_tema=?,autor=?, datum_komentara=?, id_parent_komentar=?,tekst_komentar=?,izmenjen=?,obrisan=? where id=?";
		PreparedStatement p = null;
		try {
			
			p = DbConnection.getConnection().prepareStatement(sql);
			
			p.setInt(1, komentar.getId_tema());
			p.setInt(2, komentar.getAutor());
			java.util.Date utilDate = new java.util.Date();
			p.setDate(3, new Date(utilDate.getTime()));
			p.setInt(4, komentar.getId_parent_komentar());
			p.setString(5, komentar.getTekst_komentar());
			p.setBoolean(6, true);
			p.setBoolean(7, komentar.isObrisan());
			p.setInt(8, komentar.getId()); 
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
		


	}

	@Override
	public Komentar addNew(Komentar komentar) {
		// TODO Auto-generated method stub
				String sql = "INSERT INTO komentar (id_tema,autor,datum_komentara, id_parent_komentar,tekst_komentar,izmenjen,obrisan)"
						+ "VALUES (?, ?, ?, ?, ?, ?, ?)";
				PreparedStatement p = null;
				try {
					
					p = DbConnection.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
					p.setInt(1, komentar.getId_tema());
					p.setInt(2, komentar.getAutor());
					java.util.Date utilDate = new java.util.Date();
					p.setDate(3, new Date(utilDate.getTime()));
					p.setInt(4, komentar.getId_parent_komentar());
					p.setString(5, komentar.getTekst_komentar());
					p.setBoolean(6, komentar.isIzmenjen());
					p.setBoolean(7, komentar.isObrisan());
					p.execute();
				       try (ResultSet generatedKeys = p.getGeneratedKeys()) {
				            if (generatedKeys.next()) {
				                komentar.setId(generatedKeys.getInt(1));
				                return komentar;
				            }
				            else {
				                throw new SQLException("Creating Komentar failed, no ID obtained.");
				            }
				        }					
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
				
				return komentar;
	}

	
	protected List<Komentar> processSelectAll(ResultSet rs) throws SQLException {
		List<Komentar> list = new ArrayList<Komentar>();

		while (rs.next()) {
			Komentar komentar = new Komentar();
			fillEntityFromResultSet(komentar, rs);
			list.add(komentar);
		}

		return list;
	}

    
	private void fillEntityFromResultSet(Komentar k, ResultSet rs) throws SQLException {
	    k.setId(rs.getInt("id"));
	    k.setId_tema(rs.getInt("id_tema"));
	    k.setAutor(rs.getInt("autor"));
	    k.setDatum_kreiranja(rs.getDate("datum_komentara"));
	    k.setId_parent_komentar(rs.getInt("id_parent_komentar"));
	    k.setTekst_komentar(rs.getString("tekst_komentar"));
	    k.setIzmenjen(rs.getBoolean("izmenjen"));
	    k.setObrisan(rs.getBoolean("obrisan"));
	    LikeKomentarDao likeKomentarDao = new LikeKomentarDaoImpl();
	    k.setBrojLike(likeKomentarDao.getLikeCount(k));
	    k.setBrojDislike(likeKomentarDao.getDislikeCount(k));
	}


	@Override
	public List getLikedByUser(int userId) {
		// TODO Auto-generated method stub
		return null;
	}




}
