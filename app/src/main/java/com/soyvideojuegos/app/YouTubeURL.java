package com.soyvideojuegos.app;

import com.soyvideojuegos.app.conf.YouTubeConf;


public class YouTubeURL {

    private int maxResult;
    private String pageToken;

    public YouTubeURL() {
        this.maxResult = 10;
        this.pageToken = "";
    }

    public int getMaxResult() {
        return this.maxResult;
    }

    public void setMaxResult(int maxResult) {
        this.maxResult = maxResult;
    }

    public String getPageToken() {
        return this.pageToken;
    }

    public void setPageToken(String pageToken) {
        this.pageToken = pageToken;
    }

    public void setURL(String pageToken) {
        setPageToken(pageToken);
    }

    public String getPlayListURL() {

        String url = YouTubeConf.BASE_URL + YouTubeConf.PLAY_LIST + "?part=snippet&maxResults=" + getMaxResult()
                + "&channelId=" + YouTubeConf.CHANNEL_ID
                + "&key=" + YouTubeConf.API_KEY;

        if (!pageToken.equalsIgnoreCase("")) {
            url = url + "&pageToken=" + pageToken;
        }

        return url;
    }

    public String getVideoItemURL(String playListId) {

        String url = YouTubeConf.BASE_URL + YouTubeConf.VIDEO_ITEM + "?part=snippet&maxResults=" + getMaxResult()
                + "&playlistId=" + playListId
                + "&key=" + YouTubeConf.API_KEY;

        if (!pageToken.equalsIgnoreCase("")) {
            url = url + "&pageToken=" + pageToken;
        }

        return url;
    }
}