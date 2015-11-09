package com.example.android.projecti_popularmovies;

import android.os.AsyncTask;
import android.util.Log;

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
 * Created by Joao on 08/11/15.
 */
//public class FetchMoviesTask extends AsyncTask<String, String, Void> {

//    private final String LOG_TAG = FetchMoviesTask.class.getSimpleName();
//
//    private Void getMovieDetailsFromJson(String movieJSONstr) throws JSONException {
//
//        final String OWM_RESULTS = "results";
//        final String OWM_ID = "id";
//        final String OWM_POSTERPATH = "poster_path";
//        final String OWM_TITLE = "original_title";
//        final String OWM_OVERVIEW = "overview";
//        final String OWM_USERRATING = "vote_average";
//        final String OWM_RELEASEDATE = "release_date";
//
//        JSONObject movieJson = new JSONObject(movieJSONstr);
//        JSONArray movieArray = movieJson.getJSONArray(OWM_RESULTS);
//
//        for(int i = 0; i < movieArray.length(); i++) {
//            JSONObject movieObj = movieArray.getJSONObject(i);
//            matrixMoviesInfo[i][0] = movieObj.getString(OWM_POSTERPATH);
//            matrixMoviesInfo[i][1] = movieObj.getString(OWM_ID);
//            matrixMoviesInfo[i][2] = movieObj.getString(OWM_TITLE);
//            matrixMoviesInfo[i][3] = movieObj.getString(OWM_OVERVIEW);
//            matrixMoviesInfo[i][4] = movieObj.getString(OWM_USERRATING);
//            matrixMoviesInfo[i][5] = movieObj.getString(OWM_RELEASEDATE);
//        }
//        for(int i=0;i<20;i++) {
//            new FetchMoviesTask().execute(baseUrl + matrixMoviesInfo[i][1] + "/reviews" + apiKey, Integer.toString(i));
//            new FetchMoviesTask().execute(baseUrl + matrixMoviesInfo[i][1] + "/videos" + apiKey, "v", Integer.toString(i));
//        }
//        Log.v(null, "GETMOVIEDETAILS");
//        return null;
//    }
//
//    private Void getMovieReviewsFromJson(String movieJSONstr, String position) throws JSONException{
//
//        int pos = Integer.parseInt(position);
//        final String OWM_RESULTS = "results";
//        final String OWM_AUTHOR = "author";
//        final String OWM_CONTENT = "content";
//        final String OWM_TOTALRESULTS = "total_results";
//
//        JSONObject movieJson = new JSONObject(movieJSONstr);
//        JSONArray movieArray = movieJson.getJSONArray(OWM_RESULTS);
//        int numberOfResults = Integer.parseInt(movieJson.getString(OWM_TOTALRESULTS));
//
//        if(numberOfResults >= 1) {
//            matrixMoviesInfo[pos][6] = movieArray.getJSONObject(0).getString(OWM_AUTHOR);
//            matrixMoviesInfo[pos][7] = movieArray.getJSONObject(0).getString(OWM_CONTENT);
//        }
//        if(numberOfResults > 1) {
//            matrixMoviesInfo[pos][8] = movieArray.getJSONObject(1).getString(OWM_AUTHOR);
//            matrixMoviesInfo[pos][9] = movieArray.getJSONObject(1).getString(OWM_CONTENT);
//        }
//
//        return null;
//    }
//
//    private Void getMovieVideosFromJson(String movieJSONstr, String position) throws JSONException{
//
//        int pos = Integer.parseInt(position);
//        final String OWM_RESULTS = "results";
//        final String OWM_KEY = "key";
//        final String OWM_TYPE = "type";
//
//        JSONObject movieJson = new JSONObject(movieJSONstr);
//        JSONArray movieArray = movieJson.getJSONArray(OWM_RESULTS);
//
//        for(int i = 0; i < movieArray.length(); i++) {
//            JSONObject movieObj = movieArray.getJSONObject(i);
//            if(movieObj.getString(OWM_TYPE).equals("Trailer")){
//                Log.v(null, "ITS IN!"+movieObj.getString(OWM_KEY));
//                matrixMoviesInfo[pos][10] = movieObj.getString(OWM_KEY);
//                break;
//            }
//        }
//
//        return null;
//    }
//
//    @Override
//    protected Void doInBackground(String... params) {
//
//        HttpURLConnection urlConnection = null;
//        BufferedReader reader = null;
//
//        String movieJSONstr = null;
//
//        try{
//            URL url = new URL(params[0]);
//            urlConnection = (HttpURLConnection) url.openConnection();
//            urlConnection.setRequestMethod("GET");
//            urlConnection.connect();
//
//            InputStream inputStream = urlConnection.getInputStream();
//            StringBuffer buffer = new StringBuffer();
//            if(inputStream == null){
//                return null;
//            }
//            reader = new BufferedReader(new InputStreamReader(inputStream));
//
//            String line;
//            while((line = reader.readLine()) != null){
//                buffer.append(line + "\n");
//            }
//
//            if(buffer.length() == 0){
//                movieJSONstr = null;
//            }
//            movieJSONstr = buffer.toString();
//        } catch (IOException e) {
//            Log.e(LOG_TAG, "Error ", e);
//
//            return null;
//        } finally{
//            if (urlConnection != null) {
//                urlConnection.disconnect();
//            }
//            if (reader != null) {
//                try {
//                    reader.close();
//                } catch (final IOException e) {
//                    Log.e("MovieFragment", "Error closing stream", e);
//                }
//            }
//        }
//
//        try{
//            if(params[1].equals("i")){
//                Log.v(null,"EXECUTEGETMOVIEDETAILS");
//                return getMovieDetailsFromJson(movieJSONstr);
//            }
//            else {
//                if (params[1].equals("v")){
//                    Log.v("VIDEO ID", ""+params[2]);
//                    return getMovieVideosFromJson(movieJSONstr, params[2]);}
//                else{
//                    return getMovieReviewsFromJson(movieJSONstr, params[1]);}
//            }
//        }catch (JSONException e) {
//            Log.e(LOG_TAG, e.getMessage(), e);
//            e.printStackTrace();
//        }
//
//        return null;
//    }
//
//    @Override
//    protected void onPostExecute(Void aVoid) {
//        String[] mArrayImages = new String[20];
//        for(int i=0;i<20;i++){
//            mArrayImages[i] = matrixMoviesInfo[i][0];
//        }
//        gridView.setAdapter(new ImageAdapter(getActivity(), mArrayImages));
//        Log.v(null, "IMAGES SET GRIDVIEW REFRESHED");
//    }
//}