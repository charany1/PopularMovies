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
public class RetrofitConfig implements AdapterView.OnItemClickListener {

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
    * Function to Asynchronously call TMDB , get response and populate GridView in fragments
    * with the help of MovieAdapter.
    * */
    public void makeApiCallAndUpdateGridView(View rootView){
        Timber.d("makeApiCallAndUpdateGridView() starts!");

        final GridView gridView = (GridView)rootView.findViewById(R.id.gridView);


        TMDBService tmdbService = retrofit.create(TMDBService.class);
        Call<Movies> call =  tmdbService.discover(mSortBy);


        Timber.d("Add  call to asynch queue using call.enqueue()");
        call.enqueue(new Callback<Movies>() {
            @Override
            public void onResponse(Response<Movies> response) {

                Timber.d("Got response ,code : %d",response.code());
                Movies movies = response.body();
                mResults = (ArrayList) movies.results;
                //set custom MovieAdapter on it to populate it with movie results
                MovieAdapter movieAdapter = new MovieAdapter(mContext,mResults);
                Timber.d("setting moviewAdapter and onItemClickListener on gridView");
                gridView.setAdapter(movieAdapter);
                gridView.setOnItemClickListener(RetrofitConfig.this);

            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(mContext, "Call failed!", Toast.LENGTH_SHORT).show();
                Timber.d("Call failed!");
                t.printStackTrace();
            }
        });





    }

    /**
     * Callback method to be invoked when an item in this AdapterView has
     * been clicked.
     * <p>
     * Implementers can call getItemAtPosition(position) if they need
     * to access the data associated with the selected item.
     *
     * @param parent   The AdapterView where the click happened.
     * @param view     The view within the AdapterView that was clicked (this
     *                 will be a view provided by the adapter)
     * @param position The position of the view in the adapter.
     * @param id       The row id of the item that was clicked.
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Timber.d("onItemClick() starts!");

        Intent detailActivityLauncher = new Intent(mContext, DetailActivity.class);
        detailActivityLauncher.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        Timber.d("Getting item using getItemAtPosition(position!)");
        Result result = (Result)parent.getItemAtPosition(position);
        if(result == null){
            Timber.d("Result == null");
        }
        else{
        Timber.d("Got result,wrap() using Parcels.wrap() ");
        }
        Parcelable resultParcel =  Parcels.wrap(result);
        Bundle bundle = new Bundle();
        bundle.putParcelable("result", resultParcel);
        detailActivityLauncher.putExtra("result", bundle);

        Timber.d("startAcitvity() to launch detailsActivityLauncher!");
        mContext.startActivity(detailActivityLauncher);




    }



}
