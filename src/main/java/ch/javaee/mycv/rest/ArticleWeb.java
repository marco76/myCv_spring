package ch.javaee.mycv.rest;

import ch.javaee.mycv.model.Article;
import ch.javaee.mycv.service.CvService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by marco on 16/03/16.
 */
@Path("/article")
public class ArticleWeb {

    @Inject
    CvService cvService;

    @GET
    @Path("latest")
    @Produces("application/JSON")
    public Response getLatestArticles(){
        List<Article> text = cvService.getLatestArticles();

        return Response.status(200).entity(text).build();

    }
}
