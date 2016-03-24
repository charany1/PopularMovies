package me.yogeshwardan.popularmovies.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.yogeshwardan.popularmovies.R;
import me.yogeshwardan.popularmovies.model.Result;
import me.yogeshwardan.popularmovies.util.Constants;


/**
 * Created by yogeshwardancharan on 16/3/16.
 */
public class MovieAdapter extends ArrayAdapter<Result> {



    public MovieAdapter(Context context , List<Result> results){
        super(context,0,results);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Result result = getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.movie_item,parent,false);

        }
        String posterPath = result.poster_path;
        String imageURL = Constants.IMAGE_PATH_BASE_URL+posterPath;
        ImageView mPosterImageView = (ImageView)convertView.findViewById(R.id.poster_image);
        Picasso.with(getContext()).load(imageURL).into(mPosterImageView);
        //mPosterImageView.setImageResource(R.drawable.place_holder_poster);

        TextView titleTextView = (TextView)convertView.findViewById(R.id.title_text_view);
        titleTextView.setText(result.title);

        TextView ratingTextView = (TextView)convertView.findViewById(R.id.rating_text_view);
        ratingTextView.setText(new Float(result.vote_average).toString());


        return convertView;
    }
}
