package rs.ftn.mr.webforum.dao;

import java.util.List;

import rs.ftn.mr.webforum.entities.Podforum;
import rs.ftn.mr.webforum.entities.User;

public interface PodforumDao {
	public User selectById(int podforumId) ;
	public User selectByName(String podforumName) ;
	public List selectAll() ;
	public void delete(int podforumId) ;
	public void update(Podforum podforum) ;
	public int addNew(Podforum podforum);
}
