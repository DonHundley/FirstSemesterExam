package dal;

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

    public Movie addMovie(String name, float rating, String fileLink, Date lastView, float IMDBRating) throws SQLServerException {
        try(Connection connection = databaseConnector.getConnection()) {
            String insert = "'" + name + "'" + "," +  rating  + "," + "'" + fileLink + "'" + "," + "'" + lastView + "'" + ","  + IMDBRating;
            String sql = "INSERT INTO Movie (Name, Rating, FileLink, LastView, IMDBRating) VALUES (" + insert + ")";

            Statement statement = connection.createStatement();
            statement.execute(sql, Statement.RETURN_GENERATED_KEYS);

            ResultSet keys = statement.getGeneratedKeys();
            keys.next();
            int id = keys.getInt(1);

            return new Movie(id, name, rating, fileLink, lastView, IMDBRating);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Movie> getAllMovies() {
        ArrayList<Movie> allMovies = new ArrayList<>();
        try(Connection connection = databaseConnector.getConnection()){
            String sql = "SELECT * FROM Movie";

            Statement statement = connection.createStatement();

            if(statement.execute(sql)) {
                ResultSet resultSet = statement.getResultSet();
                while(resultSet.next()) {
                    int id = resultSet.getInt("MovieID");
                    String name = resultSet.getString("Name");
                    float rating = resultSet.getFloat("Rating");
                    String fileLink = resultSet.getString("FileLink");
                    Date lastView = resultSet.getDate("LastView");
                    float IMDBRating = resultSet.getFloat("IMDBRating");

                    Movie movie = new Movie(id, name, rating, fileLink, lastView, IMDBRating);
                    allMovies.add(movie);
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

    public void deleteMovie(Movie movie){

    }
}
