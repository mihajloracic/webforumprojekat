package rs.ftn.mr.webforum.daoimpl;

import rs.ftn.mr.webforum.dao.ZalbaDao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import rs.ftn.mr.webforum.db.DbConnection;
import rs.ftn.mr.webforum.entities.Podforum;
import rs.ftn.mr.webforum.entities.Zalba;
import rs.ftn.mr.webforum.entities.User;
import rs.ftn.mr.webforum.util.DbUtils;

public class ZalbaDaoImpl implements ZalbaDao {

	@Override
	public Zalba selectById(int id) {
		String sql = "select * from zalba where id = ?";
    	PreparedStatement p = null;
		ResultSet rs = null;
		try {
			p = DbConnection.getConnection()
						.prepareStatement(sql);
			p.setInt(1, id);
			rs = p.executeQuery();
			Zalba t = new Zalba();
			fillEntityFromResultSet(t, rs);
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
	public List<Zalba> selectAll() {
		String sql = "select * from zalba order by id";
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
	public Zalba addNew(Zalba zalba) {

		String sql = "INSERT INTO zalba (user,tekst,datum,tip_entiteta,id_podforum,id_tema,id_komentar,preduzeta_akcija,odgovor_tekst)"
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement p = null;
		try {
			
			p = DbConnection.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			p.setInt(1, zalba.getIdUser() );
			p.setString(2, zalba.getTekst());
			p.setDate(3, zalba.getDatum());
			p.setString(4, zalba.getTipEntiteta());
			p.setInt(5, zalba.getIdPodforum());
			p.setInt(6, zalba.getIdTema());
			p.setInt(7, zalba.getIdKomentar());
			p.setString(8, zalba.getPreduzetaAkcija());
			p.setString(9, zalba.getOdgovorTekst()); 
			p.execute();
		       try (ResultSet generatedKeys = p.getGeneratedKeys()) {
		            if (generatedKeys.next()) {
		                zalba.setId(generatedKeys.getInt(1));
		                return zalba;
		            }
		            else {
		                throw new SQLException("Creating Komentar failed, no ID obtained.");
		            }
		        }			
		} catch (SQLException e) {
			e.printStackTrace();
		}catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		finally {
			DbUtils.close(p);
		}
		
		return zalba;
	}
	
	@Override
	public void update(Zalba zalba) {

		String sql = "UPDATE zalba SET user=?,tekst=?,datum=?,tip_entiteta=?,id_podforum=?,id_tema=?,id_komentar=?,preduzeta_akcija=?,odgovor_tekst=?"
		 +"WHERE id = ?";  

		PreparedStatement p = null;
		try {			
			p = DbConnection.getConnection().prepareStatement(sql);
			p.setInt(1, zalba.getIdUser() );
			p.setString(2, zalba.getTekst());
			p.setDate(3, zalba.getDatum());
			p.setString(4, zalba.getTipEntiteta());
			p.setInt(5, zalba.getIdPodforum());
			p.setInt(6, zalba.getIdTema());
			p.setInt(7, zalba.getIdKomentar());
			p.setString(8, zalba.getPreduzetaAkcija());
			p.setString(9, zalba.getOdgovorTekst()); 
			p.setInt(10, zalba.getId());
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
	public void delete(int id) {
		String sql = "delete from WHERE id = ?";  
		PreparedStatement p = null;
		try {			
			p = DbConnection.getConnection().prepareStatement(sql);
			p.setInt(1, id);
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
	public void delete(Zalba zalba) {
		delete(zalba.getId());
	}


	private void fillEntityFromResultSet(Zalba zalba, ResultSet rs) throws SQLException {
		zalba.setId(rs.getInt("id"));
		zalba.setIdUser(rs.getInt("user"));
		zalba.setTekst(rs.getString("tekst"));
		zalba.setDatum(rs.getDate("datum"));
		zalba.setTipEntiteta(rs.getString("tip_entiteta"));
		zalba.setIdPodforum(rs.getInt("id_podforum"));
		zalba.setIdTema(rs.getInt("id_tema"));
		zalba.setIdKomentar(rs.getInt("id_komentar"));
		zalba.setPreduzetaAkcija(rs.getString("preduzeta_akcija"));
		zalba.setOdgovorTekst(rs.getString("odgovor_tekst"));

	}

	protected List<Zalba> processSelectAll(ResultSet rs) throws SQLException {
		List<Zalba> list = new ArrayList<Zalba>();

		while (rs.next()) {
			Zalba Zalba = new Zalba();
			fillEntityFromResultSet(Zalba, rs);
			list.add(Zalba);
		}

		return list;
	}

	
}
