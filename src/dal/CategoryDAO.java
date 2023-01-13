package dal;

import be.Category;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO {
    private DatabaseConnector databaseConnector;

    public CategoryDAO() {
        databaseConnector = new DatabaseConnector();
    }

    public static void main(String[] args) throws SQLServerException {
        CategoryDAO categoryDAO = new CategoryDAO();
        List<Category> allCategories = categoryDAO.getAllCategories();
        System.out.println(allCategories);
        //categoryDAO.addCategory("Action");
    }

    public Category addCategory(String name) throws SQLServerException {
        try(Connection connection = databaseConnector.getConnection()) {
            String insert = "'" + name + "'";
            String sql = "INSERT INTO Category (CategoryName) VALUES (" + insert + ")";

            Statement statement = connection.createStatement();
            statement.execute(sql, Statement.RETURN_GENERATED_KEYS);

            ResultSet keys = statement.getGeneratedKeys();
            keys.next();
            int id = keys.getInt(1);

            return new Category(id, name);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Category> getAllCategories() {
        ArrayList<Category> allCategories = new ArrayList<>();
        try(Connection connection = databaseConnector.getConnection()){
            String sql = "SELECT * FROM Category";

            Statement statement = connection.createStatement();

            if(statement.execute(sql)) {
                ResultSet resultSet = statement.getResultSet();
                while(resultSet.next()) {
                    int id = resultSet.getInt("CategoryID");
                    String name = resultSet.getString("CategoryName");

                    Category category = new Category(id, name);
                    allCategories.add(category);
                }
            }
        } catch (SQLServerException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return allCategories;
    }

    public void deleteCategory(int id) {
        String sql = "DELETE FROM Category WHERE CategoryID= ?";

        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}