package bll;

import be.Category;
import be.Movie;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import dal.CategoryDAO;
import dal.MovieDAO;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class LogicManager {
    private MovieDAO movieDAO = new MovieDAO();

    private CategoryDAO categoryDAO = new CategoryDAO();


    public Movie addMovie(String name, float rating, String fileLink, Date lastView, float IMDBRating) throws SQLServerException {
        return movieDAO.addMovie(name, rating, fileLink, lastView, IMDBRating);}
    public List<Movie> getAllMovies() {return movieDAO.getAllMovies();}

    public void deleteMovie(int id){
        movieDAO.deleteMovie(id);
    }
    public void lastviewUpdate(int id){
        movieDAO.lastviewUpdate(id);
    }

    public Category addCategory (String name) throws SQLServerException {
        return categoryDAO.addCategory(name);
    }

    public List<Category> getAllCategories() {
        return categoryDAO.getAllCategories();
    }
}

