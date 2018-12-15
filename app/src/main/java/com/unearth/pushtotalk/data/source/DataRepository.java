package com.unearth.pushtotalk.data.source;

import java.util.LinkedHashMap;
import java.util.Optional;

import io.reactivex.Flowable;
import io.reactivex.annotations.NonNull;

/**
 * Created by Matthew Roberts on 12/15/2018.
 */
public class DataRepository implements DataSource {

    private static DataRepository INSTANCE;

    /**
     * Returns the single instance of this class, creating it if necessary.
     *
     * @param audioRemoteDataSource the backend data source
     * @param audioLocalDataSourcee  the device storage data source
     * @return the {@link DataRepository} instance
     */
    public static DataRepository getInstance(@android.support.annotation.NonNull DataSource audioRemoteDataSource,
                                              @android.support.annotation.NonNull DataSource audioLocalDataSourcee) {
        if (INSTANCE == null) {
            INSTANCE = new DataRepository(audioRemoteDataSource, audioLocalDataSourcee);
        }
        return INSTANCE;
    }

    private final DataSource mLocalRepository;

    private final DataSource mRemoteRepository;

    /**
     * In memory cache of {@link AudioModel}
     */
    private LinkedHashMap<Long, AudioModel> mCachedAudioFiles;

    private DataRepository(@NonNull DataSource localRepository,
                           DataSource remoteRepository) {

        mLocalRepository = localRepository;
        mRemoteRepository = remoteRepository;
    }

    @Override
    public Flowable<Optional<AudioModel>> createAudioFile(AudioModel audioModel) {

        Flowable<Optional<AudioModel>> remoteCreation = saveAudioFileToRemoteRepository(audioModel);
        return remoteCreation;
    }

    @Override
    public void updateAudioFile() {

    }

    @Override
    public void deleteAudioFile() {

    }

    @NonNull
    Flowable<Optional<AudioModel>> saveAudioFileToLocalRepository(@NonNull final AudioModel audioFile) {
        return mLocalRepository
                .createAudioFile(audioFile)
                .doOnNext(taskOptional -> {
                    if (taskOptional.isPresent()) {
                        addToInMemoryCache(audioFile);
                    }
                })
                .firstElement().toFlowable();
    }

    @NonNull
    Flowable<Optional<AudioModel>> saveAudioFileToRemoteRepository(@NonNull final AudioModel audioFile) {
        return mRemoteRepository
                .createAudioFile(audioFile)
                .doOnNext(taskOptional -> {
                    if (taskOptional.isPresent()) {
                        AudioModel serverFile = taskOptional.get();
                        audioFile.setMediaUrl(serverFile.getMediaUrl());
                        audioFile.setRemoteId(serverFile.getReoteId());
                        mLocalRepository.createAudioFile(audioFile);
                        addToInMemoryCache(audioFile);
                    }
                })
                .firstElement().toFlowable();
    }

    private void addToInMemoryCache(AudioModel audioFile) {
        if (mCachedAudioFiles == null) {
            mCachedAudioFiles = new LinkedHashMap<>();
        }
        mCachedAudioFiles.put(audioFile.getLocalId(), audioFile);
    }
}
