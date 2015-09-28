package com.example.android.projecti_popularmovies;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailActivityFragment extends Fragment {

    public DetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Intent intent = getActivity().getIntent();
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        if(intent != null && intent.hasExtra(Intent.EXTRA_TEXT)){
            String[] movieInfo = intent.getStringArrayExtra(Intent.EXTRA_TEXT);
            ImageView imageView = (ImageView) rootView.findViewById(R.id.poster);
            Picasso.with(getActivity()).load("http://image.tmdb.org/t/p/w500"+movieInfo[0]).into(imageView);
            ((TextView) rootView.findViewById(R.id.title)).setText(movieInfo[1]);
            ((TextView) rootView.findViewById(R.id.plot)).setText(movieInfo[2]);
            ((TextView) rootView.findViewById(R.id.userRating)).setText(movieInfo[3]);
            ((TextView) rootView.findViewById(R.id.releaseDate)).setText(movieInfo[4]);
        }
        return rootView;
    }
}
