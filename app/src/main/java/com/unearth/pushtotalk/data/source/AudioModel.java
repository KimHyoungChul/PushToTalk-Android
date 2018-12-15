package com.unearth.pushtotalk.data.source;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by Matthew Roberts on 12/13/2018.
 */
public class AudioModel {

    @NonNull
    private final String localFilePath;

    @NonNull
    private final Date date;

    /**
     * Primary key of {@link AudioModel} in server mongoDB
     */
    @SerializedName("id")
    private Long remoteId;

    /**
     * Url of audio file on the server.
     */
    private String mediaUrl;

    private Long localId;

    private String localPath;

    public AudioModel(String filePath, Date date) {
        this.localFilePath = filePath;
        this.date = date;
    }

    public Long getReoteId() {
        return remoteId;
    }

    public String getMediaUrl() {
        return mediaUrl;
    }

    public Long getLocalId() {
        return localId;
    }

    public String getLocalPath() {
        return localPath;
    }

    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
    }

    public void setRemoteId(Long remoteId) {
        this.remoteId = remoteId;
    }
}
