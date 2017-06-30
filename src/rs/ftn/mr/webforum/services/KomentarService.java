package rs.ftn.mr.webforum.services;

import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import rs.ftn.mr.webforum.dao.CookieDao;
import rs.ftn.mr.webforum.dao.KomentarDao;
import rs.ftn.mr.webforum.dao.UserDAO;
import rs.ftn.mr.webforum.daoimpl.CookieDaoImpl;
import rs.ftn.mr.webforum.daoimpl.KomentarDaoImpl;
import rs.ftn.mr.webforum.daoimpl.PodforumDaoImpl;
import rs.ftn.mr.webforum.daoimpl.UserDAOImpl;
import rs.ftn.mr.webforum.entities.Komentar;
import rs.ftn.mr.webforum.entities.Podforum;

@Path("/komentar")
public class KomentarService {
	@POST
	@Path("/add")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response add(Komentar komentar,@CookieParam("web-forum") String value) {	
		Response response;
		KomentarDao komentarDao = new KomentarDaoImpl();
		CookieDao cookieDao = new CookieDaoImpl();
		int userId = cookieDao.getById(value);
		if(userId == -1){
			response = Response.status(405).build();
		}
		komentar.setAutor(userId);
		komentarDao.addNew(komentar);
	    response = Response.status(200).build();

		return response;
	}
}
