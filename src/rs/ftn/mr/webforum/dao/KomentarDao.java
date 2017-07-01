package rs.ftn.mr.webforum.dao;

import java.util.List;

import rs.ftn.mr.webforum.entities.Komentar;
import rs.ftn.mr.webforum.entities.Podforum;

public interface KomentarDao {
	public Komentar selectById(int komentarId) ;
	public List<Komentar> selectByIdTema(int idTema) ;
	public List<Komentar> selectByParentIdPost(int parentId,int postId);
	public void delete(int komentarId);
	public void update(Komentar komentar);
	public Komentar addNew(Komentar komentar);
}
