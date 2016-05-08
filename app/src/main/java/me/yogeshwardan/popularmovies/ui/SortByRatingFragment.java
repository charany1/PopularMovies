package me.yogeshwardan.popularmovies.ui;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import me.yogeshwardan.popularmovies.R;
import me.yogeshwardan.popularmovies.util.RetrofitConfig;

/**
 * A placeholder fragment containing a simple view.
 */
public class SortByRatingFragment extends Fragment {

    private RetrofitConfig mRetrofitConfig;
    private String mSortBy = "top_rated";

    public SortByRatingFragment() {
    }

    @Override
    public void  onCreate(Bundle bundle){
        super.onCreate(bundle);



        //initialize mRetrofitConfig .
        mRetrofitConfig = new RetrofitConfig(getContext(), mSortBy);


    }


    public static SortByRatingFragment   newInstance(){
        return new SortByRatingFragment();
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView;

        rootView =  inflater.inflate(R.layout.fragment_popular_movies, container, false);
        mRetrofitConfig.makeApiCallAndPopulateRecycleriew(rootView);

        return  rootView;

    }


}
