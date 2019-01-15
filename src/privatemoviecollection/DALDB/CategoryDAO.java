/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package privatemoviecollection.DALDB;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import privatemoviecollection.BE.Movie;
import privatemoviecollection.DAL.ServerConnect;

/**
 *
 * @author Nijas Hansen
 */
public class CategoryDAO {

    //establishes a server connect witch can be used in the inter class
    private static ServerConnect server;

    public CategoryDAO() throws IOException {

        this.server = new ServerConnect();
    }
    /*
     * creats a category on the sever, using sql
     */

    public ArrayList getAllCategory() throws SQLServerException, SQLException {
        Connection con = server.getConnection();
        ArrayList list = new ArrayList<>();
        String sql = "SELECT * FROM [PrivateMovieCollectionName].[dbo].[Category]";
        PreparedStatement st = con.prepareStatement(sql);

        ResultSet rs = st.executeQuery();

        while (rs.next()) {
            String genre = rs.getNString("Genre");
            list.add(genre);
        }

        return list;


    }

    public void createCategory (String name) throws SQLServerException, SQLException {
        Connection con = server.getConnection();

        String sql = "INSERT INTO [PrivateMovieCollectionName].[dbo].[Category] (Genre) VALUES (?)";

        PreparedStatement st = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        st.setString(1, name);

        st.executeUpdate();

    }

    /*
    *adds a movie to a category by inserting a movie object and a category into a joined table
    */
    public void addMovieToCategory(Movie movie, String category) throws SQLServerException, SQLException {
        Connection con = server.getConnection();
        String sql = "INSERT INTO [PrivateMovieCollectionName].[dbo].[CatMovie] (MovieId, CategoryId) VALUES (?,?)";
        PreparedStatement st = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        st.setInt(1, movie.getId());
        st.setInt(2, getCategoryId(category));
        st.executeUpdate();
    }

    private int getCategoryId(String category) throws SQLException {
        Connection con = server.getConnection();
        Statement st = con.createStatement();
        ResultSet resultSet = st.executeQuery("SELECT * "
                + "FROM [PrivateMovieCollectionName].[dbo].[Category] "
                + "WHERE Genre = " + category);

        int id = 0;
        while (resultSet.next()) {
            id = resultSet.getInt("id");
        }

        return id;
    }


    /*
    * gets all the movies from the specified category
    */
    public void getMoviesFromCategory(String category) throws SQLException {
        Connection con = server.getConnection();
        Statement st = con.createStatement();

        int id = getCategoryId(category);


        ResultSet rs = st.executeQuery("SELECT * "
                + "FROM [PrivateMovieCollectionName].[dbo].[Movie] "
                + "RIGHT JOIN [PrivateMovieCollectionName].[dbo].[CatMovie] ON"
                + "[PrivateMovieCollectionName].[dbo].[Movie].[id] = [PrivateMovieCollectionName].[dbo].[CatMovie].[MovieId]"
                + "WHERE CategoryId = " + id);

        ArrayList<Movie> moives = new ArrayList<>();
        while (rs.next()) {

        }
    }




    // updates the name of the category from id
//    public boolean updateCategory(Category category) throws SQLServerException, SQLException {
//
//        String sql = "UPDATE [PrivateMovieCollectionName].[dbo].[Category] SET Name = ? WHERE id = " ;
//
//        Connection con = server.getConnection();
//
//        PreparedStatement pst = con.prepareStatement(sql);
//
//        //pst.setString(1, category.());
//
//        int rowsAffected = pst.executeUpdate();
//
//        if (rowsAffected >= 1) {
//            return true;
//        }
//        return false;
//
//    }

}
