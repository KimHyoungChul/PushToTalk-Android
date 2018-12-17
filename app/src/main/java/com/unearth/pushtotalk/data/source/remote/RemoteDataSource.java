package com.unearth.pushtotalk.data.source.remote;

import com.unearth.pushtotalk.data.source.AudioModel;
import com.unearth.pushtotalk.data.source.DataSource;

import java.io.File;
import java.util.Observer;
import java.util.Optional;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Retrofit;

public class RemoteDataSource implements DataSource {

    private static RemoteDataSource INSTANCE;

    public static RemoteDataSource getInstance(Retrofit retrofit) {
        if (INSTANCE == null) {
            INSTANCE = new RemoteDataSource(retrofit);
        }
        return INSTANCE;
    }

    private final Retrofit mRetrofit;

    private RemoteDataSource(Retrofit retrofit) {
        mRetrofit = retrofit;
    }

    @Override
    public Observable<AudioModel> createAudioFile(AudioModel audioFile) {
        IAudioApi service = mRetrofit.create(IAudioApi.class);
        File file = new File(audioFile.getLocalPath());

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", file.getName(),RequestBody.create(MediaType.parse("mp4/*"), file))
                .build();

        return service.uploadFile(requestBody);
    }

    @Override
    public void updateAudioFile(AudioModel audioModel) {

    }

    @Override
    public void deleteAudioFile(long id) {

    }
}
