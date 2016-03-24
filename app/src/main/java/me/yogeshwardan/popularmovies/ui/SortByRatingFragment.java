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
import me.yogeshwardan.popularmovies.util.RetrofitConfig;
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
public class SortByRatingFragment extends Fragment {

    RetrofitConfig mRetrofitConfig;
    String sortBy = "vote_average.desc";

    public SortByRatingFragment() {
    }

    @Override
    public void  onCreate(Bundle bundle){
        super.onCreate(bundle);



        //initialize mRetrofitConfig .
        mRetrofitConfig = new RetrofitConfig(getContext(),sortBy);


    }


    public static SortByRatingFragment   newInstance(){
        return new SortByRatingFragment();
    }



    View rootView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        rootView =  inflater.inflate(R.layout.fragment_popular_movies, container, false);
        mRetrofitConfig.makeApiCallAndUpdateGridView(rootView);

        return  rootView;

    }


}
