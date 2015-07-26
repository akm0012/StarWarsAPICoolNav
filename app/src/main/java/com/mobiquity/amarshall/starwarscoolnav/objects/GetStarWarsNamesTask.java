package com.mobiquity.amarshall.starwarscoolnav.objects;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Andrew on 7/26/15.
 */
public class GetStarWarsNamesTask extends AsyncTask<String, Void, Void> {

    Context mContext;
    ArrayList<String> tempNames;
    String mNext;

    public interface GetNamesListener{
        public void add_names_to_list(ArrayList<String> _names, String _next);
    }


    public GetStarWarsNamesTask(Context _context) {
        mContext = _context;
    }

    @Override
    protected Void doInBackground(String... page_number) {

        String baseURL = "http://swapi.co/api/people/?page=";
        String query = "";

        String data;

        tempNames = new ArrayList<>();

        try {

            query = baseURL + page_number[0];

            Log.i("tag", "Query: " + query);

            URL url = new URL(query);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.connect();

            // Start getting data
            InputStream inputStream = conn.getInputStream();
            data = IOUtils.toString(inputStream);
            inputStream.close();

            conn.disconnect();

            JSONObject json = new JSONObject(data);

            JSONArray results = json.getJSONArray("results");

            mNext = json.getString("next");

            Log.i("tag", "Results length: " + results.length());

            for (int i = 0; i < results.length(); i++) {
                Log.d("tag", "Adding name: " + results.getJSONObject(i).getString("name"));
                tempNames.add(results.getJSONObject(i).getString("name"));
            }

        } catch (MalformedURLException e) {
            Log.e("tag", "Malformed URL");
            e.printStackTrace();
        } catch (IOException e) {
            Log.e("tag", "IO Exception");
            e.printStackTrace();
        } catch (JSONException e) {
            Log.e("tag", "JSON Exception");
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        if (mContext instanceof GetNamesListener) {
            ((GetNamesListener) mContext).add_names_to_list(tempNames, mNext);
        }
    }
}
