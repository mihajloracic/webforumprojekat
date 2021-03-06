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
import rs.ftn.mr.webforum.dao.LikeKomentarDao;
import rs.ftn.mr.webforum.dao.LikeTemaDao;
import rs.ftn.mr.webforum.dao.TemaDao;
import rs.ftn.mr.webforum.dao.UserDAO;
import rs.ftn.mr.webforum.daoimpl.CookieDaoImpl;
import rs.ftn.mr.webforum.daoimpl.KomentarDaoImpl;
import rs.ftn.mr.webforum.daoimpl.LikeKomentarDaoImpl;
import rs.ftn.mr.webforum.daoimpl.LikeTemaDaoImpl;
import rs.ftn.mr.webforum.daoimpl.PodforumDaoImpl;
import rs.ftn.mr.webforum.daoimpl.TemaDaoImpl;
import rs.ftn.mr.webforum.daoimpl.UserDAOImpl;
import rs.ftn.mr.webforum.entities.Komentar;
import rs.ftn.mr.webforum.entities.LikeKomentar;
import rs.ftn.mr.webforum.entities.LikeTema;
import rs.ftn.mr.webforum.entities.Podforum;
import rs.ftn.mr.webforum.entities.User;

@Path("/komentar")
public class KomentarService {
	@POST
	@Path("/add")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Produces({ MediaType.APPLICATION_JSON})
	public Response add(Komentar komentar,@CookieParam("web-forum") String value) {	
		Response response;
		KomentarDao komentarDao = new KomentarDaoImpl();
		CookieDao cookieDao = new CookieDaoImpl();
		int userId = cookieDao.getById(value);
		if(userId == -1){
			response = Response.status(405).build();
		}
		komentar.setAutor(userId);
	    response = Response.status(200)
	    		.entity(komentarDao.addNew(komentar))
	    		.build();

		return response;
	}
	@POST
	@Path("/update")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response update(Komentar komentar,@CookieParam("web-forum") String value) {	
		Response response;
		KomentarDao komentarDao = new KomentarDaoImpl();
		CookieDao cookieDao = new CookieDaoImpl();
		UserDAO userDao = new UserDAOImpl();
		int userId = cookieDao.getById(value);
		User u = userDao.selectById(userId);
		String saveNewText = komentar.getTekst_komentar();
		komentar = komentarDao.selectById(komentar.getId());
		komentar.setTekst_komentar(saveNewText);
		if(userId != komentar.getAutor() && u.getUloga().equals("admin") && u.getUloga().equals("moderator")){
			response = Response.status(401).build();
			return response;
		}
		if(userId == komentar.getAutor()){
			komentar.setIzmenjen(true);
		}
		komentarDao.update(komentar);
	    response = Response.status(200)
	    		.build();

		return response;
	}
	@POST
	@Path("/delete")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response delete(Komentar komentar,@CookieParam("web-forum") String value) {	
		Response response;
		KomentarDao komentarDao = new KomentarDaoImpl();
		CookieDao cookieDao = new CookieDaoImpl();
		UserDAO userDao = new UserDAOImpl();
		int userId = cookieDao.getById(value);
		User u = userDao.selectById(userId);
		komentar = komentarDao.selectById(komentar.getId());
		if(userId != komentar.getAutor() && !u.getUloga().equals("admin") && !u.getUloga().equals("moderator")){
			response = Response.status(401).build();
			return response;
		}
		komentarDao.delete(komentar.getId());
	    response = Response.status(200)
	    		.build();

		return response;
	}
	@POST
	@Path("/getComments")
	@Consumes({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_XML})
	@Produces({ MediaType.APPLICATION_JSON})
	public Response getAll(String temaId) {	
		Response response;
		KomentarDao komentarDao = new KomentarDaoImpl();
		temaId = temaId.replaceAll("#", "");
		int podforumId = Integer.parseInt(temaId);
		komentarDao.selectByParentIdPost(-1, podforumId);
		
	    response = Response.status(200)
	    		.entity(komentarDao.selectByParentIdPost(-1, podforumId))
	    		.build();

		return response;
	}
	@POST
	@Path("/comments")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Produces({ MediaType.APPLICATION_JSON})
	public Response getAll(Komentar k) {	
		Response response;
		KomentarDao komentarDao = new KomentarDaoImpl();
		
		k = komentarDao.selectById(k.getId());
	    response = Response.status(200)
	    		.entity(komentarDao.selectByParentIdPost(k.getId(),k.getId_tema()))
	    		.build();

		return response;
	}
	@POST
	@Path("/getComment")
	@Consumes({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_XML})
	@Produces({ MediaType.APPLICATION_JSON})
	public Response getComment(String commentId) {	
		Response response;
		KomentarDao komentarDao = new KomentarDaoImpl();
		commentId = commentId.replaceAll("#", "");
		int id = Integer.parseInt(commentId);
		
		Komentar k = komentarDao.selectById(id);
		
	    response = Response.status(200)
	    		.entity(k)
	    		.build();

		return response;
	}
	@POST
	@Path("/liked")
	@Produces({ MediaType.APPLICATION_JSON})
	public Response getLikdeComments(@CookieParam("web-forum") String value) {	
		Response response;
		KomentarDao komentarDao = new KomentarDaoImpl();
		CookieDao cookieDao = new CookieDaoImpl();
		int userId = cookieDao.getById(value);
	    response = Response.status(200)
	    		.entity(komentarDao.getLikedByUser(userId))
	    		.build();

		return response;
	}
	
	@POST
	@Path("/like")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Produces({ MediaType.APPLICATION_JSON })
	public Response likeKomentar(LikeKomentar likeKomentar,@CookieParam("web-forum") String value) {	
		Response response;
		KomentarDao komentarDao = new KomentarDaoImpl();
		LikeKomentarDao likeKomentarDao = new LikeKomentarDaoImpl();
		CookieDao cookieDao = new CookieDaoImpl();
		int userId = cookieDao.getById(value);
		if(userId == 0){
			response = Response.status(405).build();
			return response;
		}
		likeKomentar.setIdUser(userId);
		likeKomentarDao.addNew(likeKomentar);
	    response = Response
	    		.status(200)
	    		.entity(komentarDao.selectById(likeKomentar.getIdKomentar()))
	    		.build();

		return response;
	}
	
}
