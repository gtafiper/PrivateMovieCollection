/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package privatemoviecollection.BE;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Bruger
 */
public class Movie {
    
    private String producer;
    private String filePath;
    private String movieTitle;
    private String genre;
    private StringProperty titleProperty;
    private StringProperty genreProperty;
    private StringProperty producerProperty;
    private int id;
    private int PositionID = 0;
    private double duration;

    /**
     * Construct the song
     *
     * @param fileParth
     * @param Title
     * @param id
     * @param producer
     * @param duration
     * @param genre
     */
    public Movie(String fileParth, String Title, int id, String producer, double duration, String genre)
    {
        this.filePath = fileParth;
        this.movieTitle = Title;
        titleProperty = new SimpleStringProperty(Title);
        this.id = id;
        this.producer = producer;
        producerProperty = new SimpleStringProperty(producer);
        this.duration = duration;
        this.genre = genre;
        genreProperty = new SimpleStringProperty(genre);
    }

    /**
     * get the artist as a stringProperty
     *
     * @return
     */
    public StringProperty getProducerProperty()
    {
        return producerProperty;
    }

    /**
     * get the genre as a stringProperty
     *
     * @return the value of genreProperty
     */
    public StringProperty getGenreProperty()
    {
        return genreProperty;
    }

    /**
     * Get the value of genre
     *
     * @return the value of genre
     */
    public String getGenre()
    {
        return genre;
    }

    /**
     * Set the value of genre
     *
     * @param genre new value of genre
     */
    public void setGenre(String genre)
    {
        this.genre = genre;
        genreProperty.setValue(genre);
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
     * Get the value of producer
     *
     * @return the value of producer
     */
    public String getProducer()
    {
        return producer;

    }

    /**
     * Set the value of producer
     *
     * @param producer new value of artist
     */
    public void setProducer(String producer)
    {
        this.producer = producer;
        producerProperty.setValue(producer);
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
     * @param Title new value of movieTitle
     */
    public void setTitle(String Title)
    {
        this.movieTitle = Title;
        titleProperty.setValue(Title);
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
     * get StringProperty of a title
     *
     * @return Title new value of songTitleProperty
     */
    public StringProperty getTitleProperty()
    {
        return titleProperty;
    }

    /**
     * get the positionID of a id
     *
     * @return the position of ID
     */
    public int getPositionID()
    {
        return PositionID;
    }

    /**
     * set the value of positionID
     *
     * @param PositionID
     */
    public void setPositionID(int PositionID)
    {
        this.PositionID = PositionID;
    }
    
}
