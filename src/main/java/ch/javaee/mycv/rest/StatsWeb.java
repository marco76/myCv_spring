package ch.javaee.mycv.rest;

import ch.javaee.mycv.service.StatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by marco on 9/5/16.
 */
@RestController
@RequestMapping("/stats")
public class StatsWeb {

    @Autowired
    StatsService statsService;

    @RequestMapping("weekly")
    public String weekly(){
        statsService.getLatestArticles();
    return null;
    }
}
