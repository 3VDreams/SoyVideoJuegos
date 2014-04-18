package com.soyvideojuegos.app.playlist;

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
import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.MapBuilder;
import com.google.analytics.tracking.android.Fields;
import com.soyvideojuegos.app.R;
import com.soyvideojuegos.app.YouTubeURL;
import com.soyvideojuegos.app.videolist.VideoListJSON;
import java.util.ArrayList;
import java.util.List;

public class PlayListActivity extends ListActivity implements PlayListListener,
        OnScrollListener,
        OnItemClickListener {

    private static final int ADD_PLAYLIST_ITEM_REQUEST = 0;
    private ProgressDialog dialog;
    private PlayListAdapter adapter;
    private ListView listView;
    private boolean isLoading;
    private boolean isComplete;
    private boolean isBusy = false;

    private List<PlayList> items;
    private String token;
    private View footer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.playlists);

        String name = "PlayListActivity";
        EasyTracker tracker = EasyTracker.getInstance(this);
        tracker.set(Fields.SCREEN_NAME, name);
        tracker.send(MapBuilder.createAppView().build());

        this.listView = getListView();
        this.listView.setOnScrollListener(this);
        this.listView.setOnItemClickListener(this);

        footer = getLayoutInflater().inflate(R.layout.loading, null);
        this.listView.addFooterView(footer);
        this.listView.setFooterDividersEnabled(true);

        this.items = new ArrayList<PlayList>();
        this.adapter = new PlayListAdapter(this, this.items, getResources().getConfiguration().locale);

        this.token = "";
        this.dialog = ProgressDialog.show(this, "", getResources().getString(R.string.loading));

        this.listView.removeFooterView(footer);
        loadData();

        setListAdapter(adapter);
    }

    @Override
    public void onFetchComplete(List<PlayList> data, String token) {

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
            PlayList playListItem = new PlayList(data);
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

        PlayListFetchTask task = new PlayListFetchTask(this);
        task.execute(pUrl.getPlayListURL(), token);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        PlayList playList = items.get(position);

        String category = "PlayList";
        String action = "OnClick";
        String label = playList.getTitle();
        Long value = 0l;

        EasyTracker tracker = EasyTracker.getInstance(this);
        tracker.send(MapBuilder
                    .createEvent(category, action, label, value)
                    .build()
        );

        Intent intent = new Intent(this, com.soyvideojuegos.app.videolist.VideoListActivity.class);
        intent.putExtra(VideoListJSON.PLAYLISTID, playList.getPlayListId());
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EasyTracker.getInstance(this).activityStart(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EasyTracker.getInstance(this).activityStop(this);
    }
}
