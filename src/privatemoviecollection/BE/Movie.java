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
    private int positionID = 0;
    private double rating;
    private double duration;
    private String lastView;
    private String filePath;
    private String movieTitle;
    
    public ArrayList<String> moviegenre;

    /**
     * Construct the Movie
     *
     * @param id
     * @param name
     * @param rating
     * @param lastView
     * @param fileLink
     */
    public Movie(int id, String name, double rating, String lastView, String fileLink)
    {
        this.id = id;
        this.movieTitle = name;
        this.rating = rating;
        this.lastView = lastView;
        this.filePath = fileLink;
        
        moviegenre = new ArrayList();
        
        
    }

    /**
     * gets the lastView in a string
     * @return lastView
     */
    public String getLastView()
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
    public ArrayList<String> getGenres()
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
    public double getRating()
    {
        return rating;
    }
    
    /**
     * set the value of rating in a movie
     * @param rating new rating on a movie
     */
    public void setRating(double rating)
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
     * get the positionID of a id
     *
     * @return the position of ID
     */
    public int getPositionID()
    {
        return positionID;
    }

    /**
     * set the value of positionID
     *
     * @param positionID
     */
    public void setPositionID(int positionID)
    {
        this.positionID = positionID;
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
    
    
    
}
