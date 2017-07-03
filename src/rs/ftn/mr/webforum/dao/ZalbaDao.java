package rs.ftn.mr.webforum.dao;

import java.util.List;

import rs.ftn.mr.webforum.entities.Zalba;

public interface ZalbaDao {
	public Zalba selectById(int id);
	public List<Zalba> selectAll();

	public Zalba addNew(Zalba zalba);
	public void update(Zalba zalba) ;
	void delete(Zalba zalba);
    void delete(int id) ;

}
