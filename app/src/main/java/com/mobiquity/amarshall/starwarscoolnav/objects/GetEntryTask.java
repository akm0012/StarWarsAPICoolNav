package com.mobiquity.amarshall.starwarscoolnav.objects;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Andrew on 7/26/15.
 */
public class GetEntryTask extends AsyncTask<String, Void, String> {

    private Activity mContainingActivity;

    public interface DetailEntryListener {
        public void update_detail(String _entry_api_id);
    }

    public GetEntryTask(Activity _context) {
        if (_context instanceof DetailEntryListener) {
            mContainingActivity = _context;
        } else {
            throw new IllegalArgumentException("Activity must implement DetailEntryListener");
        }
    }

    @Override
    protected String doInBackground(String... num) {

        String baseURL = "http://swapi.co/api/people/";
        String query = "";
        String data = "";

        EntryDataSource entryDataSource = new EntryDataSource(mContainingActivity);
        entryDataSource.open();

        try {

            // Else we go out a fetch from API
            query = baseURL + num[0];

            Log.i("tag", "Query: " + query);

            URL url = new URL(query);

            Log.i("tag", "URL: " + url.toString());

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.connect();

            // Start getting data
            InputStream inputStream = conn.getInputStream();
            data = IOUtils.toString(inputStream);
            inputStream.close();

            conn.disconnect();

            // Create the Entry from the JSON data
            JSONObject json = new JSONObject(data);

            entryDataSource.updateEntry(json, num[0]);

            entryDataSource.close();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            Log.e("tag", "" + e.getLocalizedMessage());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.i("tag", "Data: " + data);

        return num[0];
    }

    @Override
    protected void onPostExecute(String _api_id) {
        super.onPostExecute(_api_id);

        ((DetailEntryListener) mContainingActivity).update_detail(_api_id);

    }
}
