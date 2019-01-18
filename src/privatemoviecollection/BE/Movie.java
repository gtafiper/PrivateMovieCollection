/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package privatemoviecollection.BE;

import java.util.ArrayList;

/**
 *
 * @author Bruger
 */
public class Movie
{

    private int id;
    private int rating;
    private String lastView;
    private String filePath;
    private String movieTitle;
    private String year;
    private String runtime;
    private String director;
    private String actors;
    private String plot;
    private String imdb_rating;
    private String poster;
    private ArrayList<String> moviegenre;

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
    public Movie(int id, String movieTitle, int rating, String fileLink, String year, String runtime, String director, String actors, String plot, String imdb_rating, String poster)
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
        lastView = null;

        moviegenre = new ArrayList<>();
    }

    /**
     * Get the genre in a observableList
     *
     * @return
     */
    public ArrayList<String> getGenres()
    {
        return moviegenre;
    }

    /**
     * set the genre in a observableList
     *
     * @param genre
     */
    public void addGenre(String genre)
    {
        moviegenre.add(genre);
    }

    /**
     * delete a genre in a observableList
     *
     * @param genre
     */
    public void deleteGenre(String genre)
    {
        moviegenre.remove(genre);
    }

    /**
     * 
     * @return the id of the given movie 
     */
    public int getId()
    {
        return id;
    }

    /**
     * sets the id of the given movie
     * 
     * @param id 
     */
    public void setId(int id)
    {
        this.id = id;
    }

    /**
     * returns the rating on a movie
     * @return 
     */
    public int getRating()
    {
        return rating;
    }

    /**
     * sets the rating on a movie
     * @param rating 
     */
    public void setRating(int rating)
    {
        this.rating = rating;
    }

    /**
     * returns last view date on given movie
     * @return 
     */
    public String getLastView()
    {
        return lastView;
    }

    /**
     * sets last view date on movie
     * @param lastView 
     */
    public void setLastView(String lastView)
    {
        this.lastView = lastView;
    }

    /**
     * returns the file path 
     * 
     **/
    public String getFilePath()
    {
        return filePath;
    }

    /**
     * sets the file path
     * @param filePath 
     */
    public void setFilePath(String filePath)
    {
        this.filePath = filePath;
    }

    /**
     * returns movie title
     * @return 
     */
    public String getMovieTitle()
    {
        return movieTitle;
    }

    /**
     * sets the movie title 
     * @param movieTitle 
     */
    public void setMovieTitle(String movieTitle)
    {
        this.movieTitle = movieTitle;
    }

    /**
     * returns the year on a movie
     * @return 
     */
    public String getYear()
    {
        return year;
    }

    /**
     * sets the year on a movie
     * @param year 
     */
    public void setYear(String year)
    {
        this.year = year;
    }

    /**
     * returns runtime on a movie
     * 
     **/
    public String getRuntime()
    {
        return runtime;
    }

    /**
     * sets runtime on a movie
     * 
    **/
    public void setRuntime(String runtime)
    {
        this.runtime = runtime;
    }

    /**
     * returns the director of a movie
     * @return 
     */
    public String getDirector()
    {
        return director;
    }

    /**
     * sets the director on a movie
     * @param director 
     */
    public void setDirector(String director)
    {
        this.director = director;
    }

    /**
     * returns the actors in a movie
     * 
    **/
    public String getActors()
    {
        return actors;
    }

    /**
     * sets the actors on a movie
     * 
    **/
    public void setActors(String actors)
    {
        this.actors = actors;
    }

    /**
     * returns the plot on a movie
     * @return 
     */
    public String getPlot()
    {
        return plot;
    }

    /**
     * sets the plot on a movie
     * @param plot 
     */
    public void setPlot(String plot)
    {
        this.plot = plot;
    }

    /**
     * returns the IMDB rating on a movie
     * 
    **/
    public String getImdb_rating()
    {
        return imdb_rating;
    }

    /**
     * sets the IMDB Rating on a movie
     * @param imdb_rating 
     */
    public void setImdb_rating(String imdb_rating)
    {
        this.imdb_rating = imdb_rating;
    }

    /**
     * returns the poster of the movie
     * 
    **/
    public String getPoster()
    {
        return poster;
    }

    /**
     * sets the poster on a movie
     * 
    **/
    public void setPoster(String poster)
    {
        this.poster = poster;
    }

    /**
     * returns the object as a string 
     * @return 
     */
    @Override
    public String toString()
    {
        return movieTitle;
    }

}
