package be;

import javafx.beans.property.SimpleStringProperty;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Movie {

    private int id;
    private SimpleStringProperty name = new SimpleStringProperty();
    private float rating;
    private String fileLink;
    private Date lastView;
    private float IMDBRating;

    private List<Category> categories;

    public String getCategoriesAsString(){
        String ctg = new String();
        for (Category category: categories)
        {
            ctg = ctg + category.getName() +", ";
        }
        return ctg;
    }

    public List<Category> getCategories() {
        return categories;
    }


    public Movie(String name, float rating, String fileLink, Date lastView, float IMDBRating) {
        this.name.set(name);
        this.rating = rating;
        this.fileLink = fileLink;
        this.lastView = lastView;
        this.IMDBRating = IMDBRating;
        this.categories = new ArrayList<>();
    }

    public Movie(int id, String name, float rating, String fileLink, Date lastView, float IMDBRating) {
        this(name, rating, fileLink,lastView, IMDBRating);
        this.id = id;
        this.categories = new ArrayList<>();
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
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
