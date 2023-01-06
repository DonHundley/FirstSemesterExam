package gui.model;

import be.Movie;
import bll.LogicManager;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Date;

public class Model {



    private final ObservableList<Movie> movies = FXCollections.observableArrayList();;
    private LogicManager bll = new LogicManager();


    public ObservableList<Movie> getObsMovies() {
        return movies;
    }


    public void loadMovieList()
    {
        movies.clear();
        movies.addAll(bll.getAllMovies());
    }


    public Movie addMovie(String name, float rating, String fileLink, Date lastView, float IMDBRating) throws SQLServerException {
        Movie movie = bll.addMovie(name, rating, fileLink, lastView, IMDBRating);
        movies.add(movie);
        return movie;
    }

    public void deleteMovie(Movie movie) {
        bll.deleteMovie(movie);
        movies.remove(movie);
    }

}