package com.unearth.pushtotalk.data.source;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by Matthew Roberts on 12/13/2018.
 */
public class AudioModel {

    private String localFilePath;

    private Date createdAt;

    private Date updatedAt;

    /**
     * Primary key of {@link AudioModel} in server mongoDB
     */
    @SerializedName("_id")
    private String remoteId;

    /**
     * Url of audio file on the server.
     */
    private String mediaUrl;

    private Long localId;

    public AudioModel(String filePath) {
        this.localFilePath = filePath;
    }

    public String getRemoteId() {
        return remoteId;
    }

    public String getMediaUrl() {
        return mediaUrl;
    }

    public Long getLocalId() {
        return localId;
    }

    public String getLocalPath() {
        return localFilePath;
    }

    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
    }

    public void setRemoteId(String remoteId) {
        this.remoteId = remoteId;
    }
}
