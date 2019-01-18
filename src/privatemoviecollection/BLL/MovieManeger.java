/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package privatemoviecollection.BLL;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
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
public class MovieManeger
{

    MovieDAO movieDAO;
    CategoryDAO cateDAO;

    public MovieManeger() throws IOException
    {
        this.movieDAO = new MovieDAO();
        this.cateDAO = new CategoryDAO();
    }

    /**
     *
     * @param movie
     * @throws SQLException
     */
    public void deleteMovie(Movie movie) throws SQLException
    {
        movieDAO.deleteMovie(movie);
    }

    /**
     *
     *
     * @param fileLink
     * @param imdbId
     * @return
     * @throws SQLException
     * @throws IOException
     */
    public Movie CreateMovie(String fileLink, String imdbId) throws SQLException, IOException
    {
        return movieDAO.createMovie(fileLink, imdbId);
    }

    /**
     *
     * @return @throws SQLException
     */
    public List<Movie> getAllMovies() throws SQLException
    {
        return movieDAO.getAllMovies();
    }

    /**
     *
     * @param movie
     * @return
     * @throws SQLException
     */
    public boolean updateMovie(Movie movie) throws SQLException
    {
        
        return movieDAO.updateMovie(movie);
    }

    /**
     *
     * @param movie
     * @throws SQLException
     */
    public void getGenres(Movie movie) throws SQLException
    {
        movieDAO.getGenres(movie);
    }

    /**
     *
     * @param name
     * @throws SQLException
     */
    public void createCategory(String name) throws SQLException
    {
        cateDAO.createCategory(name);
    }

    /**
     *
     * @param movie
     * @param category
     * @throws SQLException
     */
    public void addMovieToCategory(Movie movie, String category) throws SQLException
    {
        cateDAO.addMovieToCategory(movie, category);
    }

    /**
     *
     * @param category
     * @throws SQLException
     */
    public void getMoviesFromCategory(String category) throws SQLException
    {
        cateDAO.getMoviesFromCategory(category);
    }

    /**
     *
     * @return @throws SQLException
     */
    public ArrayList getAllCategory() throws SQLException
    {
        return cateDAO.getAllCategory();
    }

    /**
     *
     * @param movie
     * @throws SQLException
     */
    public void refreshPlayDate(Movie movie) throws SQLException
    {
        movieDAO.refreshPlayDate(movie);
    }

    /**
     *
     * @param movie
     * @throws IOException
     */
    public void openMediaPlayer(Movie movie) throws IOException
    {
        MediaPlayer.openMediaPlayer(movie);
    }

    /**
     *
     * @param file
     * @throws IOException
     */
    public void setMediaPlayerPath(File file) throws IOException
    {
        CheckMediaPlayer.setMediaPlayerPath(file);
    }

    /**
     *
     * @return @throws IOException
     */
    public boolean checkMediaPath() throws IOException
    {
        return CheckMediaPlayer.CheckMediaPlayerPath();
    }

    /**
     *
     * @param genre
     * @param movie
     * @throws SQLException
     */
    public void addGenres(String genre, Movie movie) throws SQLException
    {
        movieDAO.addGenre(genre, movie);
    }

    /**
     *
     * @param movie
     * @return
     * @throws ParseException
     */
    public boolean isDueDateOver(Movie movie) throws ParseException
    {
        return CheckMediaPlayer.isDueDateOver(movie);
    }

    /**
     *
     * @param category
     * @throws SQLException
     */
    public void deleteCategory(String category) throws SQLException
    {
        cateDAO.deleteCategory(category);
    }

    /**
     *
     * @param movie
     * @param category
     * @throws SQLException
     */
    public void deleteCategoryFromMovie(Movie movie, String category) throws SQLException
    {
        cateDAO.deleteCategoryFromMovie(movie, category);
    }

    /**
     *
     * @param movie
     * @param rating
     * @return
     * @throws SQLServerException
     * @throws SQLException
     */
    public boolean setRating(Movie movie, int rating) throws SQLServerException, SQLException
    {
        return movieDAO.setRating(movie, rating);
    }
}
