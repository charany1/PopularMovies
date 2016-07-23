package me.yogeshwardan.popularmovies.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by yogeshwardancharan on 16/7/16.
 */
public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "FavoriteMovies.db";
    public static final int DATABASE_VERSION = 1;
    public static final String FAVORITE_MOVIES_TABLE_NAME = "favorite_movies";
    public static final String FAVORITE_MOVIES_COLUMN_ID = "_id";
    public static final String FAVORITE_MOVIES_COLUMN_TITLE = "title";
    public static final String FAVORITE_MOVIE_COLUMN_POSTER_PATH = "poster_path";
    public static final String FAVORITE_MOVIE_COLUMN_SYNOPSIS = "synopsis";
    public static final String FAVORITE_MOVIE_COLUMN_USER_RATING = "user_rating";
    public static final String FAVORITE_MOVIE_COLUMN_RELEASE_DATE = "release_date";




    /**
     * Create a helper object to create, open, and/or manage a database.
     * This method always returns very quickly.  The database is not actually
     * created or opened until one of {@link #getWritableDatabase} or
     * {@link #getReadableDatabase} is called.
     *
     * @param context to use to open or create the database
     * @param name    of the database file, or null for an in-memory database
     * @param factory to use for creating cursor objects, or null for the default
     * @param version number of the database (starting at 1); if the database is older,
     *                {@link #onUpgrade} will be used to upgrade the database; if the database is
     *                newer, {@link #onDowngrade} will be used to downgrade the database
     */
    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    /**
     * Called when the database is created for the first time. This is where the
     * creation of tables and the initial population of the tables should happen.
     *
     * @param db The database.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + FAVORITE_MOVIES_TABLE_NAME + "(" +
                FAVORITE_MOVIES_COLUMN_ID + " INTEGER PRIMARY KEY, " +
                FAVORITE_MOVIES_COLUMN_TITLE + " TEXT, " +
                FAVORITE_MOVIE_COLUMN_USER_RATING + " TEXT, " +
                FAVORITE_MOVIE_COLUMN_SYNOPSIS + " TEXT,"+
                FAVORITE_MOVIE_COLUMN_RELEASE_DATE+ "TEXT," +
                FAVORITE_MOVIE_COLUMN_POSTER_PATH+ "TEXT)"
        );

    }

    /**
     * Called when the database needs to be upgraded. The implementation
     * should use this method to drop tables, add tables, or do anything else it
     * needs to upgrade to the new schema version.
     * <p/>
     * <p>
     * The SQLite ALTER TABLE documentation can be found
     * <a href="http://sqlite.org/lang_altertable.html">here</a>. If you add new columns
     * you can use ALTER TABLE to insert them into a live table. If you rename or remove columns
     * you can use ALTER TABLE to rename the old table, then create the new table and then
     * populate the new table with the contents of the old table.
     * </p><p>
     * This method executes within a transaction.  If an exception is thrown, all changes
     * will automatically be rolled back.
     * </p>
     *
     * @param db         The database.
     * @param oldVersion The old database version.
     * @param newVersion The new database version.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + FAVORITE_MOVIES_TABLE_NAME);
        onCreate(db);

    }

    /**
     * Insert a movie in db.
     * */
    public boolean insertFavoriteMovie(String title, String userRating,String synopsis,String releaseDate,String posterPath) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(FAVORITE_MOVIES_COLUMN_TITLE, title);
        contentValues.put(FAVORITE_MOVIE_COLUMN_POSTER_PATH, posterPath);
        contentValues.put(FAVORITE_MOVIE_COLUMN_RELEASE_DATE, releaseDate);
        contentValues.put(FAVORITE_MOVIE_COLUMN_SYNOPSIS, synopsis);
        contentValues.put(FAVORITE_MOVIE_COLUMN_USER_RATING,userRating);
        db.insert(FAVORITE_MOVIES_TABLE_NAME, null, contentValues);
        return true;
    }

    /**
     * Returns Cursor to get all favorited movies.
     * */

    public Cursor getAllFavoriteMovies(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+FAVORITE_MOVIES_TABLE_NAME,null);
        return cursor;
    }
}
