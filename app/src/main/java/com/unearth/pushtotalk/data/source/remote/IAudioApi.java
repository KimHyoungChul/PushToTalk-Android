package com.unearth.pushtotalk.data.source.remote;

import com.unearth.pushtotalk.data.source.AudioModel;

import java.util.Observer;
import java.util.Optional;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by Matthew Roberts on 12/15/2018.
 */
public interface IAudioApi {

    @POST("audio/upload")
    Observable<AudioModel> uploadFile(@Body RequestBody filePart);

}
