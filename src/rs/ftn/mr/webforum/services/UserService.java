package rs.ftn.mr.webforum.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.POST;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import rs.ftn.mr.webforum.entities.*;
import rs.ftn.mr.webforum.dao.*;
import rs.ftn.mr.webforum.daoimpl.*;


@Path("/user")
public class UserService {
	
	@POST
	@Path("users")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response getUsers_JSON() {
		UserDAO userDAO = new UserDAOImpl();
        List<User> users = userDAO.selectAll();
        System.out.println("List of Users " + users.size());
    	User user;
    	int i=0; 
        while (i < users.size()){
        	  user= (User) users.get(i);
            user.setLozinka("ne dam ti sifru asistente");
            i++;
          }
    	        
       return Response.status(200).entity(users).build();
    }
 	
	@GET
	@Path("byId/{paramId}")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public User  getUserById(@PathParam("paramId") int id) {
		User user;
		UserDAO userDAO = new UserDAOImpl();
		user = userDAO.selectById(id);
		return user;
	}

	@GET
	@Path("byUserName/{paramName}")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public User getUserByName(@PathParam("paramName") String userName) {
		User user = null;
		UserDAO userDAO = new UserDAOImpl();
		try{
			user = userDAO.selectByName(userName);
			return user;
			
		}catch (Exception e) {
			// TODO: handle exception
		}
		return user;


	}
			
	@POST
	@Path("/login")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
//    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response login(User user) {	
		System.out.println(user.getImePrezime());
		
		Response response;
		
		UserDAO userDAO = new UserDAOImpl();
		String lozinka = userDAO.selectByName(user.getUser()).getLozinka();
		int id =  userDAO.selectByName(user.getUser()).getId();
		String cookieId = UUID.randomUUID().toString();
		if(lozinka.equals(user.getLozinka())){
			CookieDao dao = new CookieDaoImpl();
			dao.add(new Cookie("web-forum",cookieId), id);
			   response = Response.
					   status(200).entity(cookieId).
					   build();
			   
		}else{
			response = Response.status(405).build();
		}
		return response;
	}
	@POST
	@Path("/add")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
//    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response add(User user) {	
		System.out.println(user.getImePrezime());
		
		Response response;
		
		UserDAO userDAO = new UserDAOImpl();
		
		if(userDAO.selectByName(user.getUser()) == null){
			userDAO.addNew(user);		
			   response = Response.status(200).build();
		}else{
			response = Response.status(405).build();
		}
		return response;
	}
	@POST
	@Path("/update")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response update(User user) {	
		System.out.println(user.getImePrezime());
		
		Response response;
		
		UserDAO userDAO = new UserDAOImpl();
		user.setUloga("moderator");
		userDAO.update(user);
		return Response.status(200)
	    		.build();
	}
	@POST
	@Path("/cookie")
	@Consumes({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_XML})
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getUserByCookie(String cookie) {	
		User u;
		
		Response response = null;
		
		UserDAO userDAO = new UserDAOImpl();
		CookieDao cookieDao = new CookieDaoImpl();
		u = userDAO.selectById(cookieDao.getById(cookie));
		if(u != null){
			response = Response.status(200)
		    		.entity(u)
		    		.build();
			
		}else{
			response = Response.status(404)
		    		.build();
		}
	    
		return response;
	}
	@POST
	@Path("/getUserById")
	@Consumes({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_XML})
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getUserById(String id) {	
		User u;
		
		Response response = null;
		
		UserDAO userDAO = new UserDAOImpl();
		u = userDAO.selectById(Integer.parseInt(id));
		if(u != null){
			response = Response.status(200)
		    		.entity(u)
		    		.build();
			
		}else{
			response = Response.status(404)
		    		.build();
		}
	    
		return response;
	}
	
	@POST
	@Consumes({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_XML})
	@Path("/deleteCookie")
	public void deleteCookie(String value) {	
		//TODO
		
		CookieDao cookieDao = new CookieDaoImpl();
		
		cookieDao.delete(value);

	}
	
	@POST
	@Path("/search")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response searchAll(SearchJson json) {	
		
		Response response;
		
		UserDAO userDao = new UserDAOImpl();
	    response = Response.status(200)
	    		.entity(userDao.searchByNicName(json.getKorisnik())).build();
	
		return response;
	}
   
	
}

