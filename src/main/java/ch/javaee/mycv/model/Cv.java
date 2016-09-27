package ch.javaee.mycv.model;

/**
 * Created by molte on 9/26/2016.
 */

public class Cv {

    public Cv(Long id, String user, String text) {
        this.id = id;
        this.user = user;
        this.text = text;
    }

    private Long id;
    private String user;
    private String text;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
