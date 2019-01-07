/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package privatemoviecollection.BE;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Bruger
 */
public class Movie {
    
    private double rating;
    private String lastView;
    private String filePath;
    private String movieTitle;
    private int id;
    private int positionID = 0;
    private double duration;
    
    private ObservableList<String> moviegenre;
    

    /**
     * Construct the song
     *
     * @param fileLink
     * @param name
     * @param id
     * @param rating
     * @param lastView
     */
    public Movie(String fileLink, String name, int id, double rating, String lastView)
    {
        this.filePath = fileLink;
        this.movieTitle = name;
        this.id = id;
        this.duration = rating;
        this.lastView = lastView;
        
        moviegenre = FXCollections.observableArrayList();
    }

    public String getLastView()
    {
        return lastView;
    }

    public void setLastView(String lastView)
    {
        this.lastView = lastView;
    }

    /**
     *
     * @return
     */
    public ObservableList<String> getGenre()
    {
        return moviegenre;
    }
    
    public void setGenre(String genre)
    {
        moviegenre.add(genre);
    }
    
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
     * Get the value of songTitle
     *
     * @return the value of songTitle
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

    public double getRating()
    {
        return rating;
    }
    
    public void setRating()
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
    
    @Override
    public String toString()
    {
        return movieTitle;
    }
    
}
