package dal;

import be.Category;
import be.Movie;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MovieDAO {

    private DatabaseConnector databaseConnector;
    static long millis = System.currentTimeMillis();
    static java.sql.Date date = new java.sql.Date(millis);


    public MovieDAO() {
        databaseConnector = new DatabaseConnector();
    }

    public static void main(String[] args) throws SQLServerException {
        MovieDAO movieDAO = new MovieDAO();
        //List<Movie> allMovies = movieDAO.getAllMovies();
        //System.out.println(allMovies);
        //movieDAO.addMovie("Titanic", 8, "ForTestingPurposes", date, 8);
    }

    public Movie addMovie(Movie movie) throws SQLServerException {
        try(Connection connection = databaseConnector.getConnection()) {
            String sql = "INSERT INTO Movie (Name, Rating, FileLink, LastView, IMDBRating) VALUES (?,?,?,?,?)";

            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, movie.getName());
            statement.setFloat(2, movie.getRating());
            statement.setString(3, movie.getFileLink());
            statement.setDate(4, new java.sql.Date(movie.getLastView().getTime()));
            statement.setFloat(5, movie.getIMDBRating());
            statement.execute();

            ResultSet keys = statement.getGeneratedKeys();
            keys.next();
            int id = keys.getInt(1);

            String sql2 = "INSERT INTO CatMovie (CategoryID, MovieID) VALUES (?, ?)";

            PreparedStatement statement2 = connection.prepareStatement(sql2);
            statement2.setInt(1, 1008);
            statement2.setInt(2, id);
            statement2.execute();

            return new Movie(id, movie.getName(), movie.getRating(),  movie.getFileLink(), movie.getLastView(), movie.getIMDBRating());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Movie> getAllMovies() {
        ArrayList<Movie> allMovies = new ArrayList<>();
        try(Connection connection = databaseConnector.getConnection()){
            String sql = "SELECT * FROM CatMovie JOIN Movie ON CatMovie.MovieID = Movie.MovieID JOIN Category ON CatMovie.CategoryID = Category.CategoryID ORDER BY Movie.MovieID";

            /*
            1, spiderman, horror
            1, spiderman, crime
            2, shawshank, comedy
             */

            Statement statement = connection.createStatement();

            if(statement.execute(sql)) {
                ResultSet resultSet = statement.getResultSet();
                Movie lastMovie = new Movie(-1,"DUMMY",0,"", null, 0);
                while(resultSet.next()) {
                    int id = resultSet.getInt("MovieID");
                    String name = resultSet.getString("Name");
                    float rating = resultSet.getFloat("Rating");
                    String fileLink = resultSet.getString("FileLink");
                    Date lastView = resultSet.getDate("LastView");
                    float IMDBRating = resultSet.getFloat("IMDBRating");
                    int categoryID = resultSet.getInt("CategoryID");
                    String catName = resultSet.getString("CategoryName");

                    Category categoriesForMovie = new Category(categoryID, catName);


                    Movie movie = new Movie(id, name, rating, fileLink, lastView, IMDBRating);

                   if (lastMovie.getId() == id){ // SAME MOVIE AS LAST
                        movie = lastMovie;
                    }
                    else{ // NEW MOVIE NEEDS TO BE CREATED
                        allMovies.add(movie);
                    }
                    movie.getCategories().add(categoriesForMovie);

                    lastMovie = movie;

                }
            }
        } catch (SQLServerException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return allMovies;
    }

    public void deleteMovie(int id) {
        String sql = "DELETE FROM Movie WHERE MovieID= ?";

        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void lastviewUpdate(int id){

        String sql = "UPDATE Movie set lastview=? WHERE MovieID= ?";

        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setDate(1, date);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public void addUserReview(float rating, int id){

        String sql = "UPDATE Movie set rating=? WHERE MovieID= ?";

        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {


            pstmt.setFloat(1, rating);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
}
