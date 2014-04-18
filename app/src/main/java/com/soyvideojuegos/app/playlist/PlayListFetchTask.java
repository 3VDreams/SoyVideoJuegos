package com.soyvideojuegos.app.playlist;

import android.os.AsyncTask;
import android.util.Log;

import com.soyvideojuegos.app.conf.LogConf;
import com.soyvideojuegos.app.downloader.Utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class PlayListFetchTask extends AsyncTask<String, Void, String> {

    private final PlayListListener listener;
    private String msg;

    public PlayListFetchTask(PlayListListener listener) {
        this.listener = listener;
    }

    @Override
    protected String doInBackground(String... params) {
        if (params == null) {
            return null;
        }

        String url = params[0];

        HttpClient client = new DefaultHttpClient();
        HttpGet getRequest = new HttpGet(url);

        try {
            HttpResponse response = client.execute(getRequest);
            final int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                Log.w(LogConf.TAG, "Error " + statusCode +
                        " while retrieving JSON from " + url);
                return null;
            }

            final HttpEntity entity = response.getEntity();

            if (entity == null) {
                msg = "No response from server";
                return null;
            }

            // get response content and convert it to json string
            InputStream is = entity.getContent();
            return Utils.streamToString(is);
        } catch (IOException e) {
            msg = "No Network Connection";
        }

        return null;
    }

    @Override
    protected void onPostExecute(String stream) {
        if (stream == null) {

            if (listener != null) {
                listener.onFetchFailure(msg);
            }

            return;
        }

        try {

            PlayListJSON json = new PlayListJSON();
            List<PlayList> apps = json.get(stream);

            if (listener != null) {
                listener.onFetchComplete(apps, json.getNextPageToken());
            }

        } catch (JSONException e) {
            msg = "Invalid response";

            if (listener != null) {
                listener.onFetchFailure(msg);
            }

            return;
        }
    }


}
