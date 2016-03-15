package ch.javaee.mycv.rest;

import ch.javaee.mycv.model.Visitor;
import ch.javaee.mycv.service.CvService;

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
          Visitor visitor = prepareVisitor(request, name);
          cvService.recordVisit(visitor);
          return Response.status(200).entity(cvService.getCvByUser(name)).build();

}
    private Visitor prepareVisitor(HttpServletRequest request, String name){
        Visitor visitor = new Visitor();
        visitor.setIpAdress(getIpAddress(request));
        visitor.setUser(name);
        visitor.setDate(Calendar.getInstance().getTime());
        return visitor;
    }

    private String getIpAddress(HttpServletRequest request){
        String ipAddress = request.getHeader("X-FORWARDED-FOR");
        if (ipAddress == null) {
            ipAddress = request.getRemoteAddr();
        }
        return ipAddress;
    }







}
