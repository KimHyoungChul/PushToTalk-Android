package com.unearth.pushtotalk.presenter.MainScreen;

import android.support.annotation.NonNull;

import com.unearth.pushtotalk.MediaManager;
import com.unearth.pushtotalk.data.source.AudioModel;
import com.unearth.pushtotalk.data.source.DataRepository;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

/**
 * Created by Matthew Roberts on 12/16/2018.
 */
public class MainScreenPresenter implements MainScreenContract.Presenter {

    private final DataRepository mDataRepository;
    private final CompositeDisposable mCompositeDisposable;
    private MainScreenContract.View mMainView;
    private MediaManager mMediaManager;
    private AudioModel mAudioModel;

    public MainScreenPresenter(@NonNull DataRepository dataRepository,
                               MainScreenContract.View mainView,
                               MediaManager mediaManager) {
        mDataRepository = checkNotNull(dataRepository);
        mMainView = mainView;
        mMediaManager = mediaManager;
        mCompositeDisposable = new CompositeDisposable();
        mainView.setPresenter(this);
    }

    @Override
    public void startRecording(String filePath) {
        mMediaManager.startRecording(filePath);
        mAudioModel = new AudioModel(filePath);
    }

    @Override
    public void stopRecording() {
        mMediaManager.stopRecording();
        uploadAudioFile(mAudioModel);
    }

    private void uploadAudioFile(AudioModel audioModel) {
        mCompositeDisposable.add(mDataRepository
                .createAudioFile(audioModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        mediaFile -> {

                            mMainView.uploadSuccessFull(mediaFile);

                        },
                        throwable -> {

                            mMainView.onNetworkError(throwable);

                        }));
    }

    @Override
    public void loadAudioFiles() {

        //TODO: download data that doesn't yet exist locally.
    }

    @Override
    public void subscribe() {

        loadAudioFiles();
    }

    @Override
    public void unsubscribe() {
        mAudioModel = null;
        mCompositeDisposable.clear();
    }
}
