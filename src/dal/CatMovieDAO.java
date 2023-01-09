package dal;

import be.CatMovie;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CatMovieDAO {
    private DatabaseConnector databaseConnector;

    public CatMovieDAO() {
        databaseConnector = new DatabaseConnector();
    }

    public static void main(String[] args) throws SQLServerException {
        CatMovieDAO catMovieDAO = new CatMovieDAO();
        //catMovieDAO.addCatMovie(1, 19);
        //List<Category> allCategories = categoryDAO.getAllCategories();
        //System.out.println(allCategories);
        //categoryDAO.addCategory("Action");
    }

    public void addCatMovie(int categoryID, int movieID) {
        String sql = "INSERT INTO CatMovie (CategoryID, MovieID) VALUES (?, ?)";

        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, categoryID);
            pstmt.setInt(2, movieID);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<CatMovie> getAllCatForMovie(int idOfMovie) {
        ArrayList<CatMovie> allCatForMovie = new ArrayList<>();
        try(Connection connection = databaseConnector.getConnection()){
            String sql = "SELECT * FROM CatMovie WHERE MovieID = " + idOfMovie + "";

            Statement statement = connection.createStatement();

            if(statement.execute(sql)) {
                ResultSet resultSet = statement.getResultSet();
                while(resultSet.next()) {
                    int id = resultSet.getInt("CatMovieID");
                    int categoryID = resultSet.getInt("CategoryID");
                    int movieID = resultSet.getInt("MovieID");

                    CatMovie catmovie = new CatMovie(id, categoryID, movieID);
                    allCatForMovie.add(catmovie);
                }
            }
        } catch (SQLServerException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return allCatForMovie;
    }
}
