package me.yogeshwardan.popularmovies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    private  final String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CardView cardView = (CardView)findViewById(R.id.card_view);


        //Load image using Picasso
        ImageView imageView = (ImageView)cardView.findViewById(R.id.poster_image);
        Log.d("MainActivity","Picasso");
        //Picasso.with(this).load("http://i.imgur.com/DvpvklR.png").into(imageView);



        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.themoviedb.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        TMDBService tmdbService = retrofit.create(TMDBService.class);
        Call<Movies> call =  tmdbService.discover();

        call.enqueue(new Callback<Movies>() {
            @Override
            public void onResponse(Response<Movies> response) {
                Movies movies = response.body();
                ArrayList<Result> results = (ArrayList)movies.results;
                Toast.makeText(MainActivity.this, "Result size:"+(new Integer(results.size()).toString()), Toast.LENGTH_SHORT).show();
                Log.d(TAG, results.get(0).poster_path);
                String posterPath = results.get(0).poster_path;
                if(posterPath!=null)
                    Log.d(TAG,posterPath);
                else
                    Log.d(TAG,"NULL posterPath");

                String imageURL = "http://image.tmdb.org/t/p/w185/"+posterPath;
                Log.d(TAG,imageURL);
                ImageView imageView = (ImageView)findViewById(R.id.poster_image);
                Log.d("MainActivity", "Picasso");
                Picasso.with(MainActivity.this).load(imageURL).into(imageView);


            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(MainActivity.this, "API call failed!!", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });


    }


}
