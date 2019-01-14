/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package privatemoviecollection.DAL;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

/**
 *
 * @author Thorbjørn Schultz Damkjær
 */
public class OmdbHandler
{
    
    private static final String URL = "http://www.omdbapi.com/?i=IMDBID&plot=full&apikey=APIKEY";
    private static final String APIKEY = "5551e9bb";
    private static final String HASH_TITLE = "Title";
    private static final String HASH_YEAR = "Year";
    private static final String HASH_RUNTIME = "Runtime";
    private static final String HASH_GENRE = "Genre";
    private static final String HASH_DIRECTOR = "Director";
    private static final String HASH_ACTORS = "Actors";
    private static final String HASH_PLOT = "Plot";
    private static final String HASH_POSTER = "Poster";
    private static final String HASH_IMDB_RATING = "imdbRating";
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException
    {
        String jsonResponse = getMovieByImdbID("tt3896198");
        System.out.println(jsonResponse);
        HashMap<String, String>  hash = createHashMap(jsonResponse);
        System.out.println(hash.get("Title"));
        System.out.println(hash.get("Year"));
        System.out.println(hash.get("Plot"));
    }
    
    private static String getMovieByImdbID(String id) throws IOException
    {
        String requestUrl = URL
                .replace("IMDBID", id)
                .replace("APIKEY", APIKEY);
        return sendGetRequest(requestUrl);
    }
    
    private static String sendGetRequest(String requestUrl) throws MalformedURLException, IOException
    {
        StringBuffer response = new StringBuffer();
        
        URL url = new URL(requestUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept","*/*");
        connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        InputStream stream = connection.getInputStream();
        InputStreamReader reader = new InputStreamReader(stream);
        BufferedReader buffer = new BufferedReader(reader);
        String line;
        while ((line = buffer.readLine()) != null)
        {
            response.append(line);
        }
        buffer.close();
        connection.disconnect();
        
        return response.toString();
    }

    private static HashMap<String, String> createHashMap (String jsonResponse)
    {
        String[] parts = jsonResponse.split("\"");
        HashMap<String, String> hashmap = new HashMap();
        for (int i = 0; i < parts.length; i++)
        {
            if(parts[i].contains(HASH_TITLE))
            {
                hashmap.put(HASH_TITLE, parts[i+2]);
            }
            if(parts[i].contains(HASH_YEAR))
            {
                hashmap.put(HASH_YEAR, parts[i+2]);
            }
            if(parts[i].contains(HASH_RUNTIME))
            {
                hashmap.put(HASH_RUNTIME, parts[i+2]);
            }
            if(parts[i].contains(HASH_GENRE))
            {
                hashmap.put(HASH_GENRE, parts[i+2]);
            }
            if(parts[i].contains(HASH_DIRECTOR))
            {
                hashmap.put(HASH_DIRECTOR, parts[i+2]);
            }
            if(parts[i].contains(HASH_ACTORS))
            {
                hashmap.put(HASH_ACTORS, parts[i+2]);
            }
            if(parts[i].contains(HASH_PLOT))
            {
                hashmap.put(HASH_PLOT, parts[i+2]);
            }
            if(parts[i].contains(HASH_POSTER))
            {
                hashmap.put(HASH_POSTER, parts[i+2]);
            }
            if(parts[i].contains(HASH_IMDB_RATING))
            {
                hashmap.put(HASH_IMDB_RATING, parts[i+2]);
            }
        }
        return hashmap;
    }
    
    
    //to do get methods
    
    

}