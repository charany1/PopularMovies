package me.yogeshwardan.popularmovies.util;

import me.yogeshwardan.popularmovies.model.Movies;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by yogeshwardancharan on 11/1/16.
 */
public interface TMDBService {

    @GET("3/discover/movie?api_key=f00376bc772927dd62576939bc5c1321")
    Call<Movies> discover(@Query("sort_by") String sortBy);
}
