package com.soyvideojuegos.app.playlist;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class PlayList implements Parcelable {

    private String playListId;
    private String title;
    private String description;
    private Date publishedAt;
    private String thumbnails;

    public PlayList() {

    }

    public PlayList(String id, String title, String description,
                    String publishedAt, String thumbnails) {
        setPlayListId(id);
        setTitle(title);
        setDescription(description);
        setPublishedAt(publishedAt);
        setThumbnails(thumbnails);

    }

    public PlayList(Intent intent) {

        setPlayListId(intent.getStringExtra(PlayListJSON.ID));
        setTitle(intent.getStringExtra(PlayListJSON.TITLE));
        setDescription(intent.getStringExtra(PlayListJSON.DESCRIPTION));
        setPublishedAt(intent.getStringExtra(PlayListJSON.PUBLISHEDAT));
        setThumbnails(intent.getStringExtra(PlayListJSON.THUMBNAILS));
    }


    public static void packageIntent(Intent intent, String id, String title,
                                     String description, String publishedAt,
                                     String thumbnails) {
        intent.putExtra(PlayListJSON.ID, id);
        intent.putExtra(PlayListJSON.TITLE, title);
        intent.putExtra(PlayListJSON.DESCRIPTION, description);
        intent.putExtra(PlayListJSON.PUBLISHEDAT, publishedAt);
        intent.putExtra(PlayListJSON.THUMBNAILS, thumbnails);
    }

    public String toString() {
        return PlayListJSON.ID + "=" + getPlayListId()
                + ":" + PlayListJSON.TITLE + "=" + getTitle()
                + ":" + PlayListJSON.DESCRIPTION + "=" + getDescription()
                + ":" + PlayListJSON.PUBLISHEDAT + "=" + getPublishedAt(Locale.US)
                + ":" + PlayListJSON.THUMBNAILS + "=" + getThumbnails()
                ;
    }

    public String getPlayListId() {
        return playListId;
    }

    public void setPlayListId(String id) {
        this.playListId = id;
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

        SimpleDateFormat formatIn = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", new Locale("CO"));

        try {

            this.publishedAt = formatIn.parse(publishedAt);

        } catch (ParseException ex) {
        }
    }

    public String getPublishedAt(Locale locale) {

        SimpleDateFormat formatOut = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss", locale);
        return formatOut.format(publishedAt);
    }

    public String getPublishedAtTimeZone() {

        SimpleDateFormat formatOut = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", new Locale("CO"));
        return formatOut.format(publishedAt);
    }

    public String getThumbnails() {
        return thumbnails;
    }

    public void setThumbnails(String thumbnails) {
        this.thumbnails = thumbnails;
    }

    private PlayList(Parcel in) {
        setPlayListId(in.readString());
        setTitle(in.readString());
        setDescription(in.readString());
        setPublishedAt(in.readString());
        setThumbnails(in.readString());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(getPlayListId());
        dest.writeString(getTitle());
        dest.writeString(getDescription());
        dest.writeString(getPublishedAtTimeZone());
        dest.writeString(getThumbnails());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<PlayList> CREATOR = new Parcelable.Creator<PlayList>() {
        public PlayList createFromParcel(Parcel in) {
            return new PlayList(in);
        }

        public PlayList[] newArray(int size) {
            return new PlayList[size];
        }
    };


}
