package rs.ftn.mr.webforum.entities;
import java.sql.Date;
public class Zalba {
    int id;
    int idUser;
    String tekst;
    Date datum;
    String tipEntiteta;
    int idPodforum;
    int idTema;
    int idKomentar;
    String preduzetaAkcija;
    String odgovorTekst;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getIdUser() {
		return idUser;
	}
	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}
	public String getTekst() {
		return tekst;
	}
	public void setTekst(String tekst) {
		this.tekst = tekst;
	}
	public Date getDatum() {
		return datum;
	}
	public void setDatum(Date datum) {
		this.datum = datum;
	}
	public String getTipEntiteta() {
		return tipEntiteta;
	}
	public void setTipEntiteta(String tipEntiteta) {
		this.tipEntiteta = tipEntiteta;
	}
	public int getIdPodforum() {
		return idPodforum;
	}
	public void setIdPodforum(int idPodforum) {
		this.idPodforum = idPodforum;
	}
	public int getIdTema() {
		return idTema;
	}
	public void setIdTema(int idTema) {
		this.idTema = idTema;
	}
	public int getIdKomentar() {
		return idKomentar;
	}
	public void setIdKomentar(int idKomentar) {
		this.idKomentar = idKomentar;
	}
	public String getPreduzetaAkcija() {
		return preduzetaAkcija;
	}
	public void setPreduzetaAkcija(String preduzetaAkcija) {
		this.preduzetaAkcija = preduzetaAkcija;
	}
	public String getOdgovorTekst() {
		return odgovorTekst;
	}
	public void setOdgovorTekst(String odgovorTekst) {
		this.odgovorTekst = odgovorTekst;
	}
    
}
