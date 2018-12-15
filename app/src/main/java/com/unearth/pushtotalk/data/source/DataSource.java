package com.unearth.pushtotalk.data.source;

import java.util.Optional;

import io.reactivex.Flowable;

public interface DataSource {

    Flowable<Optional<AudioModel>> createAudioFile(AudioModel audioFile);
    void updateAudioFile();
    void deleteAudioFile();
}
