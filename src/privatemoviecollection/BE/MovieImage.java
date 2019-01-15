/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package privatemoviecollection.BE;

import javafx.scene.image.ImageView;

/**
 *
 * @author Thorbjørn Schultz Damkjær
 */
public class MovieImage
{

    private ImageView imageview;
    private Movie movie;

    public MovieImage(Movie movie)
    {

        this.movie = movie;
        imageview = new ImageView(movie.getPoster());
        imageview.setFitHeight(210);
        imageview.setFitWidth(140);
    }

    /**
     * Get the value of movie
     *
     * @return the value of movie
     */
    public Movie getMovie()
    {
        return movie;
    }

    /**
     * Set the value of movie
     *
     * @param movie new value of movie
     */
    public void setMovie(Movie movie)
    {
        this.movie = movie;
    }

    /**
     * Get the value of imageview
     *
     * @return the value of imageview
     */
    public ImageView getImageview()
    {
        return imageview;
    }

    /**
     * Set the value of imageview
     *
     * @param imageview new value of imageview
     */
    public void setImageview(ImageView imageview)
    {
        this.imageview = imageview;
    }

}
