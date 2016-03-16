package ch.javaee.mycv.model;

/**
 * Created by marco on 16/03/16.
 */
public class Article {

    private String title;
    private String lead;
    private String content;
    private String author;


    public Article(String title, String lead, String content, String author) {
        this.title = title;
        this.lead = lead;
        this.content = content;
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLead() {
        return lead;
    }

    public void setLead(String lead) {
        this.lead = lead;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}