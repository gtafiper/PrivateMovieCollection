/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package privatemoviecollection.BE;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Nijas Hansen
 */
public class Category {

    private String title;
    private StringProperty titleProperty;
    private int id;
    private ObservableList<Movie> movies;

    /**
     * Constructs the PlayList.
     *
     * @param title
     * @param id
     */
    public Category(String title, int id)
    {
        this.title = title;
        this.id = id;
        movies = FXCollections.observableArrayList();
        titleProperty = new SimpleStringProperty(title);
    }

    /**
     * Get the value of id
     *
     * @return the value of id
     */
    public int getId()
    {
        return id;
    }

    public StringProperty getTitleProperty()
    {
        return titleProperty;
    }

    /**
     * Get the value of songs
     *
     * @return the value of songs
     */
    public ObservableList<Movie> getMovies()
    {
        return movies;
    }

    /**
     * Get the value of title
     *
     * @return the value of title
     */
    public String getTitle()
    {
        return title;
    }

    /**
     * Set the value of title
     *
     * @param title new value of title
     */
    public void setTitle(String title)
    {
        this.title = title;
        titleProperty.setValue(title);

    }

    /**
     * Add a song to the PlayList.
     *
     * @param song
     */
    public void addToCategory(Movie movie)
    {
        movies.add(movie);
    }

    /**
     * Remove a song from songs.
     *
     * @param movie
     */
    public void RemoveMovieFromCategory(Movie movie)
    {
        movies.remove(movie);
    }

    /**
     * Remove a song from songs by index number.
     *
     * @param index
     */
    public void deleteFromCategory(int index)
    {
        movies.remove(index);
    }

    /**
     * object to string metode.
     *
     * @return title
     */
    @Override
    public String toString()
    {
        return title;
    }
}
