package me.yogeshwardan.popularmovies.database;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;
import me.yogeshwardan.popularmovies.listeners.FavoriteMovieInsertedListener;
import me.yogeshwardan.popularmovies.model.FavoriteMovie;

public class RealmHelper {

    public static void insertFavoriteMovie(final String title, final String userRating, final String synopsis, final String releaseDate, final String posterPath, final FavoriteMovieInsertedListener listener){

        try {
            Realm realm = Realm.getDefaultInstance();

            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    FavoriteMovie favoriteMovie = realm.createObject(FavoriteMovie.class);
                    favoriteMovie.setTitle(title);
                    favoriteMovie.setUserRating(userRating);
                    favoriteMovie.setSynopsis(synopsis);
                    favoriteMovie.setReleaseDate(releaseDate);
                    favoriteMovie.setPosterPath(posterPath);
                    listener.onSucces();
                }
            });
        }catch (Exception ex){
            listener.onFailure();
        }

    }

    public static RealmResults<FavoriteMovie> getAllFavoriteMovies(){
        Realm realm = Realm.getDefaultInstance();

        return realm.where(FavoriteMovie.class).findAll();
    }

}
