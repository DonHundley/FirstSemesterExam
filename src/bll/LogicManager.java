package bll;

import be.Movie;
import dal.MovieDAO;

import java.util.List;

public class LogicManager {
    private MovieDAO movieDAO = new MovieDAO();

    public Movie addMovie(String title, String category, int ratingIMDB) {
        return movieDAO.addMovie(title, category, ratingIMDB);
    }


}
