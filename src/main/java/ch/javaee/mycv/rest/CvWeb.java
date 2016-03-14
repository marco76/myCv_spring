package ch.javaee.mycv.rest;

import ch.javaee.mycv.model.Visitor;
import org.bson.Document;
import service.CvService;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.util.Calendar;

/**
 * Created by marco on 14/03/16.
 */
@Path("/cv")
public class CvWeb {

    @EJB
    CvService cvService;

    @GET
    @Path("user/{name}")
    @Produces("application/JSON")
    public Response getCvByUser(@PathParam("name") String name, @Context HttpServletRequest request){
        if (name == null){
            name = "marco";
        }
        Document visitor = mongoVisitAdapter(recordVisit(request, name));
        cvService.saveVisitor(visitor);
         return Response.status(200).entity(cvService.getCvByUser(name)).build();

}

    private Visitor recordVisit(HttpServletRequest request, String username){
        Visitor visitor = new Visitor();
        visitor.setIpAdress(getIpAddress(request));
        visitor.setUser(username);
        visitor.setDate(Calendar.getInstance().getTime());
        return visitor;

    }

    private Document mongoVisitAdapter(Visitor visitor){
        Document document = new Document();
        document.append("ipAddress", visitor.getIpAdress())
                .append("user", visitor.getUser())
                .append("date", visitor.getDate());
        return document;
     }

    private String getIpAddress(HttpServletRequest request){
        String ipAddress = request.getHeader("X-FORWARDED-FOR");
        if (ipAddress == null) {
            ipAddress = request.getRemoteAddr();
        }
        return ipAddress;
    }


}
