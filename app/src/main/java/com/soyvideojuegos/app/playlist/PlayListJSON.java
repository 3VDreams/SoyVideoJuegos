package com.soyvideojuegos.app.playlist;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PlayListJSON {

    public final static String PREVPAGETOKEN = "prevPageToken";
    public final static String NEXTPAGETOKEN = "nextPageToken";

    public final static String ITEMS = "items";
    public final static String ITEMS_SNIPPET = "snippet";

    public final static String THUMBNAILS_DEFAULT = "default";
    public final static String THUMBNAILS_URL = "url";

    public final static String ID = "id";
    public final static String TITLE = "title";
    public final static String DESCRIPTION = "description";
    public final static String PUBLISHEDAT = "publishedAt";
    public final static String THUMBNAILS = "thumbnails";

    private String prevPageToken;
    private String nextPageToken;

    public PlayListJSON() {
        prevPageToken = "";
        nextPageToken = "";
    }

    public String getPrevPageToken() {
        return prevPageToken;
    }

    public String getNextPageToken() {
        return nextPageToken;
    }

    public ArrayList<PlayList> get(String data) throws JSONException {

        List<PlayList> apps = new ArrayList<PlayList>();
        JSONObject jData = new JSONObject(data);

        if (jData.has(PlayListJSON.PREVPAGETOKEN)) {
            this.prevPageToken = jData.getString(PlayListJSON.PREVPAGETOKEN);
        }

        if (jData.has(PlayListJSON.NEXTPAGETOKEN)) {
            this.nextPageToken = jData.getString(PlayListJSON.NEXTPAGETOKEN);
        }

        JSONArray jItems = jData.getJSONArray(PlayListJSON.ITEMS);

        for (int i = 0; i < jItems.length(); i++) {
            JSONObject jItem = jItems.getJSONObject(i);

            PlayList item = new PlayList();

            JSONObject jSnippet = jItem.getJSONObject(PlayListJSON.ITEMS_SNIPPET);
            JSONObject jThumbsnail = jSnippet.getJSONObject(PlayListJSON.THUMBNAILS);
            JSONObject jThumbsnailDefault = jThumbsnail.getJSONObject(PlayListJSON.THUMBNAILS_DEFAULT);

            item.setPlayListId(jItem.getString(PlayListJSON.ID));
            item.setTitle(jSnippet.getString(PlayListJSON.TITLE));
            item.setDescription(jSnippet.getString(PlayListJSON.DESCRIPTION));
            item.setPublishedAt(jSnippet.getString(PlayListJSON.PUBLISHEDAT));
            item.setThumbnails(jThumbsnailDefault.getString(PlayListJSON.THUMBNAILS_URL));

            apps.add(item);
        }

        return (ArrayList<PlayList>) apps;
    }

}
