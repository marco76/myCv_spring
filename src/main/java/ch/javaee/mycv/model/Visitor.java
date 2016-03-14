package ch.javaee.mycv.model;

import java.util.Date;

/**
 * Created by marco on 14/03/16.
 */
public class Visitor {

    private String ipAdress;
    private Date date;
    private String user;

    public String getIpAdress() {
        return ipAdress;
    }

    public void setIpAdress(String ipAdress) {
        this.ipAdress = ipAdress;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
