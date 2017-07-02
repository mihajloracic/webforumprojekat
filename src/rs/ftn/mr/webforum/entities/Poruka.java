package rs.ftn.mr.webforum.entities;

public class Poruka {
	int id;
	int prima;
	int salje;
	String subject;
	String tekst;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPrima() {
		return prima;
	}
	public void setPrima(int prima) {
		this.prima = prima;
	}
	public int getSalje() {
		return salje;
	}
	public void setSalje(int salje) {
		this.salje = salje;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getTekst() {
		return tekst;
	}
	public void setTekst(String tekst) {
		this.tekst = tekst;
	}
	
}
