package com.android_camp.doseit.database;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import com.android_camp.doseit.Medicine;
import java.util.*;

/**
 * Created by demouser on 8/4/16.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Medicines";
    public static final int keys = 4;
    public static final ArrayList<String> places = new ArrayList<String>(
            Arrays.asList("id","Name", "Concentration", "Warning"));


    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_ENTRIES = "CREATE TABLE " + DATABASE_NAME + " (";
        for(int i = 0; i < keys; i++){
            SQL_CREATE_ENTRIES
        }+
                FeedEntry._ID + " INTEGER PRIMARY KEY," +
                FeedEntry.COLUMN_NAME_ENTRY_ID + TEXT_TYPE + COMMA_SEP +
                FeedEntry.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP +
        ... // Any other options for the CREATE command
        " )";
        //db.execSQL(SQL_CREATE_ENTRIES);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        //db.execSQL(SQL_DELETE_ENTRIES);
        //onCreate(db);
    }
    public List<String> getMedicineList(){

        return null;
    }

    public Medicine getMedicine(String name){
        return null;
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}