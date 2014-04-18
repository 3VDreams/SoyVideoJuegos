package com.soyvideojuegos.app.videolist;

import android.annotation.SuppressLint;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.soyvideojuegos.app.R;
import com.soyvideojuegos.app.YouTubeURL;

import java.util.ArrayList;
import java.util.List;

public class VideoListActivity extends ListActivity implements VideoListListener,
        OnScrollListener,
        OnItemClickListener {

    private static final int ADD_PLAYLIST_ITEM_REQUEST = 0;
    private ProgressDialog dialog;
    private VideoListAdapter adapter;
    private ListView listView;
    private boolean isLoading;
    private boolean isComplete;
    private boolean isBusy = false;

    private List<VideoList> items;
    private String token;
    private View footer;
    private String playListId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.playlists);

        Intent intent = getIntent();
        playListId = intent.getStringExtra(VideoListJSON.PLAYLISTID);

        this.listView = getListView();
        this.listView.setOnScrollListener(this);
        this.listView.setOnItemClickListener(this);

        footer = getLayoutInflater().inflate(R.layout.loading, null);
        //this.listView.addFooterView(footer);
        //this.listView.setFooterDividersEnabled(true);

        this.items = new ArrayList<VideoList>();
        this.adapter = new VideoListAdapter(this, this.items, getResources().getConfiguration().locale);

        this.token = "";
        this.dialog = ProgressDialog.show(this, "", getResources().getString(R.string.loading));

        loadData();

        setListAdapter(adapter);
    }

    @Override
    public void onFetchComplete(List<VideoList> data, String token) {

        if (dialog != null) {
            dialog.dismiss();
        }

        this.items.addAll(data);
        adapter.notifyDataSetChanged();

        this.isLoading = false;
        this.listView.removeFooterView(footer);

        if (token.equalsIgnoreCase("")) {
            this.isComplete = true;
        } else {
            this.token = token;
        }

    }

    @Override
    public void onFetchFailure(String msg) {

        if (dialog != null) {
            dialog.dismiss();
        }

        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
        this.isLoading = false;
        this.listView.removeFooterView(footer);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == ADD_PLAYLIST_ITEM_REQUEST && resultCode == RESULT_OK) {
            VideoList playListItem = new VideoList(data);
            adapter.add(playListItem);
        }

    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

        switch (scrollState) {
            case OnScrollListener.SCROLL_STATE_IDLE:
                isBusy = true;
                break;

            case OnScrollListener.SCROLL_STATE_FLING:
                isBusy = false;
                break;

            case OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
                isBusy = false;
                break;
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {

        if (getListAdapter() == null)
            return;

        if (getListAdapter().getCount() == 0)
            return;

        if (visibleItemCount + firstVisibleItem >= totalItemCount
                && !isLoading && !isComplete && !isBusy) {


            isLoading = true;
            loadData();
        }

    }

    @Override
    public void loadData() {

        this.listView.addFooterView(footer);

        this.isLoading = true;

        YouTubeURL pUrl = new YouTubeURL();
        pUrl.setMaxResult(10);
        pUrl.setPageToken(token);

        VideoListFetchTask task = new VideoListFetchTask(this);
        task.execute(pUrl.getVideoItemURL(playListId), token);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent intent = new Intent(this, com.soyvideojuegos.app.video.VideoActivity.class);
        intent.putExtra(VideoListJSON.VIDEOID, items.get(position).getVideoId());
        intent.putExtra(VideoListJSON.PLAYLISTID, items.get(position).getPlayListId());
        startActivity(intent);

    }


}
