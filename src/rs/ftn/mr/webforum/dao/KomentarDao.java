package rs.ftn.mr.webforum.dao;

import java.util.List;

import rs.ftn.mr.webforum.entities.Komentar;
import rs.ftn.mr.webforum.entities.Podforum;

public interface KomentarDao {
	public Podforum selectById(int komentarId) ;
	public Podforum selectByName(String komentarName) ;
	public List selectAll();
	public List selectByParentIdPost(int parentId,int postId);
	public void delete(int komentarId);
	public void update(Komentar komentar);
	public int addNew(Komentar komentar);
}
