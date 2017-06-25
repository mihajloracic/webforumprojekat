package rs.ftn.mr.webforum.services;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import rs.ftn.mr.webforum.entities.User;
import rs.ftn.mr.webforum.entities.*;
import rs.ftn.mr.webforum.dao.*;
import rs.ftn.mr.webforum.daoimpl.*;


@Path("/user")
public class UserService {
	
	@GET
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public List<User> getUsers_JSON() {
		UserDAO userDAO = new UserDAOImpl();
        List<User> users = userDAO.selectAll();
        System.out.println("List of Users " + users.size());
    	User user;
    	int i=0; 
        while (i < users.size()){
        	  user= (User) users.get(i);
            System.out.println(user.getImePrezime());	
            i++;
          }
    	        
       return users;
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
	@Path("/add")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
//    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces(MediaType.TEXT_PLAIN)
	public String add(User user) {	
		System.out.println(user.getImePrezime());
		
		UserDAO userDAO = new UserDAOImpl();
		userDAO.addNew(user);		
		System.out.println(user.toString());
		return "OK";
	}
}

