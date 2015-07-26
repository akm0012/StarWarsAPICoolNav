package com.mobiquity.amarshall.starwarscoolnav.objects;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by amarshall on 7/24/15.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_FILE = "stock.db";
    public static final int DATABASE_VERSION = 1;

    // SQL Names

    public static final String ENTRY_TABLE = "people";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_HEIGHT = "height";
    public static final String COLUMN_MASS = "mass";
    public static final String COLUMN_HAIR_COLOR = "hair_color";
    public static final String COLUMN_SKIN_COLOR = "skin_color";
    public static final String COLUMN_EYE_COLOR = "eye_color";
    public static final String COLUMN_BIRTH_YEAR = "birth_year";
    public static final String COLUMN_GENDER = "gender";
    public static final String COLUMN_URL = "url";

    // SQL Commands

    private static final String CREATE_TABLE_ENTRYS = "CREATE TABLE IF NOT EXISTS " + ENTRY_TABLE + "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + ", " +
            COLUMN_NAME + " TEXT NOT NULL" + ", " +
            COLUMN_HEIGHT + " TEXT NOT NULL" + ", " +
            COLUMN_MASS + " TEXT NOT NULL" + ", " +
            COLUMN_HAIR_COLOR + " TEXT NOT NULL" + ", " +
            COLUMN_SKIN_COLOR + " TEXT NOT NULL" + ", " +
            COLUMN_EYE_COLOR + " TEXT NOT NULL" + ", " +
            COLUMN_BIRTH_YEAR + " TEXT NOT NULL" + ", " +
            COLUMN_GENDER + " TEXT NOT NULL" + ", " +
            COLUMN_URL + " TEXT NOT NULL";

    public DatabaseHelper(Context _context) {
        super(_context, DATABASE_FILE, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
