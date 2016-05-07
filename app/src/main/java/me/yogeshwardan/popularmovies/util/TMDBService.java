package me.yogeshwardan.popularmovies.util;

import me.yogeshwardan.popularmovies.model.Movies;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by yogeshwardancharan on 11/1/16.
 */

/*
* Interface required by Retrofit to convert REST API calls to Java Interface.
*
* */
public interface TMDBService {

    @GET("movie/{sortBy}?api_key="+Constants.API_KEY)
    Call<Movies> discover(@Path("sortBy") String sortBy);
}
