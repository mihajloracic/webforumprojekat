package rs.ftn.mr.webforum.dao;
import rs.ftn.mr.webforum.entities.LikeTema;
import rs.ftn.mr.webforum.entities.Tema;
import java.util.List;

public interface LikeTemaDao {

	// vrati odredjeni Like
	public LikeTema selectById(int id);
	// vrati like za temu i usera
	public LikeTema selectByTemaAndUser(int idTema, int idUser);
	
	// vrati sve Lajkove za temu
	public List<LikeTema> selectByTema(int idTema);
	// vrati sve lajkove za usera
	public List<LikeTema> selectByUser(int idUser);
	public Boolean addNew(LikeTema lt);
	public Boolean update(LikeTema lt);
	public Boolean delete(int id);
	public int getTemaLikeCount(int idTema);
	public int getTemaLikeCount(Tema tema);
	public int getTemaDislikeCount(int idTema);
	public int getTemaDislikeCount(Tema tema);
}
