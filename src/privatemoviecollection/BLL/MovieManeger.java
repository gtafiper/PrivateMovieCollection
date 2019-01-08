/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package privatemoviecollection.BLL;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import privatemoviecollection.DALDB.MovieDAO;
import privatemoviecollection.BE.Movie;

/**
 *
 * @author Christian
 */
public class MovieManeger {
    
    MovieDAO movieDAO;
    
    public MovieManeger() throws IOException {
    this.movieDAO = new MovieDAO();
    }
    
    public void deleteMovie(Movie movie) throws SQLException{
        movieDAO.deleteMovie(movie);
    }
    
    public Movie CreateMovie(String name, double rating, String fileLink, String lastView) throws SQLException{
        return movieDAO.createMovie(name, rating, fileLink, lastView);
    }
    
    public List<Movie> getAllMovies() throws SQLException
    {
        return movieDAO.getAllMovies();
    }
    
    public void updateMovie(Movie movie) throws SQLException {
        movieDAO.updateMovie(movie);
    }
    
    public void addGenres(Movie movie) throws SQLException {
        movieDAO.addGenres(movie);
    }
}


