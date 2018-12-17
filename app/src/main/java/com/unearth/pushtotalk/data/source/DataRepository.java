package com.unearth.pushtotalk.data.source;

import android.util.Log;

import java.util.LinkedHashMap;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Created by Matthew Roberts on 12/15/2018.
 */
public class DataRepository implements DataSource {

    private final DataSource mLocalRepository;

    private final DataSource mRemoteRepository;

    /**
     * In memory cache of {@link AudioModel}
     */
    private LinkedHashMap<Long, AudioModel> mCachedAudioFiles;

    private static DataRepository INSTANCE;

    /**
     * Returns the single instance of this class, creating it if necessary.
     *
     * @param audioRemoteDataSource the backend data source
     * @param audioLocalDataSourcee the device storage data source
     * @return the {@link DataRepository} instance
     */
    public static DataRepository getInstance(@android.support.annotation.NonNull DataSource audioRemoteDataSource,
                                             @android.support.annotation.NonNull DataSource audioLocalDataSourcee) {
        if (INSTANCE == null) {
            INSTANCE = new DataRepository(audioRemoteDataSource, audioLocalDataSourcee);
        }
        return INSTANCE;
    }

    private DataRepository(@NonNull DataSource localRepository,
                           DataSource remoteRepository) {

        mLocalRepository = localRepository;
        mRemoteRepository = remoteRepository;
    }

    @Override
    public Observable<AudioModel> createAudioFile(AudioModel audioModel) {

        Observable<AudioModel> remoteCreation = saveAudioFileToRemoteRepository(audioModel);
        return remoteCreation;
    }

    @Override
    public void updateAudioFile(AudioModel audioModel) {
        //TODO: implment update
    }

    @Override
    public void deleteAudioFile(long id) {
        //TODO: implment delete
    }

    @NonNull
    Observable<AudioModel> saveAudioFileToRemoteRepository(@NonNull final AudioModel audioFile) {
        return mRemoteRepository
                .createAudioFile(audioFile)
                .doOnEach(new Observer<AudioModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(AudioModel serverFile) {
                        audioFile.setMediaUrl(serverFile.getMediaUrl());
                        audioFile.setRemoteId(serverFile.getRemoteId());
                        mLocalRepository.createAudioFile(audioFile);
                        addToInMemoryCache(audioFile);
                    }

                    @Override
                    public void onError(Throwable e) {
                        //TODO:handle retry logic
                        Log.e(DataRepository.class.getSimpleName(),"Network error");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void addToInMemoryCache(AudioModel audioFile) {
        if (mCachedAudioFiles == null) {
            mCachedAudioFiles = new LinkedHashMap<>();
        }
        mCachedAudioFiles.put(audioFile.getLocalId(), audioFile);
    }
}
