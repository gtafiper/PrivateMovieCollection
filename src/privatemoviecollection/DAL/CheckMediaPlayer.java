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
import privatemoviecollection.BE.Movie;

/**
 *
 * @author Nijas Hansen
 */
public class CheckMediaPlayer {

    public static final String PROP_FILE = "src/privatemoviecollection/DAL/mediaplayerpath.path";
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
    //tjecks if it is more then 2years sins the movie has bin playd and if it has a rating belov 6
    public static boolean isDoDateOver(Movie movie) throws ParseException {
        
        if (movie.getLastView() != null) {
            //format the date String to days month and tear 
            SimpleDateFormat dateformat = new SimpleDateFormat("dd MM yyyy");
            //gets todays date
            Date date = new Date();
            dateformat.format(date);
            //formates the movies last play date string to the format vi yuse
            Date lastPlayDate = dateformat.parse(movie.getLastView());
            //doDate is the difrens between todays date and the date the movie was last played
            long doDate = (date.getTime() - lastPlayDate.getTime())/24/3600000;
            //gets the rating if the movie and stors it in rating
            int rating = movie.getRating();
            
            //returns true if the difrens in time is more the 730 days 
            if (doDate >= 730 ) {
                if (rating <= 6 && rating != 0) {
                    return true;
                }

            }
        }

        return false;

    }

}
