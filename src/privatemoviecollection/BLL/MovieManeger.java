/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package privatemoviecollection.BLL;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import privatemoviecollection.DALDB.MovieDAO;
import privatemoviecollection.BE.Movie;
import privatemoviecollection.DAL.CheckMediaPlayer;
import privatemoviecollection.DALDB.CategoryDAO;
import privatemoviecollection.DAL.MediaPlayer;

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

    public Movie CreateMovie(String fileLink, String imdbId) throws SQLException, IOException{
        return movieDAO.createMovie(fileLink, imdbId);
    }

    public List<Movie> getAllMovies() throws SQLException {
        return movieDAO.getAllMovies();
    }

    public boolean updateMovie(Movie movie) throws SQLException {
        return movieDAO.updateMovie(movie);
    }

    public void getGenres(Movie movie) throws SQLException {
        movieDAO.getGenres(movie);
    }

    public void createCategory(String name) throws SQLException {
        cateDAO.createCategory(name);
    }

    public void addMovieToCategory(Movie movie, String category) throws SQLException {
        cateDAO.addMovieToCategory(movie, category);
    }

    public void getMoviesFromCategory(String category) throws SQLException {
        cateDAO.getMoviesFromCategory(category);
    }

    public ArrayList getAllCategory() throws SQLException {
        return cateDAO.getAllCategory();
    }

    public void lastPlayDate (Movie movie) throws SQLException{
       movieDAO.lastePlayDate(movie);
     }
    
    public void getMediaPlayer(Movie movie) throws IOException {
        MediaPlayer.openMediaPlayer(movie);
    }

    public void setMediaPlayerPath(File file) throws IOException {
        CheckMediaPlayer.setMediaPlayerPath(file);
    }
    
    public boolean checkMediaPath() throws IOException {
        return CheckMediaPlayer.CheckMediaPlayerPath();
    }
    
     public void addGenres(Movie movie)
     {
         //TO DO

    }

//    public void updateCategory(Category category) throws SQLException {
//        cateDAO.updateCategory(category);
//    }
}
