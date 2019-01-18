/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package privatemoviecollection.GUI.Model;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.HashMap;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import privatemoviecollection.BE.Movie;
import privatemoviecollection.BE.MovieImage;
import privatemoviecollection.BLL.Exception.MovieCollectionException;
import privatemoviecollection.BLL.MovieManeger;

/**
 *
 * @author Nijas Hansen
 */
public class Model
{

    public static final String HASH_ALLMOVIES = "All Movies";

    private ObservableList<Movie> movies = FXCollections.observableArrayList();
    private HashMap<String, ObservableList<MovieImage>> hashMap = new HashMap<>();
    private ObservableList<String> hashGenres = FXCollections.observableArrayList();
    private ObservableList<String> genres = FXCollections.observableArrayList();
    private MovieManeger logiclayer;
    private Movie movie;

    public Model()
    {
        try
        {
            logiclayer = new MovieManeger();
            movies.setAll(logiclayer.getAllMovies());
            genres.setAll(logiclayer.getAllCategory());
        } catch (Exception ex)
        {
            new MovieCollectionException("Error", "Trouble connecting to server", "check for proper connection");
        }

    }

    /**
     * hashmap operations
     *
     * @throws IOException
     * @throws SQLException
     */
    public void createGenreMoviePairs(ObservableList<MovieImage> movieImage)
    {

        hashMap.put(HASH_ALLMOVIES, movieImage);
        hashGenres.add(HASH_ALLMOVIES);

        for (MovieImage movy : movieImage)
        {
            addMovieToGenre(movy);
        }
    }

    /**
     * links a MovieImage to a category
     *
     * @param movieImage
     */
    public void addMovieToGenre(MovieImage movieImage)
    {
        if (movieImage.getMovie().getGenres().size() > 0)
        {

            ObservableList<String> temp_list = FXCollections.observableArrayList();
            temp_list.addAll(movieImage.getMovie().getGenres());

            for (String genre : temp_list)
            {
                if (hashMap.containsKey(genre))
                {

                    hashMap.get(genre).add(movieImage);

                } else
                {
                    ObservableList<MovieImage> extraMovies = FXCollections.observableArrayList();
                    extraMovies.add(movieImage);
                    hashMap.put(genre, extraMovies);
                    hashGenres.add(genre);
                }
            }
        }

    }

    /**
     * retrives all Genre Keys form the Hashmap
     *
     * @return
     */
    public ObservableList<String> getAllHashGenres()
    {
        return hashGenres;
    }

    /**
     * Deletes movie for all lists in memory and server
     *
     * @param movieImage
     */
    public void deleteMovie(MovieImage movieImage)
    {
        try
        {
            for (String genre : movieImage.getMovie().getGenres())
            {
                hashMap.get(genre).remove(movieImage);
            }

            Movie temp_movie = null;

            for (int i = 0; i < movies.size(); i++)
            {
                if (movies.get(i).getFilePath().equals(movieImage.getMovie().getFilePath()))
                {
                    movies.remove(movies.get(i));
                }
            }

            hashMap.get(HASH_ALLMOVIES).remove(movieImage);
            logiclayer.deleteMovie(movieImage.getMovie());

        } catch (Exception ex)
        {
            new MovieCollectionException("Error", "Could not delete movie", movieImage + " is already deleted");
        }
    }

    /**
     * Creates a movie in memory an on the server
     *
     * @param fileLink
     * @param imdbId
     * @return
     * @throws SQLException
     * @throws IOException
     */
    public MovieImage CreateMovie(String fileLink, String imdbId) throws SQLException, IOException
    {
        for (Movie movie : movies)
        {
            if (movie.getFilePath().equals(fileLink))
            {
                new MovieCollectionException("Error", "The movie:" + movie
                        + " already exist", fileLink + " already exists");
                return null;
            }
        }
        Movie movie = logiclayer.CreateMovie(fileLink, imdbId);
        MovieImage movieImage = new MovieImage(movie);
        addMovieToGenre(movieImage);
        movies.addAll(movie);
        hashMap.get(HASH_ALLMOVIES).add(movieImage);

        return movieImage;

    }

    /**
     * Retrives all movie og a given genre
     *
     * @param genre
     * @return
     */
    public ObservableList<MovieImage> getMoviesByGenre(String genre)
    {

        return hashMap.get(genre);
    }

    /**
     * Retrives all Movies
     *
     * @return
     */
    public ObservableList<Movie> getAllMovies()
    {
        return movies;
    }

    /**
     * updates the data of the given objects server counterpart
     *
     * @param movie
     * @return
     */
    public boolean updateMovie(Movie movie)
    {
        try
        {
            return logiclayer.updateMovie(movie);
        } catch (SQLException ex)
        {

            new MovieCollectionException("Error", "Could not update movie", movie + " does not exist");
        }
        return false;
    }

    /**
     * Updates the user rating of the movie
     *
     * @param movie
     * @param rating
     * @return
     */
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

    /**
     * creates a new genre
     *
     * @param name
     */
    public void createGenre(String name)
    {
        try
        {
            logiclayer.createCategory(name);
            genres.add(name);
        } catch (SQLException ex)
        {
            new MovieCollectionException("Error", "Could not create genre", name + " does not exist");
        }
    }

    /**
     * deletes a specific genre
     *
     * @param category
     */
    public void deleteGenre(String category)
    {
        try
        {
            logiclayer.deleteCategory(category);
            genres.remove(category);
            hashGenres.remove(category);
        } catch (SQLException ex)
        {

            new MovieCollectionException("Error", "Could not delete genre", category + " does not exist");
        }
    }

    /**
     * Create a mo
     *
     * @param movie
     * @param category
     */
    public void addMovieToNewCategory(MovieImage movie, String category)
    {
        try
        {

            if (!hashMap.get(category).contains(movie))
            {
                if (!hashMap.containsKey(category))
                {
                    ObservableList<MovieImage> hashlist = FXCollections.observableArrayList();
                    hashMap.put(category, hashlist);
                }
                hashMap.get(category).add(movie);
                movie.getMovie().addGenre(category);
                logiclayer.addMovieToCategory(movie.getMovie(), category);
            }

        } catch (SQLException ex)
        {
            new MovieCollectionException("Error", "Could not add " + movie + " to " + category, "movie or genre does not exist");

        }
    }

    /**
     * Retrives all genere
     *
     * @return
     */
    public ObservableList<String> getAllGenres()
    {
        return genres;
    }

    /**
     * retrives the
     *
     * @param movie
     */
    public void refreshPlayDate(Movie movie)
    {
        try
        {
            logiclayer.refreshPlayDate(movie);
        } catch (SQLException ex)
        {
            new MovieCollectionException("Error", "Could not find last time played", movie + " does not have a last seen date");
        }
    }

    /**
     * executes Windows media Player runtime event and plays the given movie in
     * fullscreen
     *
     * @param movie
     */
    public void openMediaPlayer(Movie movie)
    {
        try
        {
            logiclayer.openMediaPlayer(movie);
        } catch (IOException ex)
        {
            new MovieCollectionException("Error", "Could not open mediaplayer", movie + " does not exist");
        }
    }

    /**
     * create a new path to the windows mediaplayer
     *
     * @param file
     */
    public void setMediaPlayerPath(File file)
    {
        try
        {
            logiclayer.setMediaPlayerPath(file);
        } catch (IOException ex)
        {
            new MovieCollectionException("Error", "Couldn't find Windows Media Player", "Please navigate to wmplayer.exe");
        }
    }

    /**
     * retruns true if the Window Media Player path
     *
     * @return
     */
    public boolean checkMediaPlayerPath()
    {
        try
        {
            return logiclayer.checkMediaPath();
        } catch (IOException ex)
        {
            new MovieCollectionException("Error", "Couldn't find Windows Media Player", "Please navigate to wmplayer.exe");
        }
        return false;
    }

    /**
     * returns true if the duedate is more the 2 years
     *
     * @param movie
     * @return
     */
    public boolean isDueDateOver(Movie movie)
    {
        try
        {
            return logiclayer.isDueDateOver(movie);
        } catch (ParseException ex)
        {
            new MovieCollectionException("", "", "to do");
        }
        return false;
    }

    /**
     * removes the given category from the given movie.
     *
     * @param movie
     * @param genre
     */
    public void deleteCategoryFromMovie(MovieImage movie, String genre)
    {
        try
        {
            hashMap.get(genre).remove(movie);
            movie.getMovie().deleteGenre(genre);
            String temp = "";
            for (String hashGenre : hashGenres)
            {
                if (hashMap.get(genre).size() <= 0)
                {
                    temp = hashGenre;

                }

            }
            hashGenres.remove(temp);
            logiclayer.deleteCategoryFromMovie(movie.getMovie(), genre);
        } catch (Exception e)
        {

            new MovieCollectionException("Error", "Couldn't delete genre from movie", "Contact tech team");
        }
    }

}
