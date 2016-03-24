package me.yogeshwardan.popularmovies.util;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import org.parceler.Parcels;

import java.util.ArrayList;

import me.yogeshwardan.popularmovies.R;
import me.yogeshwardan.popularmovies.adapter.MovieAdapter;
import me.yogeshwardan.popularmovies.model.Movies;
import me.yogeshwardan.popularmovies.model.Result;
import me.yogeshwardan.popularmovies.ui.DetailActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;
import timber.log.Timber;

/**
 * Created by yogeshwardancharan on 23/3/16.
 */

/*
* Feature functionality required for API call using Retrofit and populating views .
*
* */
public class RetrofitConfig  {

    Context mContext;
    public static Retrofit retrofit ;
    String mSortBy;

    //for Result obtained in API call in Movie POJO
    ArrayList<Result> mResults;


    public RetrofitConfig(Context context,String sortBy){
        mContext = context;
        mSortBy = sortBy;
        retrofit = new Retrofit.Builder()
                .baseUrl(Constants.REQUEST_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }


    /*
    * Function to Asynchronously call TMDB , get response and populate GridView in fragments
    * with the help of MovieAdapter.
    * */
    public void makeApiCallAndUpdateGridView(View rootView){

        final GridView gridView = (GridView)rootView.findViewById(R.id.gridView);


        TMDBService tmdbService = retrofit.create(TMDBService.class);
        Call<Movies> call =  tmdbService.discover(mSortBy);



        call.enqueue(new Callback<Movies>() {
            @Override
            public void onResponse(Response<Movies> response) {
                Movies movies = response.body();
                mResults = (ArrayList) movies.results;
                //set custom MovieAdapter on it to populate it with movie results
                MovieAdapter movieAdapter = new MovieAdapter(mContext,mResults);

                gridView.setAdapter(movieAdapter);

            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(mContext, "Call failed!", Toast.LENGTH_SHORT).show();
                Timber.d("Call failed!");
                t.printStackTrace();
            }
        });





    }


}
