/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package privatemoviecollection.DAL;
import privatemoviecollection.DAL.CheckMediaPlayer;
import java.io.IOException;
import privatemoviecollection.BE.Movie;


/**
 *
 * @author Nijas Hansen
 */
public class MediaPlayer {
    private final static String MEDIAPLAYER_PATH = "";

    private CheckMediaPlayer checkmplayer;
    /**
     * Opens the standard mediaplayer and plays the given String name.
     * @param moviename
     * @throws IOException
     */
    public static void openMediaPlayer(Movie movie) throws IOException {
        Runtime.getRuntime().exec(MEDIAPLAYER_PATH+"/" + movie.getFilePath());



    }


}
