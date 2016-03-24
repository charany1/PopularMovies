package me.yogeshwardan.popularmovies.ui;

import android.content.Intent;
import android.os.Parcelable;
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
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;


import org.parceler.Parcels;

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
 * Created by yogeshwardancharan on 17/3/16.
 */
public class SortByPopularityFragment extends Fragment implements AdapterView.OnItemClickListener {

    RetrofitConfig mRetrofitConfig;
    String sortBy = "popularity.desc";

    ArrayList<Result> mResults;

    public SortByPopularityFragment() {
    }

    @Override
    public void  onCreate(Bundle bundle){
        super.onCreate(bundle);



        //initialize mRetrofitConfig .
        mRetrofitConfig = new RetrofitConfig(getContext(),sortBy);




    }


    public static SortByPopularityFragment   newInstance(){
        return new SortByPopularityFragment();
    }





    View rootView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        rootView =  inflater.inflate(R.layout.fragment_popular_movies, container, false);
        mRetrofitConfig.makeApiCallAndUpdateGridView(rootView);

        GridView gridView = (GridView)rootView.findViewById(R.id.gridView);
        gridView.setOnItemClickListener(this);

        return  rootView;

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

        Intent detailActivityLauncher = new Intent(getActivity(), DetailActivity.class);
        Result result = mResults.get(position);
        Parcelable resultParcel =  Parcels.wrap(result);
        Bundle bundle = new Bundle();
        bundle.putParcelable("result", resultParcel);
        detailActivityLauncher.putExtra("result", bundle);



    }


}
