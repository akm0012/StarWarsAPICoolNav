package com.mobiquity.amarshall.starwarscoolnav.objects;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.mobiquity.amarshall.starwarscoolnav.objects.NetworkCheck;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by amarshall on 7/17/15.
 */
public class StarWarsTask extends AsyncTask<String, Void, String[]> {

    private Context mContext;

    public interface StarWarsListener {
        public void set_name_list(String data);

    }

    public StarWarsTask(Context _context) {

        mContext = _context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected String[] doInBackground(String... num) {



        ArrayList<String> dataList = new ArrayList<>();

        String data = "";

        try {

            String next_page = "";
            int page_count = 2;

                GetNameTask getNameTask = new GetNameTask();
                getNameTask.execute("people/?page=" + page_count++);

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

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.i("tag", "Data: " + data);

        return (String[]) dataList.toArray();
    }

    @Override
    protected void onPostExecute(String[] s) {
        super.onPostExecute(s);

        if (mContext instanceof StarWarsListener) {
//            ((StarWarsListener) mContext).set_name_list(s);
        }
    }

    private class GetNameTask extends AsyncTask<String, Void, String[]> {

        @Override
        protected String[] doInBackground(String... num) {

            String baseURL = "http://swapi.co/api/";
            String query = "";


            try {

                String next_page = "";
                int page_count = 2;

                do {


                    GetNameTask getNameTask = new GetNameTask();
                    getNameTask.execute("people/?page=" + page_count++);


                } while (next_page.compareTo("null") != 0);


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

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return new String[0];
        }

        @Override
        protected void onPostExecute(String[] strings) {
            super.onPostExecute(strings);
        }
    }
}
