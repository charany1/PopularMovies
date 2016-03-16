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
 * A placeholder fragment containing a simple view.
 */
public class PopularMoviesFragment extends Fragment {

    public PopularMoviesFragment() {
    }

    @Override
    public void  onCreate(Bundle bundle){
        setHasOptionsMenu(true);
    }

    String sortBy = "vote_average.desc";

    public boolean onOptionsItemSelected(MenuItem item){
        int selectedMenuItemId = item.getItemId();

        switch(selectedMenuItemId){
            case R.id.popularity :
                sortBy = "popularity.desc";
                makeApiCall();
                break;
            case R.id.rating :
                sortBy = "vote_average.desc";
                makeApiCall();
                break;
            default:
                break;

        }


        return super.onOptionsItemSelected(item);

    }


    Retrofit retrofit;
    View rootView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //get gridview
        rootView =  inflater.inflate(R.layout.fragment_popular_movies, container, false);




        //API call related stuff

        retrofit = new Retrofit.Builder()
                .baseUrl("http://api.themoviedb.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();



        return  rootView;

    }

    public void makeApiCall(){

        TMDBService tmdbService = retrofit.create(TMDBService.class);
        Call<Movies> call =  tmdbService.discover(sortBy);

        final GridView gridView = (GridView)rootView.findViewById(R.id.gridView);


        call.enqueue(new Callback<Movies>() {
            @Override
            public void onResponse(Response<Movies> response) {
                Movies movies = response.body();
                ArrayList<Result> results = (ArrayList) movies.results;
                //set custom MovieAdapter on it to populate it with movie results
                MovieAdapter movieAdapter = new MovieAdapter(getContext(),results );

                gridView.setAdapter(movieAdapter);
                Timber.d("Size of results : %d", results.size());
                String posterPath = results.get(0).poster_path;
                if (posterPath != null)
                    Timber.d("Poster Path = %s", posterPath);
                else
                    Timber.d("Poster Path is null");

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
