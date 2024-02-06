package be.vinci.domain;

public class Page {

    private int id;
    private String title;
    private String uri;
    private String content;
    private String author;
    private String status;

    public Page() {
    }

    public Page(int id, String title, String uri, String content, String author, String status) {
        this.id = id;
        this.title = title;
        this.uri = uri;
        this.content = content;
        this.author = author;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getUri() {
        return uri;
    }

    public String getContent() {
        return content;
    }

    public String getAuthor() {
        return author;
    }

    public String getStatus() {
        return status;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
