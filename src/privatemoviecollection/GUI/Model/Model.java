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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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

    private ObservableList<Movie> movies = FXCollections.observableArrayList();
    private HashMap<String, ObservableList<MovieImage>> hashMap = new HashMap<>();
    private ObservableList<String> hashGenres = FXCollections.observableArrayList();
    private ObservableList<String> genres = FXCollections.observableArrayList();
    private MovieManeger logiclayer;
    private Movie movie;

    public static final String HASH_ALLMOVIES = "All Movies";

    public Model()
    {
        try
        {
            logiclayer = new MovieManeger();
            movies.setAll(logiclayer.getAllMovies());
            genres.setAll(logiclayer.getAllCategory());
        } catch (IOException ex)
        {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex)
        {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * hashmap operations
     *
     * @throws IOException
     * @throws SQLException
     */
    public void createGenreMoviePairs(ObservableList<MovieImage> movieimage)
    {

        hashMap.put(HASH_ALLMOVIES, movieimage);
        hashGenres.add(HASH_ALLMOVIES);

        for (MovieImage movy : movieimage)
        {
            addMovieToCategories(movy);
        }
    }

    public void addMovieToCategories(MovieImage movy)
    {
        if (movy.getMovie().getGenres().size() > 0)
        {

            ObservableList<String> lilleListe = FXCollections.observableArrayList();
            lilleListe.addAll(movy.getMovie().getGenres());

            for (String genre : lilleListe)
            {
                if (hashMap.containsKey(genre))
                {

                    hashMap.get(genre).add(movy);

                } else
                {
                    ObservableList<MovieImage> extraMovies = FXCollections.observableArrayList();
                    extraMovies.add(movy);
                    hashMap.put(genre, extraMovies);
                    hashGenres.add(genre);
                }
            }
        }

    }

    public ObservableList<String> getAllHashGenres()
    {
        return hashGenres;
    }

    public List<String> getHashMap()
    {
        List<String> ListOfCategorys = new ArrayList(hashMap.values());
        return ListOfCategorys;
    }

    public void deleteMovie(MovieImage delMovie)
    {
        try
        {

            for (String genre : delMovie.getMovie().getGenres())
            {
                hashMap.get(genre).remove(delMovie);
            }
            Movie temp_movie = null;
            for (int i = 0; i < movies.size(); i++)
            {
                if (movies.get(i).getFilePath().equals(delMovie.getMovie().getFilePath()))
                {
                    movies.remove(movies.get(i));
                }
            }
            hashMap.get(HASH_ALLMOVIES).remove(delMovie);
            logiclayer.deleteMovie(delMovie.getMovie());

        } catch (SQLException ex)
        {
            new MovieCollectionException("Error", "Could not delete movie", delMovie + " is already deleted");
        }
    }

    public MovieImage CreateMovie(String fileLink, String imdbId) throws SQLException, IOException
    {
        System.out.println("dav");
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
        addMovieToCategories(movieImage);
        movies.addAll(movie);
        hashMap.get(HASH_ALLMOVIES).add(movieImage);

        return movieImage;

    }

    public ObservableList<MovieImage> getMoviesByGenre(String genre)
    {

        return hashMap.get(genre);
    }

    public List<Movie> getAllMovies()
    {
        try
        {
            return logiclayer.getAllMovies();
        } catch (SQLException ex)
        {
            new MovieCollectionException("Error", "Could not find movies", "Contact tech team");
        }
        return null;
    }

    public boolean updateMovie(Movie movie)
    {
        try
        {
            System.out.println("model update");
            return logiclayer.updateMovie(movie);
        } catch (SQLException ex)
        {
            ex.printStackTrace();
            new MovieCollectionException("Error", "Could not update movie", movie + " does not exist");
        }
        return false;
    }

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

    public void deleteGenre(String category)
    {
        try
        {
            logiclayer.deleteCategory(category);
            genres.remove(category);
            hashGenres.remove(category);
        } catch (SQLException ex)
        {
            ex.printStackTrace();
            new MovieCollectionException("Error", "Could not delete genre", category + " does not exist");
        }
    }

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
            ex.printStackTrace();
        }
    }

    public void getMoviesFromCategory(String category)
    {
        try
        {
            logiclayer.getMoviesFromCategory(category);
        } catch (SQLException ex)
        {
            new MovieCollectionException("Error", "Could not find movie from genre", category + " does not exist");
        }
    }

    public ObservableList<String> getAllGenres()
    {
        return genres;
    }

    public void lastePlayDate(Movie movie)
    {
        try
        {
            logiclayer.lastPlayDate(movie);
        } catch (SQLException ex)
        {
            new MovieCollectionException("Error", "Could not find last time played", movie + " does not have a last seen date");
        }
    }

    public void getMediaPlayer(Movie movie)
    {
        try
        {
            logiclayer.getMediaPlayer(movie);
        } catch (IOException ex)
        {
            new MovieCollectionException("Error", "Could not open mediaplayer", movie + " does not exist");
        }
    }

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

    public boolean isDoDateOver(Movie movie)
    {
        try
        {
            return logiclayer.isDoDateOver(movie);
        } catch (ParseException ex)
        {
            new MovieCollectionException("", "", "to do");
        }
        return false;
    }

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
            e.printStackTrace();
            new MovieCollectionException("Error", "Couldn't delete genre from movie", "Contact tech team");
        }
    }

}
