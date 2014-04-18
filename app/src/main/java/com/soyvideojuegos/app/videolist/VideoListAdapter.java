package com.soyvideojuegos.app.videolist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.soyvideojuegos.app.R;
import com.soyvideojuegos.app.downloader.ImageLoaderCache;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class VideoListAdapter extends ArrayAdapter<VideoList> {

    public ImageLoaderCache imageLoader;
    LayoutInflater mLayoutInflater;
    Locale locale;
    private List<VideoList> mItems;

    public VideoListAdapter(Context context, Locale locale) {
        super(context, R.layout.playlist_item);
        mLayoutInflater = LayoutInflater.from(getContext());
        mItems = new ArrayList<VideoList>();
        imageLoader = new ImageLoaderCache(context);
        this.locale = locale;
    }

    public VideoListAdapter(Context context, List<VideoList> data, Locale locale) {
        super(context, R.layout.playlist_item, data);
        this.mItems = data;
        mLayoutInflater = LayoutInflater.from(getContext());
        imageLoader = new ImageLoaderCache(context);
        this.locale = locale;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        PlayListViewHolder holder;

        if (v == null) {
            v = mLayoutInflater.inflate(R.layout.playlist_item, parent, false);
            holder = new PlayListViewHolder();

            holder.title = (TextView) v.findViewById(R.id.title);
            holder.publishedAt = (TextView) v.findViewById(R.id.publishedAt);
            holder.thumbnails = (ImageView) v.findViewById(R.id.thumbnails);
            holder.thumbnails.setScaleType(ImageView.ScaleType.FIT_XY);

            v.setTag(holder);

        } else {
            holder = (PlayListViewHolder) v.getTag();
        }


        VideoList item = mItems.get(position);

        if (item != null) {
            holder.title.setText(item.getTitle());
            holder.publishedAt.setText(item.getPublishedAt(locale));
            imageLoader.DisplayImage(item.getThumbnails(), holder.thumbnails);
        }

        return v;
    }

    public void add(VideoList item) {
        mItems.add(item);
        notifyDataSetChanged();
    }

    public void clear() {
        mItems.clear();
        notifyDataSetChanged();
    }

    @Override
    public VideoList getItem(int posicion) {
        return mItems.get(posicion);
    }

    @Override
    public long getItemId(int posicion) {
        return posicion;
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    public static class PlayListViewHolder {
        TextView title;
        TextView publishedAt;
        ImageView thumbnails;
    }

}
