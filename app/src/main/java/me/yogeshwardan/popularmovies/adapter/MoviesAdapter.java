package me.yogeshwardan.popularmovies.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.List;

import me.yogeshwardan.popularmovies.R;
import me.yogeshwardan.popularmovies.model.Result;
import me.yogeshwardan.popularmovies.ui.DetailActivity;
import me.yogeshwardan.popularmovies.util.Constants;
import timber.log.Timber;

/**
 * Created by yogeshwardancharan on 8/5/16.
 */

/**
 * Adapter class to provide view to populate RecyclerView(res/layout/fragment_popular_movies)
 * with the movie results we get from API call .
 * */
public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {

    //to store position of movie item clicked,intialized in onBindViewHolder callback
    //made static because MovieViewHolder class is static
    //TODO : see if its right approach to follow



    private List<Result> mMovieResults = null;
    private Context mContext = null;

    /**
     * Class for holding the sub-view of itemView to avoid unnecessary expensive
     * findViewById call by caching references to sub-views .
     * */
    public static class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView mPosterImageView;
        TextView mTitleTextView;
        TextView mRatingTextView;
        ViewHolderItemClickListener mViewHolderItemClickListener;
        //int mPostion;

        /**
         * Constructor
         *
         * */
        public MovieViewHolder(View itemView,ViewHolderItemClickListener viewHolderItemClickListener) {
            super(itemView);

            mViewHolderItemClickListener = viewHolderItemClickListener;
            mPosterImageView = (ImageView)itemView.findViewById(R.id.poster_image);
            mTitleTextView = (TextView)itemView.findViewById(R.id.title_text_view);
            mRatingTextView = (TextView)itemView.findViewById(R.id.rating_text_view);

            //set listener
            itemView.setOnClickListener(this);


        }

        /**
         * Called when a view has been clicked.
         *
         * @param view The view that was clicked.
         */
        @Override
        public void onClick(View view) {
            mViewHolderItemClickListener.onItemClick(this.getLayoutPosition());
        }


        /**
         *Contains function to handle Click event on MovieViewHolder items
         *
         */
        public  interface ViewHolderItemClickListener{
            /**
             * Called when a movie item is clicked .
             * @param position : position of movie item in mMovieResults list,intialized in
             *                 onBindViewHolder
             * */
            public void onItemClick(int position);


        }
    }

    public MoviesAdapter(List<Result> movieResult, Context context){
        mContext = context;
        mMovieResults = movieResult;
    }


    /**
     * Called when RecyclerView needs a new {@link iewHolder} of the given type to represent
     * an item.
     * <p/>
     * This new ViewHolder should be constructed with a new View that can represent the items
     * of the given type. You can either create a new View manually or inflate it from an XML
     * layout file.
     * <p/>
     * The new ViewHolder will be used to display items of the adapter using
     * {@link #onBindViewHolder(ViewHolder, int, List)}. Since it will be re-used to display
     * different items in the data set, it is a good idea to cache references to sub views of
     * the View to avoid unnecessary {@link View#findViewById(int)} calls.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     * @see #getItemViewType(int)
     * @see #onBindViewHolder(ViewHolder, int)
     */
    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View movieView = LayoutInflater.from(mContext).inflate(R.layout.movie_item,parent,false);
        final MovieViewHolder movieViewHolder = new MovieViewHolder(movieView, new MovieViewHolder.ViewHolderItemClickListener() {
            @Override
            public void onItemClick(int position) {
                //TODO : getPosition in deprecated , try another approacho of storing position in
                // an int and updating it in onBindViewHolder
                // see this : http://stackoverflow.com/questions/24885223/why-doesnt-recyclerview-have-onitemclicklistener-and-how-recyclerview-is-dif

                Timber.d("Might crahs with NPE : movieViewHolder might not be initalized");

                Result movie = mMovieResults.get(position);

                if(movie == null){
                    Timber.d("Result == null");
                }
                else{
                    Timber.d("Got result,wrap() using Parcels.wrap() ");
                }

                Parcelable resultParcel =  Parcels.wrap(movie);
                Bundle bundle = new Bundle();
                bundle.putParcelable("result", resultParcel);



                Timber.d("onItemClick() starts!");

                Intent detailActivityLauncher = new Intent(mContext, DetailActivity.class);
                detailActivityLauncher.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


                detailActivityLauncher.putExtra("result", bundle);
                Timber.d("startAcitvity() to launch detailsActivityLauncher!");
                mContext.startActivity(detailActivityLauncher);
            }
        });

        return movieViewHolder;
    }

    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the {@link ViewHolder#itemView} to reflect the item at the given
     * position.
     * <p/>
     * Note that unlike {@link ListView}, RecyclerView will not call this method
     * again if the position of the item changes in the data set unless the item itself is
     * invalidated or the new position cannot be determined. For this reason, you should only
     * use the <code>position</code> parameter while acquiring the related data item inside
     * this method and should not keep a copy of it. If you need the position of an item later
     * on (e.g. in a click listener), use {@link ViewHolder#getAdapterPosition()} which will
     * have the updated adapter position.
     * <p/>
     * Override {@link #onBindViewHolder(ViewHolder, int, List)} instead if Adapter can
     * handle effcient partial bind.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        Result movieResult = mMovieResults.get(position);

        //setting poster image
        String posterPath = movieResult.poster_path;
        String imageURL = Constants.IMAGE_PATH_BASE_URL+posterPath;
        ImageView mPosterImageView = (ImageView)holder.mPosterImageView.findViewById(R.id.poster_image);
        Picasso.with(mContext).load(imageURL).into(mPosterImageView);

        //setting title text view
        holder.mTitleTextView.setText(movieResult.title);

        //setting rating text view
        holder.mRatingTextView.setText(new Float(movieResult.vote_average).toString());




    }

    /**
     * Returns the total number of items in the data set hold by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return mMovieResults.size();
    }
}
