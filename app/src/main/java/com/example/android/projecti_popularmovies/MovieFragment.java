package com.example.android.projecti_popularmovies;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Joao on 19/09/15.
 */
public class MovieFragment extends Fragment{

    public String[][] matrixMoviesInfo;
    public GridView gridView;
    private String baseUrl;
    private String apiKey;

    public MovieFragment(){
        matrixMoviesInfo = new String[20][5];
        baseUrl = "http://api.themoviedb.org/3/movie/";
        apiKey = "?api_key=37068e0a72b2cc1751b4246899923ba7";
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        gridView = (GridView) rootView.findViewById(R.id.gridview);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                Intent intent = new Intent(getActivity(), DetailActivity.class)
                        .putExtra(Intent.EXTRA_TEXT, matrixMoviesInfo[position]);
                startActivity(intent);
            }
        });
        return rootView;
    }

    private void updateMovies() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String preference = prefs.getString(getString(R.string.pref_sortby_key), getString(R.string.pref_sortby_default));
        new FetchMoviesTask().execute(baseUrl+preference.toLowerCase()+apiKey);
    }

    @Override
    public void onStart() {
        super.onStart();
        updateMovies();
    }

    public class FetchMoviesTask extends AsyncTask<String, Void, Void> {

        private final String LOG_TAG = FetchMoviesTask.class.getSimpleName();

        private Void getMovieImagesFromJson(String movieJSONstr) throws JSONException {

            final String OWM_RESULTS = "results";
            final String OWM_POSTERPATH = "poster_path";
            final String OWM_TITLE = "original_title";
            final String OWM_OVERVIEW = "overview";
            final String OWM_USERRATING = "vote_average";
            final String OWM_RELEASEDATE = "release_date";

            JSONObject movieJson = new JSONObject(movieJSONstr);
            JSONArray movieArray = movieJson.getJSONArray(OWM_RESULTS);

            for(int i = 0; i < movieArray.length(); i++) {
                JSONObject movieObj = movieArray.getJSONObject(i);
                matrixMoviesInfo[i][0] = movieObj.getString(OWM_POSTERPATH);
                matrixMoviesInfo[i][1] = movieObj.getString(OWM_TITLE);
                matrixMoviesInfo[i][2] = movieObj.getString(OWM_OVERVIEW);
                matrixMoviesInfo[i][3] = movieObj.getString(OWM_USERRATING);
                matrixMoviesInfo[i][4] = movieObj.getString(OWM_RELEASEDATE);
            }

            return null;
        }

        @Override
        protected Void doInBackground(String... params) {

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            String movieJSONstr = null;

            try{
                URL url = new URL(params[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if(inputStream == null){
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while((line = reader.readLine()) != null){
                    buffer.append(line + "\n");
                }

                if(buffer.length() == 0){
                    movieJSONstr = null;
                }
                movieJSONstr = buffer.toString();
            } catch (IOException e) {
                Log.e(LOG_TAG, "Error ", e);

                return null;
            } finally{
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("MovieFragment", "Error closing stream", e);
                    }
                }
            }

            try{
                return getMovieImagesFromJson(movieJSONstr);
            }catch (JSONException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(Void aVoid) {
            String[] mArrayImages = new String[20];
            for(int i=0;i<20;i++){
                mArrayImages[i] = matrixMoviesInfo[i][0];
            }
            gridView.setAdapter(new ImageAdapter(getActivity(), mArrayImages));
        }
    }
}
