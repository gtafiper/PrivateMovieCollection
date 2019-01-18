/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package privatemoviecollection.DAL;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import privatemoviecollection.BE.Movie;

/**
 *
 * @author Nijas Hansen
 */
public class MediaPlayer
{

    /**
     * Opens the standard mediaplayer and plays the given the movie from its
     * URL.
     *
     * @param moviename
     * @throws IOException
     */
    public static void openMediaPlayer(Movie movie) throws IOException
    {
        Properties mediaplayerProperties = new Properties();
        FileInputStream is = new FileInputStream(CheckMediaPlayer.PROP_FILE);
        mediaplayerProperties.load(is);

        String path = mediaplayerProperties.getProperty("Path");

        is.close();
        File file = new File(movie.getFilePath());
        String exepath = path + " \"" + file.getAbsolutePath().replace("\\", "/") + "\" /fullscreen";
        Runtime.getRuntime().exec(exepath);

    }
}
