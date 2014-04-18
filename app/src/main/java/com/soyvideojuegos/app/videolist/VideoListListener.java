package com.soyvideojuegos.app.videolist;

import java.util.List;

public interface VideoListListener {

    public void onFetchComplete(List<VideoList> data, String token);

    public void onFetchFailure(String msg);

    public void loadData();

}