package gui.model;

import be.Movie;
import bll.LogicManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Model {
    private ObservableList<Movie> movies;
    private LogicManager bll = new LogicManager();



    public Model() {
        movies = FXCollections.observableArrayList();
    }
    public ObservableList<Movie> getMovies() {
        return movies;
    }





    public Movie addMovie(String title, String category, double ratingIMDB, double myRating) {

        Movie movie = bll.addMovie(title, category, ratingIMDB,myRating);
        movies.add(movie);
        return movie;
    }

}