package rs.ftn.mr.webforum.services;

import java.awt.PageAttributes.MediaType;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/post")
public class PostService {
	 	@POST
	    @Path("/upload")
	    @Consumes({MediaType.MULTIPART_FORM_DATA})
	    public Response uploadFileWithData(
	         @FormDataParam("file") InputStream fileInputStream,
	         @FormDataParam("file") FormDataContentDisposition cdh,
	         @FormDataParam("emp") Employee emp) throws Exception{

	        Image img = ImageIO.read(fileInputStream);
	        JOptionPane.showMessageDialog(null, new JLabel(new ImageIcon(img)));
	        System.out.println(cdh.getName());
	        System.out.println(emp);

	        return Response.ok("Cool Tools!").build();
	    } 
}
