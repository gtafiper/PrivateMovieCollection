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
    private String rating;
    private double duration;
    private String lastView;
    private String filePath;
    private String movieTitle;
    private String imageURL;

    private ObservableList<String> moviegenre;
    public String getGerne;

    /**
     * Construct the Movie
     *
     * @param id
     * @param name
     * @param rating
     * @param lastView
     * @param fileLink
     */
    public Movie(int id, String name, String rating, String fileLink)
    {
        this.id = id;
        this.movieTitle = name;
        this.rating = rating;
        this.filePath = fileLink;
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
    public String getRating()
    {
        return rating;
    }

    /**
     * set the value of rating in a movie
     * @param rating new rating on a movie
     */
    public void setRating(String rating)
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
