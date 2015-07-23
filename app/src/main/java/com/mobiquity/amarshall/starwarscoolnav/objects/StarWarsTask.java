package com.mobiquity.amarshall.starwarscoolnav.objects;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.mobiquity.amarshall.starwarscoolnav.DisplayNameListener;
import com.mobiquity.amarshall.starwarscoolnav.objects.NetworkCheck;

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
 * Created by amarshall on 7/17/15.
 */
public class StarWarsTask extends AsyncTask<Void, Void, String[]> {

    private Context mContext;
    private String mNext = "";
    private boolean mDoneLoadingPage;
    private ArrayList<String> mNameList;
    private int page_counter = 1;

    public interface StarWarsListener {
        public void set_name_list(String data);

    }

    public StarWarsTask(Context _context) {

        mContext = _context;
        mNameList = new ArrayList<>();
        mDoneLoadingPage = true;
    }

    @Override
    protected String[] doInBackground(Void... num) {
        Log.i("tag", "Starting Async Task A.");

        String[] test = {"hi"};

        return test;
    }

    @Override
    protected void onPostExecute(String[] s) {
        super.onPostExecute(s);

        while (true) {

            while (!mDoneLoadingPage) {
                // Wait
            }

            mDoneLoadingPage = false;

            if (mNext.equals("null")) {
                //TODO: send the Array back via an interface
                if (mContext instanceof DisplayNameListener) {
                    String[] nameArr = new String[mNameList.size()];
                    nameArr = mNameList.toArray(nameArr);
                    ((DisplayNameListener) mContext).set_names(nameArr);
                }
                return;
            } else {
                // Spin up a new thread and get the next page
                GetNameTask getNameTask = new GetNameTask();
                getNameTask.execute("" + page_counter);

                page_counter++;

            }
        }


    }

    private class GetNameTask extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... num) {

            Log.i("tag", "In AsyncB");

            String baseURL = "http://swapi.co/api/people/?page=";
            String query = "";

            String data;

            try {

                query = baseURL + num[0];

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
                    mNameList.add(results.getJSONObject(i).getString("name"));
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

            mDoneLoadingPage = true;

            return null;
        }

    }
}



























