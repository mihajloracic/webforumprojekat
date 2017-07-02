package rs.ftn.mr.webforum.dao;

import rs.ftn.mr.webforum.entities.Poruka;

public interface PorukaDao {
	public int add(Poruka poruka);
	public Poruka getById(int idPoruke);
	public Poruka getByUserId(int idUser);
}
