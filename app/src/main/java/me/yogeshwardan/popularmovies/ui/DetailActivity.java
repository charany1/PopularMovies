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
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import butterknife.Bind;
import butterknife.BindInt;
import butterknife.ButterKnife;
import me.yogeshwardan.popularmovies.R;
import me.yogeshwardan.popularmovies.database.DBHelper;
import me.yogeshwardan.popularmovies.model.Result;
import me.yogeshwardan.popularmovies.util.Constants;
import timber.log.Timber;

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
        Timber.d("onCreate Called");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("result");
        final Result mResult = Parcels.unwrap(bundle.getParcelable("result"));

        //initialize butterknife in this Activity
        ButterKnife.bind(this);


        //getting required data fields

        final String mTitle = mResult.getTitle();
        final String mReleaseDate = mResult.release_date;
        final String mRating = new Double(mResult.getVote_average()).toString();
        final String mSynopsis = mResult.getOverview();
        final String mPosterPath = mResult.getPoster_path();
        String mBackdropPath = mResult.getBackdrop_path();

        Picasso.with(this).load(Constants.BACKDROP_IMAGE_PATH_BASE_URL+ mBackdropPath).into(mBackgroundImageView);
        Picasso.with(this).load(Constants.IMAGE_PATH_BASE_URL + mPosterPath).into(mSmallImageView);

        mNameTextView.setText(mTitle);
        mReleaseDateTextView.setText(mReleaseDate);
        mRatingTextView.setText(mRating);
        mPopularityTextView.setText(new Double(mResult.getPopularity()).toString());


        if(mSynopsis!= null && !mSynopsis.trim().equals("")){
            mOverViewTextView.setText(mSynopsis);
        }
        else{
            mOverViewTextView.setText("No Overview recieved!");
        }


        //Initializing DBHelper

        final DBHelper dbHelper = new DBHelper(getApplicationContext(),DBHelper.DATABASE_NAME,null,DBHelper.DATABASE_VERSION);


        //Configuring listener on fab : save movie details in sqlite db
        mFavoriteFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //star_outline drawable is used to represent that movie is favorite .
                mFavoriteFab.setImageDrawable(getResources().getDrawable(R.drawable.star_outline));
                if(dbHelper.insertFavoriteMovie(mTitle,mRating,mSynopsis,mReleaseDate,mPosterPath)){
                    Toast.makeText(DetailActivity.this, "Movie favorited/inserted", Toast.LENGTH_SHORT).show();
                    Timber.d("Movie favorited/inserted");
                }else{
                    Toast.makeText(DetailActivity.this, "Movie insertion failed", Toast.LENGTH_SHORT).show();
                }
            }
        });




    }


}
