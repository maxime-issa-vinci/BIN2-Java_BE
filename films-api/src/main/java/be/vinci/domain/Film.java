package be.vinci.domain;

public class Film {

    private int id;
    private String title;
    private int duration;
    private long budget;
    private String link;

    public Film() {
    }

    public Film(int id, String title, int duration, long budget, String link) {
        this.id = id;
        this.title = title;
        this.duration = duration;
        this.budget = budget;
        this.link = link;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public long getBudget() {
        return budget;
    }

    public void setBudget(long budget) {
        this.budget = budget;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public String toString() {
        return "Film [id=" + id + ", title=" + title + ", duration=" + duration + ", budget=" + budget + ", link="
                + link + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Film film = (Film) o;

        return id == film.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
