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
public class MovieDAO
{
    private ArrayList<Movie> movies;

    //makes a server connection "sc" that can be accessed throughout the class
    ServerConnect sc;

    public MovieDAO() throws IOException {

        sc = new ServerConnect();
        movies = new ArrayList<>();

    }


    //updates a movie with new Title, Path,  rating, lastView.
    public boolean updateMovie(Movie movie) throws SQLException {
        String sql = "UPDATE [PrivateMovieCollectionName].[dbo].[Movie] SET name = ?, fileLink = ?, rating = ?, lastView = ?  WHERE id =" + movie.getId();

        Connection con = sc.getConnection();

        PreparedStatement pst = con.prepareStatement(sql);

        pst.setString(1, movie.getTitle());
        pst.setString(2, movie.getFilePath());
        pst.setString(3, movie.getRating());
        pst.setString(4, movie.getlastView());

        int rowsAffected = pst.executeUpdate();
        if (rowsAffected >= 1) {
            return true;
        }
        con.close();
        return false;
    }

    /*
    *deletes a movie both on the CatMovie and from the list of Movie
    *@parameter movie
    */
    public void deleteMovie(Movie movie) throws SQLServerException, SQLException {
        Connection con = sc.getConnection();

        Statement statement = con.createStatement();
        statement.execute(
                "DELETE FROM [PrivateMovieCollectionName].[dbo].[CatMovie] WHERE MovieId = "
                + movie.getId()
        );
        statement.execute(
                "DELETE FROM [PrivateMovieCollectionName].[dbo].[Movie] WHERE id = "
                + movie.getId()
        );
        con.close();
    }

    /*
    *gets all the movies in the server table Movie
    *@retuns List of all movies
    */
    public ArrayList<Movie> getAllMovies() throws SQLServerException, SQLException {
        ArrayList<Movie> movies = new ArrayList<>();
        Connection con = sc.getConnection();
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM [PrivateMovieCollectionName].[dbo].[Movie]");
        //runs all the movies through
        while (rs.next()) {
            int id = rs.getInt("id");
            String title = rs.getNString("title");
            String rating = rs.getNString("user_rating");
            //String lastView = rs.getNString("lastView");
            String path = rs.getNString("fileLink");

            Movie movie = new Movie(id, title, rating, path);

            movies.add(movie);
        }
        con.close();
        return movies;

    }

    /**
     * Create a movie on the server and send it back as a object
     * @param imdbId
     * @param fileLink
     * @return
     * @throws SQLException
     * @throws java.io.IOException
     */
    public Movie createMovie(String fileLink, String imdbId) throws SQLException, IOException
    {
        String sql = "INSERT INTO [PrivateMovieCollectionName].[dbo].[Movie] (title, fileLink, year, runtime, director,actors, plot, imdb_rating, poster) VALUES (?, ?, ?, ?);";

        Connection con = sc.getConnection();

        PreparedStatement st = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        HashMap<String, String> movieInfo = OmdbHandler.createHashMap(OmdbHandler.getMovieByImdbID(imdbId));

        st.setString(1, movieInfo.get(OmdbHandler.HASH_TITLE));
        st.setString(10, movieInfo.get(OmdbHandler.HASH_IMDB_RATING));
        st.setString(8, movieInfo.get(OmdbHandler.HASH_ACTORS));
        st.setString(12, movieInfo.get(OmdbHandler.HASH_GENRE));
        st.setString(5, movieInfo.get(OmdbHandler.HASH_YEAR));
        st.setString(11, movieInfo.get(OmdbHandler.HASH_POSTER));
        st.setString(6, movieInfo.get(OmdbHandler.HASH_RUNTIME));
        st.setString(9, movieInfo.get(OmdbHandler.HASH_PLOT));
        st.setString(7, movieInfo.get(OmdbHandler.HASH_DIRECTOR));
        st.setString(3, fileLink);


        st.executeUpdate();

        ResultSet rs = st.getGeneratedKeys();

        int id = 0;

        if (rs.next()) {
            id = rs.getInt(1);

        }
        con.close();
        Movie movie = new Movie(id, movieInfo.get(OmdbHandler.HASH_TITLE), movieInfo.get(OmdbHandler.HASH_IMDB_RATING), fileLink);

        return movie;

    }

    /**
     * this adds the genre if the genre doesnt exist already
     * @param movie
     * @throws SQLServerException
     * @throws SQLException
     */
    public void getGenres (Movie movie) throws SQLServerException, SQLException {
        Connection con = sc.getConnection();
        Statement st = con.createStatement();


        ResultSet rs = st.executeQuery("SELECT * "
                + "FROM [PrivateMovieCollectionName].[dbo].[Category] "
                + "RIGHT JOIN [PrivateMovieCollectionName].[dbo].[CatMovie] ON"
                + "[PrivateMovieCollectionName].[dbo].[Category].[id] = [PrivateMovieCollectionName].[dbo].[CatMovie].[CategoryId]"
                + "WHERE MovieId = " + movie.getId());

        while (rs.next())
        {
            movie.addGenre(rs.getNString("Category"));
        }

    }

    public void addGenre (String genre, Movie movie) throws SQLServerException, SQLException
    {
        Connection con = sc.getConnection();
        int id = 0;
        if ((id = doItExist(con, genre)) != 0){
        String sql = "INSERT INTO [PrivateMovieCollectionName].[dbo].[CatMovie] (CategoryId, MovieId) VALUES (?, ?)";
        PreparedStatement ps = con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);

        ps.setInt(1, id);
        ps.setInt(2, movie.getId());
        ps.executeUpdate();

        } else {
            String newSql = "INSERT INTO [PrivateMovieCollectionName.[dbo].[Category] (Category) VALUES (?)";
            PreparedStatement nps = con.prepareStatement(newSql,Statement.RETURN_GENERATED_KEYS);
            nps.setString(1, genre);
            nps.executeUpdate();

            ResultSet rs = nps.getGeneratedKeys();
            int newId = 0;
            while(rs.next())
            {
                newId = rs.getInt(1);
            }
            String newIdSql = "INSERT INTO [PrivateMovieCollectionName.[dbo].[CatMovie] (CategoryId, MovieId) VALUES (?, ?)";
            PreparedStatement nips = con.prepareStatement(newIdSql,Statement.RETURN_GENERATED_KEYS);

            nips.setInt(1, newId);
            nips.setInt(2, movie.getId());
            nips.executeUpdate();
        }

    }

    public int doItExist(Connection con, String genre) throws SQLException
    {
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM [PrivateMocieCollectionName].[dbo].[Category] WHERE Category = " + genre);

        while (rs.next())
        {

            return rs.getInt("id");
        }
        return 0;
    }

    public void lastePlayDate(Movie movie) throws SQLServerException, SQLException{

        Calendar cal = Calendar.getInstance();
        DateFormat df = new SimpleDateFormat("dd/MM/yy");

        movie.setLastView(df.format(cal.getTime()));

        String sql = "UPDATE [PrivateMovieCollectionName].[dbo].[Movie] SET lastView = ? WHERE id =" + movie.getId();

        Connection con = sc.getConnection();

        PreparedStatement pst = con.prepareStatement(sql);

        String date = df.format(cal.getTime());

        pst.setString(1, date);

        movie.setLastView(date);

    }
}
