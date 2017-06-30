package rs.ftn.mr.webforum.entities;

import java.sql.Date;

import rs.ftn.mr.webforum.dao.LikeTemaDao;

public class Tema {
	int id;
	int id_podforum;
	String naslov;
	String tip;
	int autor;
	String tekst;
	String link;
	Date datum_kreiranja;
	String slika;
	int brojLike;
	int brojDislike;
	
	public int getBrojLike() {
		return brojLike;
	}
	public void setBrojLike(int brojLike) {
		this.brojLike = brojLike;
	}
	public int getBrojDislike() {
		return brojDislike;
	}
	public void setBrojDislike(int brojDislike) {
		this.brojDislike = brojDislike;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getId_podforum() {
		return id_podforum;
	}
	public void setId_podforum(int id_podforum) {
		this.id_podforum = id_podforum;
	}
	public String getNaslov() {
		return naslov;
	}
	public void setNaslov(String naslov) {
		this.naslov = naslov;
	}
	public String getTip() {
		return tip;
	}
	public void setTip(String tip) {
		this.tip = tip;
	}
	public int getAutor() {
		return autor;
	}
	public void setAutor(int autor) {
		this.autor = autor;
	}
	public String getTekst() {
		return tekst;
	}
	String nazivPodforum;
	public void setTekst(String tekst) {
		this.tekst = tekst;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public Date getDatum_kreiranja() {
		return datum_kreiranja;
	}
	public void setDatum_kreiranja(Date datum_kreiranja) {
		this.datum_kreiranja = datum_kreiranja;
	}
	public String getSlika() {
		return slika;
	}
	public void setSlika(String slika) {
		this.slika = slika;
	}
	public String getNazivPodforum() {
		return nazivPodforum;
	}
	public void setNazivPodforum(String nazivPodforum) {
		this.nazivPodforum = nazivPodforum;
	}
	
}
