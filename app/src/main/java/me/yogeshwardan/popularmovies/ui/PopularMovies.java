package me.yogeshwardan.popularmovies.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Switch;

import me.yogeshwardan.popularmovies.R;

public class PopularMovies extends AppCompatActivity {

    SortByPopularityFragment sortByPopularityFragment;
    SortByRatingFragment sortByRatingFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popular_movies);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sortByPopularityFragment= SortByPopularityFragment.newInstance();
        sortByRatingFragment = SortByRatingFragment.newInstance();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,sortByPopularityFragment).commit();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_popular_movies,menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        int selectedMenuItemId = item.getItemId();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        switch(selectedMenuItemId){
            case R.id.popularity :
                //code for dynamically adding Popularity Fragment



                transaction.replace(R.id.fragment_container,sortByPopularityFragment);
                transaction.addToBackStack(null);
                transaction.commit();
                break;
            case R.id.rating :
                //code for dynamically adding Rating Fragment



                transaction.replace(R.id.fragment_container,sortByRatingFragment);
                transaction.addToBackStack(null);
                transaction.commit();
                break;
            default:
                break;

        }


        return super.onOptionsItemSelected(item);

    }
}
