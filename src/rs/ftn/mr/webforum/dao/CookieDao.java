package rs.ftn.mr.webforum.dao;

import javax.servlet.http.Cookie;

public interface CookieDao {
	public void add(Cookie cookie,int userId);
	public int getById(String cookie);
	public int delete(String id);
}
