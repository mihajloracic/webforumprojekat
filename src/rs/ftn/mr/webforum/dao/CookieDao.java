package rs.ftn.mr.webforum.dao;

import javax.servlet.http.Cookie;

public interface CookieDao {
	public void add(Cookie cookie,int userId);
	public int getById(Cookie cookie);
}
