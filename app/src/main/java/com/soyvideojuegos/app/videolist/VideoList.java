package com.soyvideojuegos.app.videolist;

import android.content.Intent;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class VideoList {

    private String videoItemId;
    private String title;
    private String description;
    private Date publishedAt;
    private String thumbnails;
    private int position;
    private String playListId;
    private String videoId;

    public VideoList() {

    }

    public VideoList(String id, String title, String description,
                     String publishedAt, String thumbnails,
                     int posicion, String playListId, String videoId) {

        setVideoItemId(id);
        setTitle(title);
        setDescription(description);
        setPublishedAt(publishedAt);
        setThumbnails(thumbnails);
        setPosicion(posicion);
        setPlayListId(playListId);
        setVideoId(videoId);
    }

    public VideoList(Intent intent) {

        setVideoItemId(intent.getStringExtra(VideoListJSON.ID));
        setTitle(intent.getStringExtra(VideoListJSON.TITLE));
        setDescription(intent.getStringExtra(VideoListJSON.DESCRIPTION));
        setPublishedAt(intent.getStringExtra(VideoListJSON.PUBLISHEDAT));
        setThumbnails(intent.getStringExtra(VideoListJSON.THUMBNAILS));
        setPosicion(intent.getIntExtra(VideoListJSON.POSITION, 0));
        setPlayListId(intent.getStringExtra(VideoListJSON.PLAYLISTID));
        setVideoId(intent.getStringExtra(VideoListJSON.VIDEOID));

    }


    public static void packageIntent(Intent intent, String id, String title,
                                     String description, String publishedAt,
                                     String thumbnails, int posicion,
                                     String playListId, String videoId) {

        intent.putExtra(VideoListJSON.ID, id);
        intent.putExtra(VideoListJSON.TITLE, title);
        intent.putExtra(VideoListJSON.DESCRIPTION, description);
        intent.putExtra(VideoListJSON.PUBLISHEDAT, publishedAt);
        intent.putExtra(VideoListJSON.THUMBNAILS, thumbnails);
        intent.putExtra(VideoListJSON.POSITION, posicion);
        intent.putExtra(VideoListJSON.PLAYLISTID, playListId);
        intent.putExtra(VideoListJSON.VIDEOID, videoId);
    }

    public String toString() {
        return VideoListJSON.ID + "=" + getVideoItemId()
                + ":" + VideoListJSON.TITLE + "=" + getTitle()
                + ":" + VideoListJSON.DESCRIPTION + "=" + getDescription()
                + ":" + VideoListJSON.PUBLISHEDAT + "=" + getPublishedAt(Locale.US)
                + ":" + VideoListJSON.THUMBNAILS + "=" + getThumbnails()
                + ":" + VideoListJSON.POSITION + "=" + getPosicion()
                + ":" + VideoListJSON.PLAYLISTID + "=" + getPlayListId()
                + ":" + VideoListJSON.VIDEOID + "=" + getVideoId()
                ;
    }

    public String getVideoItemId() {
        return videoItemId;
    }

    public void setVideoItemId(String id) {
        this.videoItemId = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPublishedAt(String publishedAt) {

        SimpleDateFormat formatIn = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);

        try {

            this.publishedAt = formatIn.parse(publishedAt);

        } catch (ParseException ex) {
        }


    }

    public String getPublishedAt(Locale locale) {

        SimpleDateFormat formatOut = new SimpleDateFormat("EEEE, d MMM yyyy HH:mm:ss", locale);
        return formatOut.format(publishedAt);
    }

    public String getThumbnails() {
        return thumbnails;
    }

    public void setThumbnails(String thumbnails) {
        this.thumbnails = thumbnails;
    }

    public int getPosicion() {
        return position;
    }

    public void setPosicion(int posicion) {
        this.position = posicion;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getPlayListId() {
        return playListId;
    }

    public void setPlayListId(String playListId) {
        this.playListId = playListId;
    }
}
