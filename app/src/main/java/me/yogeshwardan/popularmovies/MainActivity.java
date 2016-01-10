package me.yogeshwardan.popularmovies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView imageView = (ImageView)findViewById(R.id.poster_image);
        Log.d("MainActivity","Picasso");
        Picasso.with(this).load("http://i.imgur.com/DvpvklR.png").into(imageView);

        /*
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.themoviedb.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        TMDBService tmdbService = retrofit.create(TMDBService.class);
        Call<Movies> call = new tmdbService.discover();

        call.enqueue();
        */


    }


}
