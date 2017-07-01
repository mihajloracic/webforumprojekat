package rs.ftn.mr.webforum.dao;

import java.util.List;

import rs.ftn.mr.webforum.entities.Podforum;
import rs.ftn.mr.webforum.entities.User;

public interface PodforumDao {
	public Podforum selectById(int podforumId) ;
	public Podforum selectByName(String podforumName) ;
	public List<Podforum> Search(String Naslov,String Opis, String moderatorUser);
	public List<Podforum> selectAll() ;
	public void delete(int podforumId) ;
	public void update(Podforum podforum) ;
	public int addNew(Podforum podforum);
}
