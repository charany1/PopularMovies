package me.yogeshwardan.popularmovies;

/**
 * Created by yogeshwardancharan on 10/1/16.
 */

import java.util.ArrayList;
import java.util.List;
public class Result {

    public String poster_path;
    public boolean adult;
    public String overview;
    public String release_date;
    public List<Integer> genre_ids = new ArrayList<Integer>();
    public int id;
    public String original_title;
    public String original_language;
    public String title;
    public String backdrop_path;
    public float popularity;
    public int vote_count;
    public boolean video;
    public float vote_average;

}
