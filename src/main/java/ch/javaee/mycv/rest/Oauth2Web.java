package ch.javaee.mycv.rest;

import ch.javaee.mycv.service.Oauth2Service;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

/**
 * Created by marco on 15/03/16.
 */
@Path("/oauth2")
public class Oauth2Web {

    @EJB
    Oauth2Service oauth2Service;


    @GET
    @Path("login")
    @Produces("text/html")
    public Response getLogin(@Context HttpServletRequest request, @Context HttpServletResponse response) {
        try {
            String url = oauth2Service.requestLogin();
            response.sendRedirect(url);
            return Response.status(Response.Status.ACCEPTED).build();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @GET
    @Path("callback")
    @Produces("text/html")
    public String getCallback(@Context HttpServletRequest request, @Context HttpServletResponse response,
                                @QueryParam("code") String code) {
        try {
            String email = oauth2Service.authorize(code);

           return email;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
