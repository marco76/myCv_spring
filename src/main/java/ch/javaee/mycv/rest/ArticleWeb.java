package ch.javaee.mycv.rest;

import ch.javaee.mycv.model.Article;
import ch.javaee.mycv.service.CvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created by marco on 16/03/16.
 */
@RequestMapping("/article")
public class ArticleWeb {

    @Autowired
    private CvService cvService;

    @RequestMapping("latest")
    public List<Article> getLatestArticles(){

        //List<Article> text = cvService.getLatestArticles();

        //return text;
        return null;
    }
}
