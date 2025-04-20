package com.example.itubeapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class iTubeDataBaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "iTube.db";
    private static final int DATABASE_VERSION = 1;

    // User table
    public static final String TABLE_USERS = "user";
    public static final String COLUMN_USER_ID = "id";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_PASSWORD = "password";

    // Playlist table
    public static final String TABLE_PLAYLIST = "playlist";
    public static final String COLUMN_PLAYLIST_ID = "id";
    public static final String COLUMN_REF_ID = "user_id";
    public static final String COLUMN_VIDEO_URL = "video_url";

    public iTubeDataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create user table
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "(" +
                COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_USERNAME + " TEXT UNIQUE," +
                COLUMN_PASSWORD + " TEXT)";

        // Create playlist table
        // Update here too
        String CREATE_PLAYLIST_TABLE = "CREATE TABLE " + TABLE_PLAYLIST + "(" +
                COLUMN_PLAYLIST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_REF_ID + " INTEGER," +
                COLUMN_VIDEO_URL + " TEXT," +
                "FOREIGN KEY(" + COLUMN_REF_ID + ") REFERENCES " + TABLE_USERS + "(" + COLUMN_USER_ID + "))";

        db.execSQL(CREATE_USERS_TABLE);
        db.execSQL(CREATE_PLAYLIST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLAYLIST);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    public boolean addUser(UserData user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, user.getUsername());
        values.put(COLUMN_PASSWORD, user.getPassword());

        long result = db.insert(TABLE_USERS, null, values);
        db.close();

        return result != -1;
    }

    public boolean addToPlaylist(int userId, String videoUrl) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_REF_ID, userId);
        values.put(COLUMN_VIDEO_URL, videoUrl);

        long result = db.insert(TABLE_PLAYLIST, null, values);
        db.close();

        return result != -1;
    }

    public List<String> getUserPlaylist(int userId) {
        List<String> playlist = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                TABLE_PLAYLIST,
                new String[]{COLUMN_VIDEO_URL},
                COLUMN_REF_ID + "=?",
                new String[]{String.valueOf(userId)},
                null, null, null
        );

        if (cursor.moveToFirst()) {
            do {
                playlist.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return playlist;
    }
}

