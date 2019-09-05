package com.rmalan.app.moviecataloguealpha.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.rmalan.app.moviecataloguealpha.model.FavoriteItems;

import java.util.ArrayList;

import static com.rmalan.app.moviecataloguealpha.db.DatabaseContract.FavoritesColumns.ID;
import static com.rmalan.app.moviecataloguealpha.db.DatabaseContract.FavoritesColumns.OVERVIEW;
import static com.rmalan.app.moviecataloguealpha.db.DatabaseContract.FavoritesColumns.POSTER;
import static com.rmalan.app.moviecataloguealpha.db.DatabaseContract.FavoritesColumns.RELEASE_DATE;
import static com.rmalan.app.moviecataloguealpha.db.DatabaseContract.FavoritesColumns.TITLE;
import static com.rmalan.app.moviecataloguealpha.db.DatabaseContract.TABLE_MOVIES;
import static com.rmalan.app.moviecataloguealpha.db.DatabaseContract.TABLE_TV_SHOWS;

public class FavoritesHelper {

    private static final String DATABASE_TABLE_TV_SHOWS = TABLE_TV_SHOWS;
    private static final String DATABASE_TABLE_MOVIES = TABLE_MOVIES;
    private static DatabaseHelper databaseHelper;
    private static FavoritesHelper INSTANCE;

    private static SQLiteDatabase database;

    private FavoritesHelper(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    public static FavoritesHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new FavoritesHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    public void open() throws SQLException {
        database = databaseHelper.getWritableDatabase();
    }

    public void close() {
        databaseHelper.close();
        if (database.isOpen())
            database.close();
    }

    public ArrayList<FavoriteItems> getAllMovies() {
        ArrayList<FavoriteItems> arrayList = new ArrayList<>();
        Cursor cursor = database.query(DATABASE_TABLE_MOVIES, null,
                null,
                null,
                null,
                null,
                ID + " ASC",
                null);
        cursor.moveToFirst();
        FavoriteItems favoriteItems;
        if (cursor.getCount() > 0) {
            do {
                favoriteItems = new FavoriteItems();
                favoriteItems.setId(cursor.getInt(cursor.getColumnIndexOrThrow(ID)));
                favoriteItems.setPoster(cursor.getString(cursor.getColumnIndexOrThrow(POSTER)));
                favoriteItems.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
                favoriteItems.setReleaseDate(cursor.getString(cursor.getColumnIndexOrThrow(RELEASE_DATE)));
                favoriteItems.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(OVERVIEW)));

                arrayList.add(favoriteItems);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }

        cursor.close();
        return arrayList;
    }

    public long insertMovie(FavoriteItems favoriteItems) {
        ContentValues args = new ContentValues();
        args.put(ID, favoriteItems.getId());
        args.put(POSTER, favoriteItems.getPoster());
        args.put(TITLE, favoriteItems.getTitle());
        args.put(RELEASE_DATE, favoriteItems.getReleaseDate());
        args.put(OVERVIEW, favoriteItems.getOverview());

        return database.insert(DATABASE_TABLE_MOVIES, null, args);
    }

    public int deleteMovie(int id) {
        return database.delete(TABLE_MOVIES, ID + " = '" + id + "'", null);
    }


    public ArrayList<FavoriteItems> getAllTvShows() {
        ArrayList<FavoriteItems> arrayList = new ArrayList<>();
        Cursor cursor = database.query(DATABASE_TABLE_TV_SHOWS, null,
                null,
                null,
                null,
                null,
                ID + " ASC",
                null);
        cursor.moveToFirst();
        FavoriteItems favoriteItems;
        if (cursor.getCount() > 0) {
            do {
                favoriteItems = new FavoriteItems();
                favoriteItems.setId(cursor.getInt(cursor.getColumnIndexOrThrow(ID)));
                favoriteItems.setPoster(cursor.getString(cursor.getColumnIndexOrThrow(POSTER)));
                favoriteItems.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
                favoriteItems.setReleaseDate(cursor.getString(cursor.getColumnIndexOrThrow(RELEASE_DATE)));
                favoriteItems.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(OVERVIEW)));

                arrayList.add(favoriteItems);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }

        cursor.close();
        return arrayList;
    }

    public long insertTvShow(FavoriteItems favoriteItems) {
        ContentValues args = new ContentValues();
        args.put(ID, favoriteItems.getId());
        args.put(POSTER, favoriteItems.getPoster());
        args.put(TITLE, favoriteItems.getTitle());
        args.put(RELEASE_DATE, favoriteItems.getReleaseDate());
        args.put(OVERVIEW, favoriteItems.getOverview());

        return database.insert(DATABASE_TABLE_TV_SHOWS, null, args);
    }

    public int deleteTvShow(int id) {
        return database.delete(TABLE_TV_SHOWS, ID + " = '" + id + "'", null);
    }

}