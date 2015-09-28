package com.example.android.projecti_popularmovies;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by Joao on 19/09/15.
 */
public class ImageAdapter extends BaseAdapter {

    private Context mContext;
    private String[] mArrayImages;
    private String baseUrl;

    public ImageAdapter(Context context, String[] images) {
        this.mArrayImages = new String[20];
        this.mArrayImages = images;
        this.mContext = context;
        this.baseUrl = "http://image.tmdb.org/t/p/w500";
    }

    @Override
    public int getCount() {
        return mArrayImages.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(370, 550));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(0, 0, 0, 0);
        } else {
            imageView = (ImageView) convertView;
        }
        Picasso.with(mContext).load(baseUrl+mArrayImages[position]).into(imageView);
        return imageView;
    }
}
