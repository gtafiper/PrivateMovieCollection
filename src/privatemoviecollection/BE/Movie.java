/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package privatemoviecollection.BE;

import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Bruger
 */
public class Movie {

    private int id;
    private int rating;
    private double duration;
    private String lastView;
    private String filePath;
    private String movieTitle;
    private String imageURL;
    private String year;
    private String runtime;
    private String director;
    private String actors;
    private String plot;
    private String imdb_rating;
    private String poster;
<<<<<<< HEAD
    private ArrayList<String> moviegenre;
    
=======
    
    
    
    
    private final ObservableList<String> moviegenre;
    public String getGerne;
>>>>>>> parent of 7db8491... Merge branch 'master' of https://github.com/gtafiper/PrivateMovieCollection

    /**
     * Construct the Movie
     *
     * @param id
     * @param title
     * @param rating
     * @param lastView
     * @param year
     * @param runtime
     * @param director
     * @param fileLink
     * @param plot
     * @param imdb_rating
     * @param poster
     * @param actors
     */
    
    public Movie(int id, String movieTitle, int rating, String fileLink, String lastView, String year, String runtime, String director, String actors, String plot, String imdb_rating, String poster)
    {
        this.id = id;
        this.movieTitle = movieTitle;
        this.rating = rating;
        this.filePath = fileLink;
        this.year = year;
        this.runtime = runtime;
        this.director = director;
        this.actors = actors;
        this.plot = plot;
        this.imdb_rating = imdb_rating;
        this.poster = poster;
<<<<<<< HEAD
        
        moviegenre = new ArrayList<>();
=======
        this.rating = 0;
        imageURL = new String();

        moviegenre = FXCollections.observableArrayList();
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    /**
     * gets the lastView in a string
     * @return lastView
     */
    public String getlastView()
    {
        return lastView;
    }

    /**
     * set the lastView in a string
     * @param lastView
     */
    public void setLastView(String lastView)
    {
        this.lastView = lastView;
>>>>>>> parent of 7db8491... Merge branch 'master' of https://github.com/gtafiper/PrivateMovieCollection
    }

    /**
     * Get the genre in a observableList
     * @return
     */
    public ObservableList<String> getGenres()
    {
        return moviegenre;
    }

    /**
     * set the genre in a observableList
     * @param genre
     */
    public void addGenre(String genre)
    {
        moviegenre.add(genre);
    }

    /**
     * delete a genre in a observableList
     * @param genre
     */
    public void deleteGenre (String genre)
    {
        moviegenre.remove(genre);
    }

    /**
     * Get the value of duration
     *
     * @return the value of duration
     */
    public double getDuration()
    {
        return duration;
    }

    /**
     * Set the value of duration
     *
     * @param duration new value of duration
     */
    public void setDuration(double duration)
    {
        this.duration = duration;
    }

    /**
     * Get the value of filePath
     *
     * @return the value of filePath
     */
    public String getFilePath()
    {
        return filePath;
    }

    /**
     * Set the value of filePath
     *
     * @param fileParth new value of filePath
     */
    public void setFilePath(String fileParth)
    {
        this.filePath = fileParth;
    }

    /**
     * Get the value of movieTitle
     *
     * @return the value of movieTitle
     */
    public String getTitle()
    {
        return movieTitle;
    }

    /**
     * Set the value of movieTitle
     *
     * @param title new value of movieTitle
     */
    public void setTitle(String title)
    {
        this.movieTitle = title;
    }

    /**
     * get the value of rating
     * @return the value of rating
     */
    public int getRating()
    {
        return rating;
    }

    /**
     * set the value of rating in a movie
     * @param rating new rating on a movie
     */
    public void setRating(int rating)
    {
        this.rating = rating;
    }

    /**
     * Get the value of songId
     *
     * @return the song Id
     */
    public int getId()
    {
        return id;
    }


    /**
     * get the movieTitle object into a string
     * @return the movietitle to a string
     */
    @Override
    public String toString()
    {
        return "ID: " + id + " Movie title: " + movieTitle + " Rating: " + rating;
    }

    public String getYear() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String getSummry() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String getActors() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String getDirector() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }



}
