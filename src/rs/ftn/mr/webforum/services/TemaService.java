package rs.ftn.mr.webforum.services;
 
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;
 
import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.POST;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import rs.ftn.mr.webforum.dao.CookieDao;
import rs.ftn.mr.webforum.dao.LikeTemaDao;
import rs.ftn.mr.webforum.dao.PodforumDao;
import rs.ftn.mr.webforum.dao.TemaDao;
import rs.ftn.mr.webforum.dao.UserDAO;
import rs.ftn.mr.webforum.daoimpl.CookieDaoImpl;
import rs.ftn.mr.webforum.daoimpl.LikeTemaDaoImpl;
import rs.ftn.mr.webforum.daoimpl.PodforumDaoImpl;
import rs.ftn.mr.webforum.daoimpl.TemaDaoImpl;
import rs.ftn.mr.webforum.daoimpl.UserDAOImpl;
import rs.ftn.mr.webforum.entities.LikeTema;
import rs.ftn.mr.webforum.entities.SearchJson;
import rs.ftn.mr.webforum.entities.Tema;
import rs.ftn.mr.webforum.entities.User;
 
 
@Path("/post")
public class TemaService {
	
	@Context
	ServletContext ctx;
	
    @POST
    @Path("/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadFile(InputStream uploadedInputStream) {
        String fileLocation = ctx.getRealPath("") + "\\images\\"; // fileDetail.getFileName();
        String imageId = UUID.randomUUID().toString()  + ".jpg";
        
        fileLocation += imageId;
        File directory = new File("~/");
        System.out.println(directory.getAbsolutePath());
        try {
            FileOutputStream out = new FileOutputStream(new File(fileLocation));
            int read = 0;
            byte[] bytes = new byte[1024];
            out = new FileOutputStream(new File(fileLocation));
            while ((read = uploadedInputStream.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Response.status(200).entity(imageId).build();
    }
    
    @POST
	@Path("/add")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response add(Tema tema,@CookieParam("web-forum") String value) {	
		
		Response response;
		
		TemaDao temaDao = new TemaDaoImpl();
		
		CookieDao cookieDao = new CookieDaoImpl();
		
		PodforumDao podforumDao = new PodforumDaoImpl();
		tema.setNazivPodforum(tema.getNazivPodforum().replaceAll("#", ""));
		int podforumId = Integer.parseInt(tema.getNazivPodforum());
		tema.setId_podforum(podforumId);
		int userId = cookieDao.getById(value);
		tema.setAutor(userId);
		if(userId != 0 && userId != -1){
			temaDao.addNew(tema);
			response = Response.status(200).build();
		}else{
			response = Response.status(401).build();
		}
		return response;
	}
	@POST
	@Path("/teme")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_XML})
	public Response findAll(String nazivPodforum) {	
		
		Response response;
		
		TemaDao temaDao = new TemaDaoImpl();
		
		PodforumDao podforumDao = new PodforumDaoImpl();
		nazivPodforum = nazivPodforum.replaceAll("#", "");
		int podforumId = Integer.parseInt(nazivPodforum);
		
	    response = Response.
	    		status(200)
	    		.entity(temaDao.selectAll(podforumId))
	    		.build();
	
		return response;
	}
	@POST
	@Path("/temaById")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_XML})
	public Response findById(String idTema) {	
		Response response;
		TemaDao temaDao = new TemaDaoImpl();
		idTema = idTema.replaceAll("#", "");
		int idTeme = Integer.parseInt(idTema);
		Tema tema = temaDao.selectById(idTeme);
		
	    response = Response.
	    		status(200)
	    		.entity(tema)
	    		.build();
	
		return response;
	}
	@POST
	@Path("/like")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Produces({ MediaType.APPLICATION_JSON })
	public Response addLikeTema(LikeTema likeTema,@CookieParam("web-forum") String value) {	
		Response response;
		CookieDao cookieDao = new CookieDaoImpl();
	    LikeTemaDao likeTemaDao = new LikeTemaDaoImpl();
	    TemaDao temaDao = new TemaDaoImpl();
		int userId = cookieDao.getById(value);
		likeTema.setIdUser(userId);
		likeTemaDao.addNew(likeTema);
		if(userId != 0){
			response = Response
					.status(200)
					.entity(temaDao.selectById(likeTema.getIdTema()))
					.build();
		}else{
			response = Response.status(401).build();
		}
		return response;
	
	}
	@POST
	@Path("/search")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response search(SearchJson json) {	
		Response response;		
		TemaDao temaDao = new TemaDaoImpl();
	    response = Response.
	    		status(200)
	    		.entity(temaDao.search(json.getNaslov(),json.getSadrzaj(),json.getAutor(),json.getPodforum()))
	    		.build();
	
		return response;
	}	
	@POST
	@Path("/getFollowed")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getFollowed(@CookieParam("web-forum") String value) {	
		Response response;		
		TemaDao temaDao = new TemaDaoImpl();
		CookieDao cookieDao = new CookieDaoImpl();
		int userId = cookieDao.getById(value);
		User user = new User();
		user.setId(userId);
	    response = Response.
	    		status(200)
	    		.entity(temaDao.getFromForumsFollowedByUser(user))
	    		.build();
	
		return response;
	}
	@POST
	@Path("/liked")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response searchTemaLiked(@CookieParam("web-forum") String value) {	
		Response response;		
	    TemaDao temaDao = new TemaDaoImpl();
		CookieDao cookieDao = new CookieDaoImpl();
		int userId = cookieDao.getById(value);
	    response = Response.
	    		status(200)
	    		.entity(temaDao.getLikedByUser(userId))
	    		.build();
	
		return response;
	}	
}