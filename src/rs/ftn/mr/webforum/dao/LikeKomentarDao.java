package rs.ftn.mr.webforum.dao;

import rs.ftn.mr.webforum.entities.LikeKomentar;
import rs.ftn.mr.webforum.entities.Komentar;
import java.util.List;
public interface LikeKomentarDao {

	// vrati odredjeni Like
	public LikeKomentar selectById(int id);

	// vrati sve Lajkove za komentar
	public List<LikeKomentar> selectByKomentar(int idKomentar);
	public LikeKomentar selectByKomentarAndUser(int idKomentar, int idUser);
	public Boolean addNew(LikeKomentar lk);
	public Boolean update(LikeKomentar lk);
	public Boolean delete(int id);
	public int getLikeCount(int idKmentar);
	public int getLikeCount(Komentar komentar);
	public int getDislikeCount(int idKomentar);
	public int getDislikeCount(Komentar komentar);
}
