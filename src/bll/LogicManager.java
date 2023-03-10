package bll;

import be.Category;
import be.Movie;
import be.MovieInfo;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import dal.CatMovieDAO;
import dal.CategoryDAO;
import dal.MovieDAO;
import dal.TMDBDAO;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class LogicManager {
    private MovieDAO movieDAO = new MovieDAO();

    private CategoryDAO categoryDAO = new CategoryDAO();

    private CatMovieDAO catMovieDAO = new CatMovieDAO();

    private TMDBDAO tmdb = new TMDBDAO();


    public Movie addMovie(Movie m) throws SQLServerException {
        return movieDAO.addMovie(m);}
    public List<Movie> getAllMovies() {return movieDAO.getAllMovies();}


    public void deleteMovie(int id){
        movieDAO.deleteMovie(id);
    }
    public void lastviewUpdate(int id){
        movieDAO.lastviewUpdate(id);
    }

    public void addUserReview(float rating, int id){movieDAO.addUserReview(rating,id);}

    public Category addCategory (String name) throws SQLServerException {
        return categoryDAO.addCategory(name);
    }

    public List<Category> getAllCategories() {
        return categoryDAO.getAllCategories();
    }

    public void deleteCategory(int id) {
        categoryDAO.deleteCategory(id);
    }

    public void addCatToMovie (Category category, Movie movie) throws SQLException {
        catMovieDAO.addCatToMovie(category.getId(), movie.getId());
    }

    public MovieInfo getTMDBResult(Movie selectedUser) {
        return tmdb.getResult(selectedUser);
    }
}

