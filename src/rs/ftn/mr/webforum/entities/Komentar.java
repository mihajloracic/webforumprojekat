package rs.ftn.mr.webforum.entities;

import java.sql.Date;

public class Komentar {
	int id;
	int id_tema;
	int autor;
	Date datum_kreiranja;
	int id_parent_komentar;
	String tekst_komentar;
	boolean izmenjen;
	boolean obrisan;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getId_tema() {
		return id_tema;
	}
	public void setId_tema(int id_tema) {
		this.id_tema = id_tema;
	}
	public int getAutor() {
		return autor;
	}
	public void setAutor(int autor) {
		this.autor = autor;
	}
	public Date getDatum_kreiranja() {
		return datum_kreiranja;
	}
	public void setDatum_kreiranja(Date datum_kreiranja) {
		this.datum_kreiranja = datum_kreiranja;
	}
	public int getId_parent_komentar() {
		return id_parent_komentar;
	}
	public void setId_parent_komentar(int id_parent_komentar) {
		this.id_parent_komentar = id_parent_komentar;
	}
	public String getTekst_komentar() {
		return tekst_komentar;
	}
	public void setTekst_komentar(String tekst_komentar) {
		this.tekst_komentar = tekst_komentar;
	}
	public boolean isIzmenjen() {
		return izmenjen;
	}
	public void setIzmenjen(boolean izmenjen) {
		this.izmenjen = izmenjen;
	}
	public boolean isObrisan() {
		return obrisan;
	}
	public void setObrisan(boolean obirsan) {
		this.obrisan = obirsan;
	}
	
}
