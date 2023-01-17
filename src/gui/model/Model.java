package gui.model;

import be.CatMovie;
import be.Category;
import be.Movie;
import be.MovieInfo;
import bll.LogicManager;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.Date;

public class Model {


    private final ObservableList<Movie> movies = FXCollections.observableArrayList();

    private final ObservableList<Category> categories = FXCollections.observableArrayList();



    private LogicManager bll = new LogicManager();


    public ObservableList<Movie> getObsMovies() {
        return movies;
    }


    public void loadMovieList() {
        movies.clear();
        movies.addAll(bll.getAllMovies());
    }


    public Movie addMovie(Movie movie) throws SQLServerException {
        Movie m = bll.addMovie(movie);
        movies.add(m);
        return movie;
    }

    public void deleteMovie(Movie movie) {
        bll.deleteMovie(movie.getId());
        movies.remove(movie);
    }

    public void lastviewUpdate(int id) {
        bll.lastviewUpdate(id);
    }

    public void addUserReview(float rating, int id) {
        bll.addUserReview(rating, id);
    }

    public Category addCategory(String name) throws SQLServerException {
        Category category = bll.addCategory(name);
        categories.add(category);
        return category;
    }

    public void fetchCategoriesFromStorage() {
        categories.clear();
        categories.addAll(bll.getAllCategories());
    }

    public ObservableList<Category> getCategories() {
        return categories;
    }

    public void deleteCategory(Category category) {
        bll.deleteCategory(category.getId());
        categories.remove(category);
    }

    public void addCatToMovie(Category category, Movie movie) throws SQLException {
        bll.addCatToMovie(category, movie);
    }

    public MovieInfo getTMDBResult(Movie selectedUser) {
        return bll.getTMDBResult(selectedUser);
    }
}