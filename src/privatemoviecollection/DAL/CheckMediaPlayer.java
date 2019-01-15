/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package privatemoviecollection.DAL;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;


import java.util.logging.Level;
import java.util.logging.Logger;
import privatemoviecollection.BE.Movie;
import privatemoviecollection.BLL.Exception.MovieCollectionException;
import privatemoviecollection.GUI.FXMLDocumentController;

/**
 *
 * @author Nijas Hansen
 */
public class CheckMediaPlayer {

    private static final String PROP_FILE = "src/privatemoviecollection/DAL/mediaplayerpath.path";
    //public static final String PROP_FILE = "src/privatemoviecollection/DAL/mediaplayerpath.path";
    public static final String TEMP_PROPFILE = "src/privatemoviecollection/DAL/temp_mediaplayerpath.path";

    public static Boolean CheckMediaPlayerPath() throws IOException {
        Properties mediaplayerProperties = new Properties();
        FileInputStream is = new FileInputStream(PROP_FILE);
        mediaplayerProperties.load(is);

        String path = mediaplayerProperties.getProperty("Path");
        File file = new File(path);
        is.close();
        if (!file.exists()) {
            return false;
        }
        return true;
    }

    public static void setMediaPlayerPath(File path) throws IOException {
        File tempPropfile = new File(TEMP_PROPFILE);
        BufferedWriter bw = new BufferedWriter(new FileWriter(tempPropfile));

        bw.write("Path=" + path.getAbsolutePath().replaceAll("\\\\", "/"));
        bw.close();
        Files.copy(tempPropfile.toPath(), new File(PROP_FILE).toPath(), StandardCopyOption.REPLACE_EXISTING);
        Files.delete(tempPropfile.toPath());

    }

    public static boolean isDoDateOver(Movie movie) throws ParseException {

        SimpleDateFormat dateformat = new SimpleDateFormat("dd MM yyyy");

        Date date = new Date();
        dateformat.format(date);
        Date lastPlayDate = dateformat.parse(movie.getlastView());
        long doDate = date.getTime() - lastPlayDate.getTime();
        int rating = movie.getRating();
        if ((doDate >= 730) && (rating > 6 && rating != 0)) {

            return true;
        }

        return false;

    }
}
