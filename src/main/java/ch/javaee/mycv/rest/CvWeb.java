package ch.javaee.mycv.rest;

import ch.javaee.mycv.model.Visitor;
import ch.javaee.mycv.service.CvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;


/**
 * Created by marco on 14/03/16.
 */
@RestController
@RequestMapping("/cv")
public class CvWeb {

    @Autowired
    private CvService cvService;

    @RequestMapping("user/{name}")
    public String getCvByUser(@RequestParam("name") String name, HttpServletRequest request) {

        if (name == null) {
            name = "marco";
        }

        Visitor visitor = prepareVisitor(request, name);
        cvService.recordVisit(visitor);

        return cvService.getCvByUser(name);
    }

    private Visitor prepareVisitor(HttpServletRequest request, String name) {

        Visitor visitor = new Visitor();
        visitor.setIpAdress(getIpAddress(request));

        visitor.setUser(name);
        visitor.setDate(Calendar.getInstance().getTime());

        return visitor;
    }

    private String getIpAddress(HttpServletRequest request) {

        String ipAddress = request.getHeader("X-FORWARDED-FOR");
        if (ipAddress == null) {
            ipAddress = request.getRemoteAddr();
        }

        return ipAddress;
    }
}
