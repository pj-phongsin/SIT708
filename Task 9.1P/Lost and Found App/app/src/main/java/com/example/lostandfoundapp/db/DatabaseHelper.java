package com.example.lostandfoundapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.lostandfoundapp.model.Item;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "lost_found.db";
    private static final int DATABASE_VERSION = 2;

    private static final String TABLE_NAME = "Items";
    private static final String COL_ID = "id";
    private static final String COL_TYPE = "type";
    private static final String COL_NAME = "name";
    private static final String COL_PHONE = "phone";
    private static final String COL_DESC = "description";
    private static final String COL_DATE = "date";
    private static final String COL_LOCATION = "location";
    private static final String COL_LAT = "latitude";
    private static final String COL_LNG = "longitude";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE Items (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "type TEXT," +
                "name TEXT," +
                "phone TEXT," +
                "description TEXT," +
                "date TEXT," +
                "location TEXT," +
                "latitude REAL," +
                "longitude REAL" +
                ")";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void insertItem(Item item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_TYPE, item.getType());
        values.put(COL_NAME, item.getName());
        values.put(COL_PHONE, item.getPhone());
        values.put(COL_DESC, item.getDescription());
        values.put(COL_DATE, item.getDate());
        values.put(COL_LOCATION, item.getLocation());
        values.put(COL_LAT, item.getLatitude());
        values.put(COL_LNG, item.getLongitude());
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public ArrayList<Item> getAllItems() {
        ArrayList<Item> items = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        if (cursor.moveToFirst()) {
            do {
                Item item = new Item(
                        cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_TYPE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_NAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_PHONE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_DESC)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_DATE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_LOCATION))
                );

                item.setLatitude(cursor.getDouble(cursor.getColumnIndexOrThrow(COL_LAT)));
                item.setLongitude(cursor.getDouble(cursor.getColumnIndexOrThrow(COL_LNG)));

                items.add(item);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return items;
    }

    public void deleteItem(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COL_ID + "=?", new String[] {String.valueOf(id)});
        db.close();
    }
}
