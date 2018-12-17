package com.unearth.pushtotalk.data.source;


import io.reactivex.Observable;

public interface DataSource {

    Observable<AudioModel> createAudioFile(AudioModel audioFile);
    void updateAudioFile(AudioModel audioModel);
    void deleteAudioFile(long id);
}
