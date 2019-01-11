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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import privatemoviecollection.BE.Movie;
import privatemoviecollection.DAL.ServerConnect;

/**
 *
 * @author Bruger
 */
public class MovieDAO
{
    private ObservableList<Movie> movies;

    //makes a server connection "sc" that can be accessed throughout the class
    ServerConnect sc;

    public MovieDAO() throws IOException {

        sc = new ServerConnect();
        movies = FXCollections.observableArrayList();

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
        con.close();
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
        con.close();
    }

    /*
    *gets all the movies in the server table Movie
    *@retuns List of all movies
    */
    public ObservableList<Movie> getAllMovies() throws SQLServerException, SQLException {
        ObservableList<Movie> movies = FXCollections.observableArrayList();
        Connection con = sc.getConnection();
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM [PrivateMovieCollectionName].[dbo].[Movie]");
        //runs all the movies through
        while (rs.next()) {
            int id = rs.getInt("id");
            String title = rs.getNString("title");
            double rating = rs.getDouble("user_rating");
            String lastView = rs.getNString("lastView");
            String path = rs.getNString("fileLink");

            Movie movie = new Movie(id, title, rating, lastView, path);

            movies.add(movie);
        }
        con.close();
        return movies;

    }

    /**
     * Create a movie on the server and send it back as a object
     * @param title
     * @param rating
     * @param fileLink
     * @param lastView
     * @return
     * @throws SQLException
     */
    public Movie createMovie(String title, double rating, String fileLink, String lastView) throws SQLException
    {
        String sql = "INSERT INTO [PrivateMovieCollectionName].[dbo].[Movie] (title, user_rating, fileLink, lastView) VALUES (?, ?, ?, ?);";

        Connection con = sc.getConnection();

        PreparedStatement st = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        st.setString(1, title);
        st.setDouble(2, rating);
        st.setString(3, fileLink);
        st.setString(4, lastView);

        st.executeUpdate();

        ResultSet rs = st.getGeneratedKeys();

        int id = 0;

        if (rs.next()) {
            id = rs.getInt(1);

        }
        con.close();
        Movie movie = new Movie(id, title, rating, fileLink, lastView);
        
        return movie;

    }

    /**
     * this adds the genre if the genre doesnt exist already
     * @param movie
     * @throws SQLServerException
     * @throws SQLException
     */
    public void addGenres (Movie movie) throws SQLServerException, SQLException {
        Connection con = sc.getConnection();
        Statement st = con.createStatement();


        ResultSet rs = st.executeQuery("SELECT * "
                + "FROM [PrivateMovieCollectionName].[dbo].[Category] "
                + "RIGHT JOIN [PrivateMovieCollectionName].[dbo].[CatMovie] ON"
                + "[PrivateMovieCollectionName].[dbo].[Category].[id] = [PrivateMovieCollectionName].[dbo].[CatMovie].[CategoryId]"
                + "WHERE MovieId = " + movie.getId());

        while (rs.next())
        {
            movie.addGenre(rs.getNString("Category"));
        }

    }
}
