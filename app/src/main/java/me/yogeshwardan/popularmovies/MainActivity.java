package me.yogeshwardan.popularmovies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        //Load image using Picasso
        ImageView imageView = (ImageView)findViewById(R.id.poster_image);
        Log.d("MainActivity","Picasso");
        Picasso.with(this).load("http://i.imgur.com/DvpvklR.png").into(imageView);
*/

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
                Integer id = (Integer)movies.results.get(0).id;
                Toast.makeText(MainActivity.this, "API call successful:Size of Results:"+movies.results.size()+"Id[0]"+id.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(MainActivity.this, "API call failed!!", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });


    }


}
