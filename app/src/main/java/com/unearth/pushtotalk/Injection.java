package com.unearth.pushtotalk;

import android.content.Context;

import com.unearth.pushtotalk.data.source.DataRepository;
import com.unearth.pushtotalk.data.source.local.LocalDataSource;
import com.unearth.pushtotalk.data.source.remote.RemoteDataSource;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * Created by Matthew Roberts on 12/15/2018.
 */
public class Injection {
    public static DataRepository provideAudioFileRepository(Context context) {

        OkHttpClient httpClient = new OkHttpClient();
        OkHttpClient.Builder builder = httpClient.newBuilder()
                .readTimeout(20, TimeUnit.SECONDS)
                .connectTimeout(20, TimeUnit.SECONDS);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(builder.build())
                .build();

        return DataRepository.getInstance(RemoteDataSource.getInstance(retrofit),
                LocalDataSource.getInstance(context));
    }
}
