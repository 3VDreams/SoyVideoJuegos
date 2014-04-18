package com.soyvideojuegos.app.videolist;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class VideoListJSON {

    public final static String PREVPAGETOKEN = "prevPageToken";
    public final static String NEXTPAGETOKEN = "nextPageToken";

    public final static String ITEMS = "items";
    public final static String ITEMS_ID = "id";
    public final static String ITEMS_SNIPPET = "snippet";
    public final static String ITEMS_SNIPPET_RESOURCEID = "resourceId";

    public final static String THUMBNAILS_DEFAULT = "default";
    public final static String THUMBNAILS_URL = "url";

    public final static String ID = "id";
    public final static String TITLE = "title";
    public final static String DESCRIPTION = "description";
    public final static String PUBLISHEDAT = "publishedAt";
    public final static String THUMBNAILS = "thumbnails";
    public final static String PLAYLISTID = "playlistId";
    public final static String POSITION = "position";
    public final static String VIDEOID = "videoId";

    private String prevPageToken;
    private String nextPageToken;

    public VideoListJSON() {
        prevPageToken = "";
        nextPageToken = "";
    }

    public String getPrevPageToken() {
        return prevPageToken;
    }

    public String getNextPageToken() {
        return nextPageToken;
    }

    public ArrayList<VideoList> get(String data) throws JSONException {

        List<VideoList> apps = new ArrayList<VideoList>();
        JSONObject jData = new JSONObject(data);

        if (jData.has(VideoListJSON.PREVPAGETOKEN)) {
            this.prevPageToken = jData.getString(VideoListJSON.PREVPAGETOKEN);
        }

        if (jData.has(VideoListJSON.NEXTPAGETOKEN)) {
            this.nextPageToken = jData.getString(VideoListJSON.NEXTPAGETOKEN);
        }

        JSONArray jItems = jData.getJSONArray(VideoListJSON.ITEMS);

        for (int i = 0; i < jItems.length(); i++) {
            JSONObject jItem = jItems.getJSONObject(i);

            VideoList item = new VideoList();

            JSONObject jSnippet = jItem.getJSONObject(VideoListJSON.ITEMS_SNIPPET);
            JSONObject jThumbsnail = jSnippet.getJSONObject(VideoListJSON.THUMBNAILS);
            JSONObject jThumbsnailDefault = jThumbsnail.getJSONObject(VideoListJSON.THUMBNAILS_DEFAULT);

            JSONObject jResourceId = jSnippet.getJSONObject(VideoListJSON.ITEMS_SNIPPET_RESOURCEID);

            item.setVideoItemId(jItem.getString(VideoListJSON.ITEMS_ID));
            item.setTitle(jSnippet.getString(VideoListJSON.TITLE));
            item.setDescription(jSnippet.getString(VideoListJSON.DESCRIPTION));
            item.setPublishedAt(jSnippet.getString(VideoListJSON.PUBLISHEDAT));
            item.setThumbnails(jThumbsnailDefault.getString(VideoListJSON.THUMBNAILS_URL));
            item.setPosicion(jSnippet.getInt(VideoListJSON.POSITION));
            item.setPlayListId(jSnippet.getString(VideoListJSON.PLAYLISTID));
            item.setVideoId(jResourceId.getString(VideoListJSON.VIDEOID));

            apps.add(item);
        }

        return (ArrayList<VideoList>) apps;
    }

}
