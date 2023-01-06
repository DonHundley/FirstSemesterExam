package bll;

import be.Movie;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import dal.MovieDAO;

import java.util.Date;
import java.util.List;

public class LogicManager {
    private MovieDAO movieDAO = new MovieDAO();


    public Movie addMovie(String name, float rating, String fileLink, Date lastView, float IMDBRating) throws SQLServerException {
        return movieDAO.addMovie(name, rating, fileLink, lastView, IMDBRating);}
    public List<Movie> getAllMovies() {return movieDAO.getAllMovies();}

    public void deleteMovie(int id){
        movieDAO.deleteMovie(id);
    }

}

