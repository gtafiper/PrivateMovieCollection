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
public class CategoryDAO
{

    //establishes a server connect witch can be used in the inter class
    private ServerConnect server;

    public CategoryDAO() throws IOException
    {

        this.server = new ServerConnect();
    }

    /**
     * retrives all the MovieCatogory saved on the server
     *
     * @return @throws SQLServerException
     * @throws SQLException
     */
    public ArrayList getAllCategory() throws SQLServerException, SQLException
    {
        Connection con = server.getConnection();
        ArrayList list = new ArrayList<>();
        String sql = "SELECT * FROM [PrivateMovieCollectionName].[dbo].[Category]";
        PreparedStatement st = con.prepareStatement(sql);

        ResultSet rs = st.executeQuery();

        while (rs.next())
        {
            String genre = rs.getNString("Genre");
            list.add(genre);
        }

        return list;

    }

    /**
     * sends the Input to the Server and autogenerates an table ID
     *
     * @param name
     * @throws SQLServerException
     * @throws SQLException
     */
    public void createCategory(String name) throws SQLServerException, SQLException
    {
        Connection con = server.getConnection();

        String sql = "INSERT INTO [PrivateMovieCollectionName].[dbo].[Category] (Genre) VALUES (?)";

        PreparedStatement st = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        st.setString(1, name);

        st.executeUpdate();

    }

    /**
     * Saves the Movie-Category link
     *
     * @param movie
     * @param category
     * @throws SQLServerException
     * @throws SQLException
     */
    public void addMovieToCategory(Movie movie, String category) throws SQLServerException, SQLException
    {
        Connection con = server.getConnection();
        String sql = "INSERT INTO [PrivateMovieCollectionName].[dbo].[CatMovie] (MovieId, CategoryId) VALUES (?,?)";
        PreparedStatement st = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        st.setInt(1, movie.getId());
        st.setInt(2, getCategoryId(category));
        st.executeUpdate();
    }

    /**
     * retrives a Categories ID
     *
     * @param category
     * @return
     * @throws SQLException
     */
    private int getCategoryId(String category) throws SQLException
    {
        Connection con = server.getConnection();
        Statement st = con.createStatement();
        ResultSet resultSet = st.executeQuery("SELECT * "
                + "FROM [PrivateMovieCollectionName].[dbo].[Category] "
                + "WHERE Genre = '" + category + "';");

        int id = 0;
        while (resultSet.next())
        {
            id = resultSet.getInt("id");
        }

        return id;
    }

    /**
     * Retrives all movies linking to a specific Category
     *
     * @param category
     * @throws SQLException
     */
    public void getMoviesFromCategory(String category) throws SQLException
    {
        Connection con = server.getConnection();
        Statement st = con.createStatement();

        int id = getCategoryId(category);

        ResultSet rs = st.executeQuery("SELECT * "
                + "FROM [PrivateMovieCollectionName].[dbo].[Movie] "
                + "RIGHT JOIN [PrivateMovieCollectionName].[dbo].[CatMovie] ON"
                + "[PrivateMovieCollectionName].[dbo].[Movie].[id] = [PrivateMovieCollectionName].[dbo].[CatMovie].[MovieId]"
                + "WHERE CategoryId = " + id);

        ArrayList<Movie> moives = new ArrayList<>();
        while (rs.next())
        {

        }
    }

    /**
     * Deletes all Instances og the Category
     *
     * @param category
     * @throws SQLServerException
     * @throws SQLException
     */
    public void deleteCategory(String category) throws SQLServerException, SQLException
    {
        Connection con = server.getConnection();
        Statement st = con.createStatement();

        st.execute("DELETE FROM"
                + " [PrivateMovieCollectionName].[dbo].[CatMovie] WHERE CategoryId = " + getCategoryId(category)
        );

        st.execute("DELETE FROM"
                + " [PrivateMovieCollectionName].[dbo].[Category] WHERE Genre = '" + category + "';"
        );

        con.close();
    }

    /**
     * deletes a specific Movie-Category link
     *
     * @param movie
     * @param category
     * @throws SQLServerException
     * @throws SQLException
     */
    public void deleteCategoryFromMovie(Movie movie, String category) throws SQLServerException, SQLException
    {
        Connection con = server.getConnection();
        Statement st = con.createStatement();

        int id = getCategoryId(category);

        st.execute("DELETE "
                + "FROM [PrivateMovieCollectionName].[dbo].[CatMovie] "
                + "WHERE CategoryId = " + id + " AND MovieId = " + movie.getId());

        con.close();
    }

}
