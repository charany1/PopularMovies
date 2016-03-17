package me.yogeshwardan.popularmovies.ui;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.style.TtsSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;



import java.util.ArrayList;


import me.yogeshwardan.popularmovies.R;
import me.yogeshwardan.popularmovies.adapter.MovieAdapter;
import me.yogeshwardan.popularmovies.model.Movies;
import me.yogeshwardan.popularmovies.model.Result;
import me.yogeshwardan.popularmovies.util.TMDBService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;
import timber.log.Timber;

/**
 * Created by yogeshwardancharan on 17/3/16.
 */
public class SortByPopularityFragment extends Fragment {

    Retrofit retrofit;
    String sortBy = "popularity.desc";

    public SortByPopularityFragment() {
    }

    @Override
    public void  onCreate(Bundle bundle){
        super.onCreate(bundle);



        retrofit = new Retrofit.Builder()
                .baseUrl("http://api.themoviedb.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();


    }


    public static SortByPopularityFragment   newInstance(){
        return new SortByPopularityFragment();
    }





    View rootView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //get gridview




        //API call related stuff


        rootView =  inflater.inflate(R.layout.fragment_popular_movies, container, false);
        makeApiCallAndUpdateGridView(rootView);

        return  rootView;

    }

    @Override
    public void onActivityCreated(Bundle bundle) {


        super.onActivityCreated(bundle);

    }

    public void makeApiCallAndUpdateGridView(View rootView){

        final GridView gridView = (GridView)rootView.findViewById(R.id.gridView);


        TMDBService tmdbService = retrofit.create(TMDBService.class);
        Call<Movies> call =  tmdbService.discover(sortBy);



        call.enqueue(new Callback<Movies>() {
            @Override
            public void onResponse(Response<Movies> response) {
                Movies movies = response.body();
                ArrayList<Result> results = (ArrayList) movies.results;
                //set custom MovieAdapter on it to populate it with movie results
                MovieAdapter movieAdapter = new MovieAdapter(getContext(),results );

                gridView.setAdapter(movieAdapter);

            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(getActivity(), "Call failed!", Toast.LENGTH_SHORT).show();
                Timber.d("Call failed!");
                t.printStackTrace();
            }
        });





    }
}
