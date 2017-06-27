package rs.ftn.mr.webforum.services;

import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import rs.ftn.mr.webforum.dao.CookieDao;
import rs.ftn.mr.webforum.dao.PodforumDao;
import rs.ftn.mr.webforum.dao.UserDAO;
import rs.ftn.mr.webforum.daoimpl.CookieDaoImpl;
import rs.ftn.mr.webforum.daoimpl.PodforumDaoImpl;
import rs.ftn.mr.webforum.daoimpl.UserDAOImpl;
import rs.ftn.mr.webforum.entities.Podforum;
import rs.ftn.mr.webforum.entities.User;

@Path("/podforum")
public class PodforumService {
	
	@POST
	@Path("/add")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Produces({ MediaType.APPLICATION_JSON })
	public Response add(Podforum podforum,@CookieParam("web-forum") String value) {	
		
		Response response;
		
		PodforumDao podforumDao = new PodforumDaoImpl();
		CookieDao cookieDao = new CookieDaoImpl();
		int userId = cookieDao.getById(value);
		podforum.setOdgovorniModerator(userId);
		if(true){//dodati proveru sesije
				podforumDao.addNew(podforum);	
			   response = Response.status(200).build();
		}else{
			response = Response.status(405).build();
		}
		return response;
	}
	@POST
	@Path("/podforumi")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response findAll() {	
		
		Response response;
		
		PodforumDao podforumDao = new PodforumDaoImpl();
	
	    response = Response.
	    		status(200)
	    		.entity(podforumDao.selectAll())
	    		.build();
	
		return response;
	}
}
