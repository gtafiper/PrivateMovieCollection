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
import privatemoviecollection.BE.Movie;
import privatemoviecollection.DAL.ServerConnect;

/**
 *
 * @author Bruger
 */
public class MovieDAO
{
    //makes a server connection "sc" that can be accessed throughout the class
    ServerConnect sc;
    //MetadataExtractor metaExtractor;

    public MovieDAO() throws IOException {
        sc = new ServerConnect();
        //metaExtractor = new MetadataExtractor();
    }
    
    
    //updates a movie witht new Title, Path.
    public boolean updateMovie(Movie movie) throws SQLException {
        String sql = "UPDATE [PrivateMovieCollectionName].[dbo].[Movie] SET name = ?, fileLink = ?, rating = ?, lastView = ?  WHERE id =" + movie.getId();

        Connection con = sc.getConnection();

        PreparedStatement pst = con.prepareStatement(sql);

        pst.setString(1, movie.getTitle());
        pst.setString(2, movie.getFilePath());
        pst.setDouble(3, movie.get());

        int rowsAffected = pst.executeUpdate();
        if (rowsAffected >= 1) {
            return true;
        }
        return false;

    }
    /*
    *deletes a movie both on the CatMovie and from the list of Movie
    *@pahrameter song
    */
    public void deleteMovie(Movie movie) throws SQLServerException, SQLException {
        Connection con = sc.getConnection();

        Statement statement = con.createStatement();
        statement.execute(
                "DELETE FROM [PrivateMovieCollectionName].[dbo].[CatMovie] WHERE MovieId = "
                + movie.getId()
        );
        statement.execute(
                "DELETE FROM [PrivateMovieCollectionName].[dbo].[Movie] WHERE id = "
                + movie.getId()
        );

    }
    /*
    *gets all the movies in the server table Movie
    *@retuns List of all movies
    */
    public List<Movie> getAllMovies() throws SQLServerException, SQLException {
        List<Movie> movies = new ArrayList<>();
        Connection con = sc.getConnection();
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM [PrivateMovieCollectionName].[dbo].[Movie]");
        //runs all the songs through
        while (rs.next()) {
            int id = rs.getInt("id");
            String title = rs.getNString("name");
            String path = rs.getNString("fileLink");
            double rating = rs.getDouble("rating");
            String lastView = rs.getNString("lastView");

            Movie movie = new Movie(path, title, id, rating, lastView);

            movies.add(movie);

        }

        return movies;
    }
    
    /*
    * 
    *@retuns a Movie
    */
    /**
     *  extracts metadat from file, adds it to the Database and returns a song
     * 
     * @param addedFile
     * 
     * @return Movie
     * 
     * @throws IOException
     * @throws FileNotFoundException
     * @throws SAXException
     * @throws TikaException
     * @throws SQLServerException
     * @throws SQLException 
     */
    /*
    public Movie createSong(File addedFile) throws IOException, FileNotFoundException, SAXException, TikaException, SQLServerException, SQLException
    {
        
    }
    */
}
