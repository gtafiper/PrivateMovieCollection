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
import privatemoviecollection.BE.Category;
import privatemoviecollection.BE.Movie;
import privatemoviecollection.DAL.ServerConnect;

/**
 *
 * @author Nijas Hansen
 */
public class CategoryDAO {

    //establishes a server connect witch can be used in the inter class
    private static ServerConnect server;
    
    /**
     * 
     * @throws IOException 
     */
    public CategoryDAO() throws IOException {

        this.server = new ServerConnect();
    }
    /* 
     * creats a playlist on the sever, using sql
     */
    
    public Category createCategory (String name) throws SQLServerException, SQLException {
        String sql = "INSERT INTO [PrivateMovieCollectionName].[dbo].[Category] (Name) VALUES (?)";

        Connection con = server.getConnection();

        PreparedStatement st = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        st.setString(1, name);

        int rowsAffected = st.executeUpdate();
        //get a sever generated number to use as id
        ResultSet rs = st.getGeneratedKeys();

        int id = 0;

        if (rs.next()) {
            id = rs.getInt(1);
        }

        Category playlist = new Category(name, id);

        return playlist;

    }
    
    /*
    *adds a song to a playlist by inserting a song object and a playlist object into a joind table 
    @SongID
    @PlaylistID
    */
    public void addMovieToCategory(Movie movie, Category category) throws SQLServerException, SQLException {
        Connection con = server.getConnection();
        String sql = "INSERT INTO [PrivateMovieCollectionName].[dbo].[CatMovie] (MovieId, CategoryId) VALUES (?,?)";

        PreparedStatement st = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        st.setInt(1, movie.getId());
        st.setInt(2, category.getId());

        int rowsAffected = st.executeUpdate();

        ResultSet rs = st.getGeneratedKeys();

        if (rs.next()) {
            movie.setPositionID(rs.getInt(1));
        }

    }
    //delete a playlist by playlist id
    public void deletePlayliste(Category category) throws SQLServerException, SQLException {
        Connection con = server.getConnection();
        //sql that delete the playlist from sever tabel
        Statement statement = con.createStatement();
        statement.execute(
                "DELETE FROM [PrivateMovieCollectionName].[dbo].[Category] WHERE id = "
                + category.getId()
        );
        statement.execute(
                "DELETE FROM [PrivateMovieCollectionName].[dbo].[CatMovie] WHERE CategoryId = "
                + category.getId()
        );

    }
    /*
    * gets all playlists from sever
    *@returns all playlists 
    */
    public List<Category> getAllPlaylits() throws SQLException {
        List<Category> categorys = new ArrayList<>();
        Connection con = server.getConnection();
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM [PrivateMovieCollectionName].[dbo].[Category]");

        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getNString("Name");
            Category category = new Category(name, id);
            getSongFromPlaylist(category);

            categorys.add(category);
        }
        return categorys;
    }
    /*
    * gets all the songs on a playlist 
    */
    public void getSongFromPlaylist(Category category) throws SQLException {
        Connection con = server.getConnection();
        Statement st = con.createStatement();
        ResultSet resultSet = st.executeQuery("SELECT * "
                + "FROM [PrivateMovieCollectionName].[dbo].[Movie] "
                + "RIGHT JOIN [PrivateMovieCollectionName].[dbo].[CatMovie] ON [PrivateMovieCollectionName].[dbo].[Movie].[id] = [PrivateMovieCollectionName].[dbo].[CatMovie].[id] "
                + "WHERE CategoryId = " + category.getId()
        );
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String title = resultSet.getNString("Name");
            String path = resultSet.getNString("fileLink");
            String genre = resultSet.getNString("Genre");
            String artist = resultSet.getNString("Artist");
            int position = resultSet.getInt("PositionID");

            Movie movie = new Movie(path, title, id, artist, duration, genre);
            movie.setPositionID(position);
            category.addToPlaylist(movie);
        }
    }

    // updates the name of the playlist from id 
    public boolean updatePlaylist(Category category) throws SQLServerException, SQLException {

        String sql = "UPDATE [PrivateMovieCollectionName].[dbo].[Category] SET Name = ? WHERE id =" + category.getId();

        Connection con = server.getConnection();

        PreparedStatement pst = con.prepareStatement(sql);

        pst.setString(1, category.getTitle());

        int rowsAffected = pst.executeUpdate();

        if (rowsAffected >= 1) {
            return true;
        }
        return false;

    }
    //delets a song from the playlist using a playlist id and the song PositionID 
    public void deleteFromPlayist(Movie movie, Category category) throws SQLException {
        Connection con = server.getConnection();
        Statement st = con.createStatement();

        st.execute(
                "DELETE FROM [CS2018A_32].[dbo].[CatMovie] WHERE CategoryId = " + category.getId()
                + " AND MovieId = " + movie.getPositionID()
        );

    }

}

