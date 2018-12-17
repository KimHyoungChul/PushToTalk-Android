package com.unearth.pushtotalk.presenter.MainScreen;

import com.unearth.pushtotalk.data.source.AudioModel;
import com.unearth.pushtotalk.presenter.BasePresenter;
import com.unearth.pushtotalk.presenter.BaseView;

import java.util.ArrayList;

/**
 * Created by Matthew Roberts on 12/16/2018.
 *
 * This specifies the contract between the view and the presenter.
 */
public class MainScreenContract  {

    public interface View extends BaseView<Presenter> {

        void showListOfAudioFiles(ArrayList<AudioModel> audioModelArrayList);

        void onNetworkError(int errorMessageId);

        void uploadSuccessFull(AudioModel audioModel);

        void onNetworkError(Throwable throwable);
    }

    public interface Presenter extends BasePresenter {

        void startRecording(String fileName);

        void stopRecording();

        void loadAudioFiles();
    }
}