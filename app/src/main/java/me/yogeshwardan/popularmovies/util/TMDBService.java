package me.yogeshwardan.popularmovies.util;

import me.yogeshwardan.popularmovies.model.Movies;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by yogeshwardancharan on 11/1/16.
 */

/*
* Interface required by Retrofit to convert REST API calls to Java Interface.
*
* */
public interface TMDBService {

    @GET("3/discover/movie?api_key="+Constants.API_KEY)
    Call<Movies> discover(@Query("sort_by") String sortBy);
}
