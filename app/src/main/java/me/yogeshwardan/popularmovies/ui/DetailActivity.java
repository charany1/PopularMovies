package me.yogeshwardan.popularmovies.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import com.github.clans.fab.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import butterknife.Bind;
import butterknife.BindInt;
import butterknife.ButterKnife;
import me.yogeshwardan.popularmovies.R;
import me.yogeshwardan.popularmovies.model.Result;
import me.yogeshwardan.popularmovies.util.Constants;

public class DetailActivity extends AppCompatActivity {


    //Bind View using Butterknife.
    @Bind(R.id.backdropIV) ImageView mBackgroundImageView;
    @Bind(R.id.imageIV) ImageView  mSmallImageView;
    @Bind(R.id.nameTV) TextView mNameTextView ;
    @Bind(R.id.relaseDateTV) TextView mReleaseDateTextView;
    @Bind(R.id.ratingTV) TextView mRatingTextView;
    @Bind(R.id.popularityTV) TextView mPopularityTextView;
    @Bind(R.id.overviewTV) TextView mOverViewTextView;

    //Floating action button for Favoriting a movie
    @Bind(R.id.favorite_fab) FloatingActionButton mFavoriteFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("result");
        Result mResult = Parcels.unwrap(bundle.getParcelable("result"));

        //initialize butterknife in this Activity
        ButterKnife.bind(this);

        Picasso.with(this).load(Constants.BACKDROP_IMAGE_PATH_BASE_URL+ mResult.getBackdrop_path()).into(mBackgroundImageView);
        Picasso.with(this).load(Constants.IMAGE_PATH_BASE_URL + mResult.getPoster_path()).into(mSmallImageView);


        mNameTextView.setText(mResult.getTitle());
        mReleaseDateTextView.setText(mResult.getPoster_path());
        mRatingTextView.setText(new Double(mResult.getVote_average()).toString());
        mPopularityTextView.setText(new Double(mResult.getPopularity()).toString());
        String overView = mResult.getOverview();

        if(overView!= null && !overView.trim().equals("")){
            mOverViewTextView.setText(overView);
        }
        else{
            mOverViewTextView.setText("No Overview recieved!");
        }


        //Configuring listener on fab : save movie details in SharePreferences
        mFavoriteFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //star_outline drawable is used to represent that movie is favorite .
                mFavoriteFab.setImageDrawable(getResources().getDrawable(R.drawable.star_outline));
            }
        });


    }


}
