/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package privatemoviecollection.DALDB;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import privatemoviecollection.BE.Movie;
import privatemoviecollection.DAL.OmdbHandler;
import privatemoviecollection.DAL.ServerConnect;

/**
 *
 * @author Bruger
 */
public class MovieDAO {

    private ArrayList<Movie> movies;

    //makes a server connection "sc" that can be accessed throughout the class
    ServerConnect sc;

    public MovieDAO() throws IOException {

        sc = new ServerConnect();
        movies = new ArrayList<>();

    }

    /**
     * updates a movie with new Title, Path, rating, lastView. in the database
     * @param movie
     * @return
     * @throws SQLException
     */
    public boolean updateMovie(Movie movie) throws SQLException {
        String sql = "UPDATE [PrivateMovieCollectionName].[dbo].[Movie] SET name = ?, fileLink = ?, rating = ?, lastView = ?  WHERE id =" + movie.getId();
        //connect to database
        Connection con = sc.getConnection();
        //Make a preparedt statement for security and sets the strings and int
        PreparedStatement pst = con.prepareStatement(sql);

        pst.setString(1, movie.getMovieTitle());
        pst.setString(2, movie.getFilePath());
        pst.setInt(3, movie.getRating());
        pst.setString(4, movie.getLastView());
        //send it back to the database
        int rowsAffected = pst.executeUpdate();
        if (rowsAffected >= 1) {
            return true;
        }
        //closes the connection to the database
        con.close();
        return false;
    }

    /*
    *deletes a movie both on the CatMovie and from the list of Movie in the database
    *@parameter movie
     */
    public void deleteMovie(Movie movie) throws SQLServerException, SQLException {
        //connect to the database
        Connection con = sc.getConnection();
        //tells the database to where the deleted movie should be remove from CatMovie table
        Statement statement = con.createStatement();
        statement.execute(
                "DELETE FROM [PrivateMovieCollectionName].[dbo].[CatMovie] WHERE MovieId = "
                + movie.getId()
        );
        //tells the database to where the deleted movie should be remove from Movie table
        statement.execute(
                "DELETE FROM [PrivateMovieCollectionName].[dbo].[Movie] WHERE id = "
                + movie.getId()
        );
        //close the connection to the database
        con.close();
    }

    /*
    *gets all the movies in the server table Movie
    *@retuns List of all movies
     */
    public ArrayList<Movie> getAllMovies() throws SQLServerException, SQLException {
        //Making a arraylist with Movie and connect to the database.
        ArrayList<Movie> movies = new ArrayList<>();
        Connection con = sc.getConnection();
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM [PrivateMovieCollectionName].[dbo].[Movie]");
        //runs all the movies through
        while (rs.next()) {
            int id = rs.getInt("id");
            String movieTitle = rs.getNString("title");
            int rating = rs.getInt("user_rating");
            String fileLink = rs.getNString("fileLink");
            String lastView = rs.getNString("lastView");
            String year = rs.getNString("year");
            String runtime = rs.getNString("runtime");
            String director = rs.getNString("director");
            String actors = rs.getNString("actors");
            String plot = rs.getNString("plot");
            String imdb_rating = rs.getNString("imdb_rating");
            String poster = rs.getNString("poster");
        //making a Movie object and adds a genre
            Movie movie = new Movie(id, movieTitle, rating, fileLink, lastView,
                    year, runtime, director, actors, plot,
                    imdb_rating, poster);

            getGenres(movie);
            movies.add(movie);

        }
        //close the connection to the database
        con.close();

        return movies;

    }

    /**
     * Create a movie on the database and send it back as a object       
     * @param imdbId
     * @param fileLink
     * @return
     * @throws SQLException
     * @throws java.io.IOException
     */
    public Movie createMovie(String fileLink, String imdbId) throws SQLException, IOException {
        //making a string to tell where it should put in the infomation
        String sql = "INSERT INTO [PrivateMovieCollectionName].[dbo].[Movie] (title, fileLink, "
                + "year, runtime, director, actors, plot, imdb_rating, poster, user_rating) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        //Connect to database with a prepared statement
        Connection con = sc.getConnection();

        PreparedStatement st = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        //making a Hashmap from the omdbhandler and set in the infomation
        HashMap<String, String> movieInfo = OmdbHandler.createHashMap(OmdbHandler.getMovieByImdbID(imdbId));

        st.setString(1, movieInfo.get(OmdbHandler.HASH_TITLE));
        st.setString(2, fileLink);
        st.setString(3, movieInfo.get(OmdbHandler.HASH_YEAR));
        st.setString(4, movieInfo.get(OmdbHandler.HASH_RUNTIME));
        st.setString(5, movieInfo.get(OmdbHandler.HASH_DIRECTOR));
        st.setString(8, movieInfo.get(OmdbHandler.HASH_IMDB_RATING));
        st.setString(6, movieInfo.get(OmdbHandler.HASH_ACTORS));
        st.setString(9, movieInfo.get(OmdbHandler.HASH_POSTER));
        st.setString(7, movieInfo.get(OmdbHandler.HASH_PLOT));
        st.setInt(10, 0);
        
        //upload it to the database
        st.executeUpdate();
        //making a resultset that get the generatedkeys
        ResultSet rs = st.getGeneratedKeys();
        //checking the next line and get the id
        int id = 0;

        if (rs.next()) {
            id = rs.getInt(1);

        }
        //close the connection to the database
        con.close();
        //making a new Movie object
        int rating = 0;
        String lastplaydate = "";

        Movie movie = new Movie(id,
                movieInfo.get(OmdbHandler.HASH_TITLE),
                rating,
                fileLink,
                lastplaydate,
                movieInfo.get(OmdbHandler.HASH_YEAR),
                movieInfo.get(OmdbHandler.HASH_RUNTIME),
                movieInfo.get(OmdbHandler.HASH_DIRECTOR),
                movieInfo.get(OmdbHandler.HASH_ACTORS),
                movieInfo.get(OmdbHandler.HASH_PLOT),
                movieInfo.get(OmdbHandler.HASH_IMDB_RATING),
                movieInfo.get(OmdbHandler.HASH_POSTER));

        String[] genres = movieInfo.get(OmdbHandler.HASH_GENRE).split(", ");

        for (String genre : genres) {
            addGenre(genre, movie);
        }

        return movie;

    }

    /**
     * this adds the genre if the genre doesnt exist already in the database
     * @param movie
     * @throws SQLServerException
     * @throws SQLException
     */
    public void getGenres(Movie movie) throws SQLServerException, SQLException {
        //make connection to the database
        Connection con = sc.getConnection();
        Statement st = con.createStatement();
        //tells the database where to get the genres of the movie
        ResultSet rs = st.executeQuery("SELECT * "
                + "FROM [PrivateMovieCollectionName].[dbo].[Category] "
                + "RIGHT JOIN [PrivateMovieCollectionName].[dbo].[CatMovie] ON"
                + "[PrivateMovieCollectionName].[dbo].[Category].[id] = [PrivateMovieCollectionName].[dbo].[CatMovie].[CategoryId]"
                + "WHERE MovieId = " + movie.getId());

        while (rs.next()) {
            movie.addGenre(rs.getNString("Genre"));
        }

    }

    /**
     * add a genre to the list of genre on the database
     * @param genre
     * @param movie
     * @throws SQLServerException
     * @throws SQLException
     */
    public void addGenre(String genre, Movie movie) throws SQLServerException, SQLException {
        //make connection to the server
        Connection con = sc.getConnection();
        int id = 0;
        //checks if the genre do exist in a movie with the Movieid and categoryid in the database CatMovie and give it to the movie
        if ((id = doItExist(genre)) != 0) {
            String sql = "INSERT INTO [PrivateMovieCollectionName].[dbo].[CatMovie] (CategoryId, MovieId) VALUES (?, ?)";
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setInt(1, id);
            ps.setInt(2, movie.getId());
            ps.executeUpdate();
        //if the genre dont exist it will generate the new genre and put it into Categoy table under genre
        } else {
            String newSql = "INSERT INTO [PrivateMovieCollectionName].[dbo].[Category] (Genre) VALUES (?)";
            PreparedStatement nps = con.prepareStatement(newSql, Statement.RETURN_GENERATED_KEYS);
            nps.setString(1, genre);
            nps.executeUpdate();
        
            ResultSet rs = nps.getGeneratedKeys();
            int newId = 0;
            while (rs.next()) {
                newId = rs.getInt(1);
            }
        //it will insert into the CatMovie with categoryid and a movieid and add the genre to the movie
            String newIdSql = "INSERT INTO [PrivateMovieCollectionName].[dbo].[CatMovie] (CategoryId, MovieId) VALUES (?, ?)";
            PreparedStatement nips = con.prepareStatement(newIdSql, Statement.RETURN_GENERATED_KEYS);

            nips.setInt(1, newId);
            nips.setInt(2, movie.getId());
            nips.executeUpdate();
            movie.addGenre(genre);
        }
        con.close();
    }

    /**
     * check if the genre exist in the database
     * @param con
     * @param genre
     * @return
     * @throws SQLException
     */
    public int doItExist(String genre) throws SQLException {
        //Connect to the database
        Connection con = sc.getConnection();
        Statement st = con.createStatement();
        //checks if the genre exist 
        System.out.println(genre);
        ResultSet rs = st.executeQuery("SELECT * FROM [PrivateMovieCollectionName].[dbo].[Category] "
                + "WHERE Genre = '" + genre + "';");

        while (rs.next()) {

            return rs.getInt("id");
        }

        return 0;
    }

    /**
     * last time the movie are played
     * @param movie
     * @throws SQLServerException
     * @throws SQLException
     */
    public void lastePlayDate(Movie movie) throws SQLServerException, SQLException {
        //make a new calender object and tells how the format should be stored
        Calendar cal = Calendar.getInstance();
        DateFormat df = new SimpleDateFormat("dd/MM/yy");
        //set the date into the movie
        movie.setLastView(df.format(cal.getTime()));
        //tells where it should be updated to
        String sql = "UPDATE [PrivateMovieCollectionName].[dbo].[Movie] SET lastView = ? WHERE id =" + movie.getId();
        //make the connection
        Connection con = sc.getConnection();

        PreparedStatement pst = con.prepareStatement(sql);
        //make a new string to store the date time and add it.
        String date = df.format(cal.getTime());

        pst.setString(1, date);

        movie.setLastView(date);

    }

    /**
     * set and or update the rating of a movie in the database
     * @param movie
     * @param rating
     * @return
     * @throws SQLServerException
     * @throws SQLException
     */
    public boolean setRating(Movie movie, int rating) throws SQLServerException, SQLException {
        //Connect to the database
        Connection con = sc.getConnection();
        //tells where to update the movie with a new rating
        String sql = "UPDATE [PrivateMovieCollectionName].[dbo].[Movie] SET user_rating = ? WHERE id = " + movie.getId();

        PreparedStatement pst = con.prepareStatement(sql);
        //insert the new rating
        pst.setInt(1, rating);

        int rowsAffected = pst.executeUpdate();
        if (rowsAffected >= 1) {
            return true;
        }
        //close the connection to the database
        con.close();
        return false;
    }

}
