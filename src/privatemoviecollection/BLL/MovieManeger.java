/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package privatemoviecollection.BLL;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;
import java.util.Locale.Category;
import privatemoviecollection.DALDB.MovieDAO;
import privatemoviecollection.BE.Movie;
import privatemoviecollection.DALDB.CategoryDAO;

/**
 *
 * @author Christian
 */
public class MovieManeger {
    
    MovieDAO movieDAO;
    CategoryDAO cateDAO;
    
    public MovieManeger() throws IOException {
    this.movieDAO = new MovieDAO();
    this.cateDAO = new CategoryDAO();
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
    
    public void createCategory(String name) throws SQLException {
        cateDAO.createCategory(name);
    }
    
    public void addMovieToCategory(Movie movie, String category) throws SQLException {
        cateDAO.addMovieToCategory(movie, category);
    }
    
    public void getMovieFromCategory(String category) throws SQLException {
        cateDAO.getMovieFromCategory(category);
    }
    
//    public void updateCategory(Category category) throws SQLException {
//        cateDAO.updateCategory(category);
//    }
}


