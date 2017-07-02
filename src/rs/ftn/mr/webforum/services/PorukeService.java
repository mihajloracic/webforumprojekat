package rs.ftn.mr.webforum.services;

import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import rs.ftn.mr.webforum.dao.CookieDao;
import rs.ftn.mr.webforum.dao.PorukaDao;
import rs.ftn.mr.webforum.dao.UserDAO;
import rs.ftn.mr.webforum.daoimpl.CookieDaoImpl;
import rs.ftn.mr.webforum.daoimpl.PorukaDaoImpl;
import rs.ftn.mr.webforum.daoimpl.UserDAOImpl;
import rs.ftn.mr.webforum.entities.Poruka;
import rs.ftn.mr.webforum.entities.User;

@Path("/poruka")
public class PorukeService {
	@POST
	@Path("/add")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response add(Poruka poruka,@CookieParam("web-forum") String value) {	
		
		Response response = null;
		
		UserDAO userDAO = new UserDAOImpl();
		
		CookieDao cookieDao = new CookieDaoImpl();
		int idSender = cookieDao.getById(value);
		
		if(idSender == 0){
			response = Response.status(405).build();
			return response;
		}
		poruka.setSalje(idSender);
		PorukaDao porukaDao = new PorukaDaoImpl();
		
		porukaDao.add(poruka);
		
		return Response.status(200).build();
	}
}
