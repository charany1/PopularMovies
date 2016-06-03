package me.yogeshwardan.popularmovies.network;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;

import me.yogeshwardan.popularmovies.adapter.MoviesAdapter;
import me.yogeshwardan.popularmovies.model.Movies;
import me.yogeshwardan.popularmovies.model.Result;
import me.yogeshwardan.popularmovies.util.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;
import timber.log.Timber;

/**
 * Created by yogeshwardancharan on 23/3/16.
 */

/**
*Has functionality required for API call using Retrofit and populating views .
*
*/
public class RetrofitConfig {



    Context mContext;
    public static Retrofit retrofit ;
    String mSortBy;

    //for Result obtained in API call in Movie POJO
    ArrayList<Result> mResults;


    public RetrofitConfig(Context context,String sortBy){
        mContext = context;
        mSortBy = sortBy;

        Timber.d("Building retrofit client!");
        retrofit = new Retrofit.Builder()
                .baseUrl(Constants.REQUEST_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }



    /*
    * Function to Asynchronously call TMDB , get response and populate RecyclerView in fragments
    * with the help of MoviesAdapter.
    * */

    public void makeApiCallAndPopulateRecyclerView(final RecyclerView recyclerView){
        Timber.d("makeApiCallAndPopulateRecyclerView() starts!");

        TMDBService tmdbService = retrofit.create(TMDBService.class);
        Call<Movies> call =  tmdbService.discover(mSortBy);


        Timber.d("Add  call to asynch queue using call.enqueue()");
        call.enqueue(new Callback<Movies>() {
            @Override
            public void onResponse(Response<Movies> response) {
                Timber.d("Got response ,code : %d",response.code());
                Movies movies = response.body();
                mResults = (ArrayList) movies.results;
                //set Adapter on recyclerView
                MoviesAdapter moviesAdapter = new MoviesAdapter(mResults,mContext);
                recyclerView.setAdapter(moviesAdapter);
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
