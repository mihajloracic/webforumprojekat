package rs.ftn.mr.webforum.entities;

public class Podforum {
	int id;
	String naziv;
	String opis;
	String spisakPravila;
	int odgovorniModerator;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNaziv() {
		return naziv;
	}
	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}
	public String getOpis() {
		return opis;
	}
	public void setOpis(String opis) {
		this.opis = opis;
	}
	public String getSpisakPravila() {
		return spisakPravila;
	}
	public void setSpisakPravila(String spisakPravila) {
		this.spisakPravila = spisakPravila;
	}
	public int getOdgovorniModerator() {
		return odgovorniModerator;
	}
	public void setOdgovorniModerator(int odgovorniModerator) {
		this.odgovorniModerator = odgovorniModerator;
	}
	
	
}
