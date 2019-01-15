/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package privatemoviecollection.GUI.Model;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import privatemoviecollection.BE.Movie;
import privatemoviecollection.BLL.Exception.MovieCollectionException;
import privatemoviecollection.BLL.MovieManeger;

/**
 *
 * @author Nijas Hansen
 */
public class Model {

    private ObservableList<Movie> movies = FXCollections.observableArrayList();
    private HashMap<String, ObservableList> hashMap = new HashMap<>();
    private ObservableList<String> genres = FXCollections.observableArrayList();
    private MovieManeger logiclayer;
    private Movie movie;

    public Model() throws IOException, SQLException {
        logiclayer = new MovieManeger();
        movies.setAll(logiclayer.getAllMovies());
        genres.setAll(logiclayer.getAllCategory());
        createGenreMoviePairs();
    }

    /**
     * hashmap operations
     *
     * @throws IOException
     * @throws SQLException
     */
    private void createGenreMoviePairs(){
        for (Movie movy : movies) {
            addMoviesToCategory(movy);
        }
    }

    private void addMoviesToCategory(Movie movy) {
        if (movy.getGenres().size() > 0) {

            ObservableList<String> lilleListe = FXCollections.observableArrayList();
            lilleListe.addAll(movy.getGenres());

            for (String genre : lilleListe) {
                if (hashMap.containsKey(genre)) {

                    hashMap.get(genre).add(movy);

                } else {
                    ObservableList<Movie> extraMovies = FXCollections.observableArrayList();
                    extraMovies.add(movy);
                    hashMap.put(genre, extraMovies);
                }
            }
        }

    }

   public ObservableList<String> getAllgenres(){
       return genres;
   }

   public List<String> getHashMap() {
       List<String> ListOfCategorys = new ArrayList(hashMap.values());
       return ListOfCategorys;
   }

      public void deleteMovie(Movie movie){
        try
        {
            logiclayer.deleteMovie(movie);
        } catch (SQLException ex)
        {
            new MovieCollectionException("Error", "Could not delete movie", movie + " is already deleted");
        }
    }

    public Movie CreateMovie(String fileLink, String imdbId) throws SQLException, IOException{
        
        for (Movie movie : movies) {
            if (movie.getFilePath().equals(fileLink)) {
                new MovieCollectionException("Error", "Movie already exist", fileLink + " already exists");
                return null;
            } 
        }
        Movie movie = logiclayer.CreateMovie(fileLink, imdbId);
        return movie; 
        
    }

    public List<Movie> getAllMovies(){
        try
        {
            return logiclayer.getAllMovies();
        } catch (SQLException ex)
        {
            new MovieCollectionException("Error", "Could not find movies", "Contact tech team");
        }
        return null;
    }

    public boolean updateMovie(Movie movie){
        try
        {
            return logiclayer.updateMovie(movie);
        } catch (SQLException ex)
        {
            new MovieCollectionException("Error", "Could not update movie", movie + " does not exist");
        }
        return false;
    }

    public void addGenres(String genre, Movie movie){
        try
        {
            logiclayer.addGenres(genre, movie);
        } catch (SQLException ex)
        {
            new MovieCollectionException("Error", "Could not add " + genre + " to " + movie, movie + " or " + genre + " does not exist");
        }
    }
    
    public void getGenres(Movie movie)
    {
        try
        {
            logiclayer.getGenres(movie);
        } catch (SQLException ex)
        {
            new MovieCollectionException("Error", "Could not get genres", movie + " does not have any genres");
        }
    }

    public boolean setRating(Movie movie, int rating)
    {
        try
        {
            return logiclayer.setRating(movie, rating);
        } catch (SQLException ex)
        {
            new MovieCollectionException("Error", "Could not set " + rating, movie + " already have a rating");
        }
        return false;
    }
    
    public void createCategory(String name){
        try
        {
            logiclayer.createCategory(name);
        } catch (SQLException ex)
        {
            new MovieCollectionException("Error", "Could not create genre", name + " does not exist");
        }
    }

    public void addMovieToCategory(Movie movie, String category){
        try
        {
            logiclayer.addMovieToCategory(movie, category);
        } catch (SQLException ex)
        {
            new MovieCollectionException("Error", "Could not add " + movie + " to " + category, "movie or genre does not exist");
        }
    }

    public void getMoviesFromCategory(String category){
        try
        {
            logiclayer.getMoviesFromCategory(category);
        } catch (SQLException ex)
        {
            new MovieCollectionException("Error", "Could not find movie from genre", category + " does not exist");
        }
    }

    public ArrayList getAllCategory(){
        try
        {
            return logiclayer.getAllCategory();
        } catch (SQLException ex)
        {
            new MovieCollectionException("Error", "Could not recieve genres", "genres does not exist");
        }
        return null;
    }
    
    
    public void lastePlayDate(Movie movie){
        try
        {
            logiclayer.lastPlayDate(movie);
        } catch (SQLException ex)
        {
            new MovieCollectionException("Error", "Could not find last time played", movie + " does not have a last seen date");
        }
    }
    
    public void getMediaPlayer(Movie movie){
        try
        {
            logiclayer.getMediaPlayer(movie);
        } catch (IOException ex)
        {
            new MovieCollectionException("Error", "Could not open mediaplayer", movie + " does not exist");
        }
    }
    
    public void setMediaPlayerPath(File file) {
        try {
            logiclayer.setMediaPlayerPath(file);
        } catch (IOException ex) {
            new MovieCollectionException("Error", "Couldn't find Windows Media Player", "Please navigate to wmplayer.exe");
        }
    }
    
    public boolean checkMediaPlayerPath() {
        try {
            return logiclayer.checkMediaPath();
        } catch (IOException ex) {
            new MovieCollectionException("Error", "Couldn't find Windows Media Player", "Please navigate to wmplayer.exe");
        }
        return false;
    }


}
