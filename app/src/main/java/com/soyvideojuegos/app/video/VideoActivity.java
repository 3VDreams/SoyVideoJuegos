package com.soyvideojuegos.app.video;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.google.android.youtube.player.YouTubePlayerView;
import com.soyvideojuegos.app.R;
import com.soyvideojuegos.app.conf.YouTubeConf;
import com.soyvideojuegos.app.videolist.VideoListJSON;

public class VideoActivity extends YouTubeBaseActivity implements
        YouTubePlayer.OnInitializedListener {

    private static final int RQS_ErrorDialog = 1;
    private String videoId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_fullscreen);

        Intent intent = getIntent();
        videoId = intent.getStringExtra(VideoListJSON.VIDEOID);

        YouTubePlayerView youTubePlayerView = (YouTubePlayerView) findViewById(R.id.youtubeplayerview);
        youTubePlayerView.initialize(YouTubeConf.API_KEY, this);

    }

    @Override
    public void onInitializationFailure(Provider provider,
                                        YouTubeInitializationResult result) {

        if (result.isUserRecoverableError()) {
            result.getErrorDialog(this, RQS_ErrorDialog).show();
        }

    }

    @Override
    public void onInitializationSuccess(Provider provider,
                                        YouTubePlayer player, boolean wasRestored) {

        if (!wasRestored) {
            player.setFullscreen(true);
            player.cueVideo(videoId);
            //player.cuePlaylist(playListId);
        }

    }

}
