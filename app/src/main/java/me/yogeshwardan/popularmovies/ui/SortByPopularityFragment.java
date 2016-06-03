package me.yogeshwardan.popularmovies.ui;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import me.yogeshwardan.popularmovies.R;
import me.yogeshwardan.popularmovies.network.RetrofitConfig;

/**
 * Created by yogeshwardancharan on 17/3/16.
 */
public class SortByPopularityFragment extends Fragment  {

    public static final int SPAN_SIZE = 2;


    private RetrofitConfig mRetrofitConfig;
    private String mSortBy = "popular";
    private View mRootView;




    public SortByPopularityFragment() {
    }

    @Override
    public void  onCreate(Bundle bundle){
        super.onCreate(bundle);



        //initialize mRetrofitConfig .
        mRetrofitConfig = new RetrofitConfig(getContext(),mSortBy);




    }


    public static SortByPopularityFragment   newInstance(){
        return new SortByPopularityFragment();
    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mRootView = inflater.inflate(R.layout.fragment_popular_movies, container, false);

        //configuring recyclerView :we set GridLayoutManager on recylerView here
        // adapter is set in RetrofitConfit#makeApiCallAndPopulateRecyclerView
        RecyclerView recyclerView = (RecyclerView) mRootView.findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager gridLayoutManager = new GridLayoutManager(getContext(),SPAN_SIZE);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(gridLayoutManager);
        mRetrofitConfig.makeApiCallAndPopulateRecyclerView(recyclerView);

        return mRootView;

    }



}
