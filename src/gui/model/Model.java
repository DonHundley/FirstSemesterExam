package gui.model;

import be.Movie;
import bll.LogicManager;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Date;

public class Model {
    private ObservableList<Movie> movies;
    private LogicManager bll = new LogicManager();



    public Model() {
        movies = FXCollections.observableArrayList();
    }
    public ObservableList<Movie> getMovies() {
        return movies;
    }





    public Movie addMovie(String name, float rating, String fileLink, Date lastView, float IMDBRating) throws SQLServerException {
        Movie movie = bll.addMovie(name, rating, fileLink, lastView, IMDBRating);
        movies.add(movie);
        return movie;
    }

}