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
import java.util.List;
import java.util.Locale.Category;
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
     * creats a playlist on the sever, using sql
     */
    
    public void createCategory (String name) throws SQLServerException, SQLException {
        String sql = "INSERT INTO [PrivateMovieCollectionName].[dbo].[Category] (Name) VALUES (?)";

        Connection con = server.getConnection();

        PreparedStatement st = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        st.setString(1, name);

        st.executeUpdate();
         
    }
    
    /*
    *adds a song to a playlist by inserting a song object and a playlist object into a joind table 
    @SongID
    @PlaylistID
    */
    public void addMovieToCategory(Movie movie, String category) throws SQLServerException, SQLException {
        Connection con = server.getConnection();
        String sql = "INSERT INTO [PrivateMovieCollectionName].[dbo].[CatMovie] (MovieId, CategoryId) VALUES (?,?)";
        PreparedStatement st = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ResultSet rs = st.executeQuery("SELECT * FROM [PrivateMovieCollectionName].[dbo].[Category] WHERE name = " + category);
        
        int id = 0;
        
        while (rs.next()) {
            id = rs.getInt("name");
        }
        
        st.setInt(1, movie.getId());
        st.setInt(2, id);

        st.executeUpdate();
    }
    
   
    /*
    * gets all the songs on a playlist 
    */
    public void getMovieFromCategory(String category) throws SQLException {
        Connection con = server.getConnection();
        PreparedStatement pst = con.prepareStatement(category);
        ResultSet resultSet = pst.executeQuery("SELECT * "
                + "FROM [PrivateMovieCollectionName].[dbo].[Category] "
                + "WHERE Category = name");
        
        while (resultSet.next()) {
            pst.setNString(1, category);
        }
    }

    // updates the name of the playlist from id 
    public boolean updateCategory(Category category) throws SQLServerException, SQLException {

        String sql = "UPDATE [PrivateMovieCollectionName].[dbo].[Category] SET Name = ? WHERE id = id";

        Connection con = server.getConnection();

        PreparedStatement pst = con.prepareStatement(sql);

        //pst.setString(1, category.());

        int rowsAffected = pst.executeUpdate();

        if (rowsAffected >= 1) {
            return true;
        }
        return false;

    }
    
}

