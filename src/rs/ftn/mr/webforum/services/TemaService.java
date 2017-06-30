package rs.ftn.mr.webforum.services;
 
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;
 
import javax.imageio.ImageIO;
import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import rs.ftn.mr.webforum.dao.CookieDao;
import rs.ftn.mr.webforum.dao.PodforumDao;
import rs.ftn.mr.webforum.dao.TemaDao;
import rs.ftn.mr.webforum.dao.UserDAO;
import rs.ftn.mr.webforum.daoimpl.CookieDaoImpl;
import rs.ftn.mr.webforum.daoimpl.PodforumDaoImpl;
import rs.ftn.mr.webforum.daoimpl.TemaDaoImpl;
import rs.ftn.mr.webforum.daoimpl.UserDAOImpl;
import rs.ftn.mr.webforum.entities.Tema;
import rs.ftn.mr.webforum.entities.User;
 
 
@Path("/post")
public class TemaService {
 
    @POST
    @Path("/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadFile(InputStream uploadedInputStream) {
        String fileLocation = "C:\\Users\\mihajlo\\JavaWebProjekti\\rs.ftn.mr.webforum\\WebContent\\images\\"; // fileDetail.getFileName();
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
		if(userId != 0){
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
   
}