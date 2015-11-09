package com.example.android.projecti_popularmovies;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Map;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailFragment extends Fragment {

    ImageButton ibFavorite;
    ImageButton ibTrailer;
    boolean favoriteState;
    ArrayList favoriteMoviesList;
    SharedPreferences preferences;
    String[] movieInfo;

    public DetailFragment() {
        favoriteState = false;
        favoriteMoviesList = new ArrayList();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = getContext().getSharedPreferences("favorites_list", Context.MODE_PRIVATE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Intent intent = getActivity().getIntent();
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        ibFavorite = (ImageButton) rootView.findViewById(R.id.favorite);
        ibTrailer = (ImageButton) rootView.findViewById(R.id.trailer);
        if(intent != null && intent.hasExtra(Intent.EXTRA_TEXT)){
            movieInfo = intent.getStringArrayExtra(Intent.EXTRA_TEXT);
            ibTrailer.setBackgroundResource(R.drawable.video_player);
            ibFavorite.setBackgroundResource(R.drawable.star);
            ImageView imageView = (ImageView) rootView.findViewById(R.id.poster);
            Picasso.with(getActivity()).load("http://image.tmdb.org/t/p/w500"+movieInfo[0]).into(imageView);
            ((TextView) rootView.findViewById(R.id.title)).setText(movieInfo[2]);
            ((TextView) rootView.findViewById(R.id.plot)).setText(movieInfo[3]);
            ((TextView) rootView.findViewById(R.id.userRating)).setText(movieInfo[4] + "/10");
            ((TextView) rootView.findViewById(R.id.releaseDate)).setText(movieInfo[5]);

            if(movieInfo[6] != null && !movieInfo[6].equals("null")) {
                ((TextView) rootView.findViewById(R.id.author1)).setText(movieInfo[6] + ":");
            }

            if(movieInfo[7] != null && !movieInfo[7].equals("null")) {
                ((TextView) rootView.findViewById(R.id.review1)).setText(movieInfo[7]);
            }

            if(movieInfo[8] != null && !movieInfo[8].equals("null")) {
                ((TextView) rootView.findViewById(R.id.author2)).setText(movieInfo[8] + ":");
            }

            if(movieInfo[9] != null && !movieInfo[9].equals("null")) {
                ((TextView) rootView.findViewById(R.id.review2)).setText(movieInfo[9]);
            }
            SharedPreferences preferences = getContext().getSharedPreferences("favorites_list", Context.MODE_PRIVATE);
            Map<String, String> map = (Map<String, String>) preferences.getAll();
            for (Map.Entry<String, String> entry : map.entrySet()){
                if(entry.getKey().equals(movieInfo[1])){
                    ibFavorite.setBackgroundResource(R.drawable.starpressed);
                    favoriteState = true;
                }
            }
        }
        ibFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!favoriteState) {
                    ibFavorite.setBackgroundResource(R.drawable.starpressed);
                    favoriteState = true;
                    SharedPreferences.Editor preferencesEditor = preferences.edit();
                    String movieInfoString = "";
                    for (String s : movieInfo) {
                        movieInfoString += s + "#";
                    }
                    preferencesEditor.putString(movieInfo[1], movieInfoString);
                    preferencesEditor.apply();
                } else {
                    ibFavorite.setBackgroundResource(R.drawable.star);
                    favoriteState = false;
                    SharedPreferences.Editor preferencesEditor = preferences.edit();
                    preferencesEditor.remove(movieInfo[1]);
                    preferencesEditor.apply();
                }
            }
        });

        ibTrailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v="+movieInfo[10])));
            }
        });

        return rootView;
    }

}
