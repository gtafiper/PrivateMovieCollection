/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package privatemoviecollection.GUI.Model;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import privatemoviecollection.BE.Movie;
import privatemoviecollection.DALDB.MovieDAO;

/**
 *
 * @author Nijas Hansen
 */
public class Model {

    private ObservableList<Movie> movies = FXCollections.observableArrayList();
    public HashMap<String, ObservableList> hashMap = new HashMap<>();

    /**
     * hashmap operations
     * @throws IOException
     * @throws SQLException
     */
    public void HashmapofCategorys() throws IOException, SQLException {

        MovieDAO mvdao = new MovieDAO();



        ObservableList<Movie> movies = mvdao.getAllMovies();

        for (Movie movy : movies) {

            if (movy.getGenres().size() > 0) {

            ObservableList<String> lilleListe = FXCollections.observableArrayList();
            lilleListe.addAll(movy.getGenres());

            for (String genre : lilleListe) {
                if (hashMap.containsKey(genre)) {

                    hashMap.get(genre).add(movy);

                }
                else {
                    ObservableList<Movie> extraMovies = FXCollections.observableArrayList();
                    extraMovies.add(movy);
                    hashMap.put(genre, extraMovies);
                }
            }
            }

        }

        addValues("1", "Action");
        addValues("2", "Crime");
        addValues("3", "Comedy");
        addValues("4", "Romantic");
        addValues("5", "Horror");
        addValues("6", "Thriller");
        addValues("7", "Western");
        addValues("8", "Adventure");
        addValues("9", "Science Fiction");

        


        Iterator it = hashMap.keySet().iterator();
        ObservableList tempList = null;

        while (it.hasNext()) {
            int key = (int) it.next();
            tempList = hashMap.get(key);
            if(tempList != null) {
                for (Object value : tempList) {
                    System.out.println("Key : "+key+ " , Value : "+value);
                }
            }
        }
        
       
        
        
    }

    public List<String> getHashMap() {
       List<String> ListOfCategorys = new ArrayList(hashMap.values());
        return ListOfCategorys;
    }
     
    
    
    
    

    /**
     * adds values to the hashmap
     * @param key
     * @param value
     */
    private void addValues(String key, String value) {

        ObservableList tempList = null;
        if (hashMap.containsKey(key)) {
            tempList = hashMap.get(key);
            if (tempList == null) {
                tempList = FXCollections.observableArrayList();
                tempList.add(value);
            }
            else {
                tempList = FXCollections.observableArrayList();
                tempList.add(value);
            }
            hashMap.put(key, tempList);
        }
    }
}
