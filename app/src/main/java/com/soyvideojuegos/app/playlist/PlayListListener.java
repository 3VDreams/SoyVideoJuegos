package com.soyvideojuegos.app.playlist;

import java.util.List;

public interface PlayListListener {

    public void onFetchComplete(List<PlayList> data, String token);

    public void onFetchFailure(String msg);

    public void loadData();

}