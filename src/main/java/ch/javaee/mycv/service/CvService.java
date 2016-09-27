package ch.javaee.mycv.service;

import ch.javaee.mycv.model.Cv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

/**
 * Created by marco on 14/03/16.
 */
@Service
public class CvService {

    private final static Logger LOGGER = Logger.getLogger(CvService.class.getName());

    @Autowired
    JdbcTemplate jdbcTemplate;

     public String getCvByUser(String username) {

        List<Cv> cvList = jdbcTemplate.query("select ID, USER, TEXT FROM CV where USER = ?", new Object[]{username},
                (rs,rowNum)->new Cv(rs.getLong("ID"), rs.getString("USER"), rs.getString("TEXT")));
        if (cvList.size() > 0){
            return cvList.get(0).getText();
        }

        return null;
     }

}
