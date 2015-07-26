package com.mobiquity.amarshall.starwarscoolnav.objects;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.json.JSONObject;

/**
 * Created by Andrew on 7/25/15.
 */
public class EntryDataSource {

    private SQLiteDatabase mDB;
    private DatabaseHelper mDBHelper;
    private String[] mAllColumns = {
            DatabaseHelper.COLUMN_ID,
            DatabaseHelper.COLUMN_API_ID,
            DatabaseHelper.COLUMN_NAME,
            DatabaseHelper.COLUMN_HEIGHT,
            DatabaseHelper.COLUMN_MASS,
            DatabaseHelper.COLUMN_HAIR_COLOR,
            DatabaseHelper.COLUMN_SKIN_COLOR,
            DatabaseHelper.COLUMN_EYE_COLOR,
            DatabaseHelper.COLUMN_BIRTH_YEAR,
            DatabaseHelper.COLUMN_GENDER,
            DatabaseHelper.COLUMN_URL
    };

    public EntryDataSource(Context _context) {
        mDBHelper = new DatabaseHelper(_context);
    }

    public void open() {
        mDB = mDBHelper.getWritableDatabase();
    }

    public void close() {
        mDBHelper.close();
    }

    public Entry updateEntry(JSONObject _entry, String _api_id) {

        try {

            String name = _entry.getString("name");
            String height = _entry.getString("height");
            String mass = _entry.getString("mass");
            String hair_color = _entry.getString("hair_color");
            String skin_color = _entry.getString("skin_color");
            String eye_color = _entry.getString("eye_color");
            String birth_year = _entry.getString("birth_year");
            String gender = _entry.getString("gender");
            String url = _entry.getString("url");

            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.COLUMN_API_ID, _api_id);
            values.put(DatabaseHelper.COLUMN_NAME, name);
            values.put(DatabaseHelper.COLUMN_HEIGHT, height);
            values.put(DatabaseHelper.COLUMN_MASS, mass);
            values.put(DatabaseHelper.COLUMN_HAIR_COLOR, hair_color);
            values.put(DatabaseHelper.COLUMN_SKIN_COLOR, skin_color);
            values.put(DatabaseHelper.COLUMN_EYE_COLOR, eye_color);
            values.put(DatabaseHelper.COLUMN_BIRTH_YEAR, birth_year);
            values.put(DatabaseHelper.COLUMN_GENDER, gender);
            values.put(DatabaseHelper.COLUMN_URL, url);

            String[] whereArgs = new String[]{_api_id};
            Cursor cursor = mDB.query(DatabaseHelper.ENTRY_TABLE, new String[]{DatabaseHelper.COLUMN_ID}, DatabaseHelper.COLUMN_API_ID + " = " + '?', whereArgs, null, null, null);

            long entryID = 0;
            if (cursor.getCount() == 0 || cursor == null) {
                entryID = mDB.insert(DatabaseHelper.ENTRY_TABLE, null, values);
            } else {
                whereArgs = new String[]{"" + cursor.getInt(0)};
                entryID = cursor.getInt(0);
                mDB.update(DatabaseHelper.ENTRY_TABLE, values, DatabaseHelper.COLUMN_ID, whereArgs);
            }

            Entry entry = null;

            if (entryID > 0) {
                whereArgs = new String[]{"" + entryID};
                cursor = mDB.query(DatabaseHelper.ENTRY_TABLE, mAllColumns, DatabaseHelper.COLUMN_ID + " = " + '?', whereArgs, null, null, null);
                cursor.moveToFirst();
                entry = cursorToEntry(cursor);
            }

            return entry;

        } catch (Exception e) {
            Log.e("tag", "Error: " + e.getLocalizedMessage());
        }

        return null;

    }

    public Entry[] reallAllEntries() {
        Cursor cursor = mDB.query(DatabaseHelper.ENTRY_TABLE, mAllColumns, null, null, null, null, null);

        Entry[] entries = new Entry[cursor.getCount()];

        cursor.moveToFirst();

        int counter = 0;
        while (!cursor.isAfterLast()) {

            int api_id = 1;

            Entry entry = cursorToEntry(cursor);
            entries[counter] = entry;

            counter++;
            api_id++;
            if (api_id == 17) { // Fixes a bug. API ID 17 does not exsit
                api_id++;
            }

            cursor.moveToNext();
        }

        return entries;

    }

    public Entry readEntry(String _api_id) {
        String[] whereArgs = {_api_id};
        Cursor cursor = mDB.query(DatabaseHelper.ENTRY_TABLE, mAllColumns, DatabaseHelper.COLUMN_API_ID + " = " + '?', whereArgs, null, null, null);
        cursor.moveToFirst();
        Entry entry = cursorToEntry(cursor);
        return entry;
    }

    public int deleteEntry(String _api_id) {
        String[] whereArgs = {_api_id};
        int affected = mDB.delete(DatabaseHelper.ENTRY_TABLE, DatabaseHelper.COLUMN_API_ID + " = " + '?', whereArgs);
        return affected;
    }

    private Entry cursorToEntry(Cursor _cursor) {

        Entry entry = new Entry(
                _cursor.getString(1), //api_id
                _cursor.getString(2), //name
                _cursor.getString(3), //height
                _cursor.getString(4), //mass
                _cursor.getString(5), //hair color
                _cursor.getString(6), //skin color
                _cursor.getString(7), //eye color
                _cursor.getString(8), //birth year
                _cursor.getString(9), //gender
                _cursor.getString(10)  //URL
        );

        return entry;
    }


}
