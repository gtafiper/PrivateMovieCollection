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

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public int getRating()
    {
        return rating;
    }

    public void setRating(int rating)
    {
        this.rating = rating;
    }

    public String getLastView()
    {
        return lastView;
    }

    public void setLastView(String lastView)
    {
        this.lastView = lastView;
    }

    public String getFilePath()
    {
        return filePath;
    }

    public void setFilePath(String filePath)
    {
        this.filePath = filePath;
    }

    public String getMovieTitle()
    {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle)
    {
        this.movieTitle = movieTitle;
    }

    public String getYear()
    {
        return year;
    }

    public void setYear(String year)
    {
        this.year = year;
    }

    public String getRuntime()
    {
        return runtime;
    }

    public void setRuntime(String runtime)
    {
        this.runtime = runtime;
    }

    public String getDirector()
    {
        return director;
    }

    public void setDirector(String director)
    {
        this.director = director;
    }

    public String getActors()
    {
        return actors;
    }

    public void setActors(String actors)
    {
        this.actors = actors;
    }

    public String getPlot()
    {
        return plot;
    }

    public void setPlot(String plot)
    {
        this.plot = plot;
    }

    public String getImdb_rating()
    {
        return imdb_rating;
    }

    public void setImdb_rating(String imdb_rating)
    {
        this.imdb_rating = imdb_rating;
    }

    public String getPoster()
    {
        return poster;
    }

    public void setPoster(String poster)
    {
        this.poster = poster;
    }

    @Override
    public String toString()
    {
        return movieTitle;
    }

}
