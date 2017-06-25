package rs.ftn.mr.webforum.model;

import java.io.Serializable;

public class User implements Serializable{
	private String id;
	private String name;
	/** Koristi se samo za AngularJS */
	private int count;
	public User() {

	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public User(String id, String name, int count) {
		super();
		this.id = id;
		this.name = name;
		this.count = count;
	}
	
	public User(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", count=" + count + "]";
	}
	
}
