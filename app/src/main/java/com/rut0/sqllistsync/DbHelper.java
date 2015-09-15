package com.rut0.sqllistsync;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Winston on 9/13/2015.
 */
public class DbHelper extends SQLiteOpenHelper {

    public static final String TABLE_NAME = "messages";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_MESSAGE = "message";
    public SQLiteDatabase database;

    public static final String DATABASE_NAME = "message.db";
    public static final int DATABASE_VERSION = 1;

    public static final String CREATE_TABLE = "CREATE TABLE" +
            TABLE_NAME + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_NAME + " TEXT NOT NULL, " +
            COLUMN_MESSAGE + " TEXT);";

    public DbHelper(Context ctx) {
        super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
        database = db;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public Cursor getMessages() {
        Cursor c = null;
        c = database.query(TABLE_NAME, new String[]{COLUMN_ID, COLUMN_NAME, COLUMN_MESSAGE}, null, null, null, null, null);
        return c;
    }

    public boolean addMessage(String name, String message) {
        ContentValues vals = new ContentValues();
        vals.put(COLUMN_NAME, name);
        vals.put(COLUMN_MESSAGE, message);
        return database.insert(TABLE_NAME, null, vals) != -1;
    }
}
