package com.unearth.pushtotalk.data.source.remote;

import com.unearth.pushtotalk.data.source.AudioModel;

import java.util.Optional;

import io.reactivex.Flowable;
import okhttp3.MultipartBody;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by Matthew Roberts on 12/15/2018.
 */
public interface IAudioApi {

    @POST("audio")
    Flowable<Optional<AudioModel>> uploadFile(@Part("file") MultipartBody.Part filePart);

}
