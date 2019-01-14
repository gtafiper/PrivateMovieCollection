/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package privatemoviecollection.GUI.Model;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import privatemoviecollection.BE.Movie;
import privatemoviecollection.BLL.MovieManeger;


/**
 *
 * @author Nijas Hansen
 */
public class Model {
    MovieManeger movieManger;

    private ObservableList<Movie> movies = FXCollections.observableArrayList();
    private HashMap<String, ObservableList> hashMap = new HashMap<>();
    private ObservableList<String> genres = FXCollections.observableArrayList();
    private MovieManeger logiclayer;

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
    private void createGenreMoviePairs() throws IOException, SQLException {
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
   
   public void deleteMovie(Movie movie) throws SQLException{
        movieManger.deleteMovie(movie);
    }
    
    public Movie CreateMovie(String name, double rating, String fileLink, String lastView) throws SQLException{
        return movieManger.CreateMovie(name, rating, fileLink, lastView);
    }
    
    public List<Movie> getAllMovies() throws SQLException {
        return movieManger.getAllMovies();
    }
    
    public boolean updateMovie(Movie movie) throws SQLException {
        return movieManger.updateMovie(movie);
    }
    
    public void addGenres(Movie movie) throws SQLException {
        movieManger.addGenres(movie);
    }
    
    public void createCategory(String name) throws SQLException {
        movieManger.createCategory(name);
    }
    
    public void addMovieToCategory(Movie movie, String category) throws SQLException {
        movieManger.addMovieToCategory(movie, category);
    }
    
    public void getMoviesFromCategory(String category) throws SQLException {
        movieManger.getMoviesFromCategory(category);
    }
    
    public ArrayList getAllCategory() throws SQLException {
        return movieManger.getAllCategory();
    }


}
