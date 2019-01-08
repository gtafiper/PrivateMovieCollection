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

    public MovieDAO() throws IOException {
        sc = new ServerConnect();
    }


    //updates a movie with new Title, Path,  rating, lastView.
    public boolean updateMovie(Movie movie) throws SQLException {
        String sql = "UPDATE [PrivateMovieCollectionName].[dbo].[Movie] SET name = ?, fileLink = ?, rating = ?, lastView = ?  WHERE id =" + movie.getId();

        Connection con = sc.getConnection();

        PreparedStatement pst = con.prepareStatement(sql);

        pst.setString(1, movie.getTitle());
        pst.setString(2, movie.getFilePath());
        pst.setDouble(3, movie.getRating());
        pst.setString(4, movie.getLastView());

        int rowsAffected = pst.executeUpdate();
        if (rowsAffected >= 1) {
            return true;
        }
        return false;
    }

    /*
    *deletes a movie both on the CatMovie and from the list of Movie
    *@parameter movie
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
    public ArrayList<Movie> getAllMovies() throws SQLServerException, SQLException {
        ArrayList<Movie> movies = new ArrayList<>();
        Connection con = sc.getConnection();
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM [PrivateMovieCollectionName].[dbo].[Movie]");
        //runs all the movies through
        while (rs.next()) {
            int id = rs.getInt("id");
            String title = rs.getNString("name");
            double rating = rs.getDouble("rating");
            String lastView = rs.getNString("lastView");
            String path = rs.getNString("fileLink");

            Movie movie = new Movie(id, title, rating, lastView, path);

            movies.add(movie);
        }
        return movies;

    }

    /**
     * Create a movie on the server and send it back as a object
     * @param name
     * @param rating
     * @param fileLink
     * @param lastView
     * @return
     * @throws SQLException
     */
    public Movie createMovie(String name, double rating, String fileLink, String lastView) throws SQLException
    {
        String sql = "INSERT INTO [PrivateMovieCollectionName].[dbo].[Movie] (name, rating, fileLink, lastView) VALUES (?, ?, ?, ?);";

        Connection con = sc.getConnection();

        PreparedStatement st = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        st.setString(1, name);
        st.setDouble(2, rating);
        st.setString(3, fileLink);
        st.setString(4, lastView);

        st.executeUpdate();

        ResultSet rs = st.getGeneratedKeys();

        int id = 0;

        if (rs.next()) {
            id = rs.getInt(1);

        }

        Movie movie = new Movie(id, name, rating, fileLink, lastView);

        return movie;

    }
    
    public void addGenres (String category) throws SQLServerException, SQLException {
        Connection con = sc.getConnection();
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery("SELECT * "
                + "FROM [PrivateMovieCollectionName].[dbo].[Category] "
                + "WHERE name =" + category);
        
        int id = rs.getInt("id");
            String title = rs.getNString("name");
            double rating = rs.getDouble("rating");
            String lastView = rs.getNString("lastView");
            String path = rs.getNString("fileLink");
        
        Movie mv = new Movie(0, category, 0, category, category);
        while (rs.next())
        {
            rs = (ResultSet) mv.moviegenre;
        }
        
        System.out.println("det virker");
    }
}
