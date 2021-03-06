package rs.ftn.mr.webforum.dao;

import java.util.List;

import rs.ftn.mr.webforum.entities.Podforum;
import rs.ftn.mr.webforum.entities.Tema;
import rs.ftn.mr.webforum.entities.User;

public interface TemaDao {
	public Tema selectById(int id) ;
	public Tema selectByName(String naziv) ;
	public List selectAll(int podforumId) ;
	public List<Tema> search(String naslov,String sadrzaj, String autor, String podforum);	
    void delete(int id) ;
	public void update(Tema tema) ;
	public int addNew(Tema tema);
	public List getLikedByUser(int userId);
	public List<Tema>getFromForumsFollowedByUser(User user);
}
