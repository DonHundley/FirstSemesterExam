package be;

import java.time.LocalDate;
import java.util.Date;

public class Movie {

    private int id;
    private String name;
    private float rating;
    private String fileLink;
    private Date lastView;
    private float IMDBRating;

    public Movie(String name, float rating, String fileLink, Date lastView, float IMDBRating) {
        this.name = name;
        this.rating = rating;
        this.fileLink = fileLink;
        this.lastView = lastView;
        this.IMDBRating = IMDBRating;
    }

    public Movie(int id, String name, float rating, String fileLink, Date lastView, float IMDBRating) {
        this(name, rating, fileLink,lastView, IMDBRating);
        this.id = id;
    }
    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }


    public String getFileLink() {
        return fileLink;
    }

    public void setFileLink(String fileLink) {
        this.fileLink = fileLink;
    }

    public Date getLastView() {
        return lastView;
    }
    public int getId() {return id;}
    public void setLastView(Date lastView) {
        this.lastView = lastView;
    }

    public float getIMDBRating() {
        return IMDBRating;
    }

    public void setIMDBRating(float IMDBRating) {
        this.IMDBRating = IMDBRating;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", rating='" + rating + '\'' +
                ", fileLink='" + fileLink + '\'' +
                ", lastView='" + lastView + '\'' +
                ", IMDBRating='" + IMDBRating + '\'' +
                '}';

    }

    }
