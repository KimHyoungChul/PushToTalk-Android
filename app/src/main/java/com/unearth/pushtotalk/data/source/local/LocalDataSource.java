package com.unearth.pushtotalk.data.source.local;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.squareup.sqlbrite2.BriteDatabase;
import com.squareup.sqlbrite2.SqlBrite;
import com.unearth.pushtotalk.data.BaseSchedulerProvider;
import com.unearth.pushtotalk.data.source.AudioModel;
import com.unearth.pushtotalk.data.source.AudioFileDatabaseHelper;
import com.unearth.pushtotalk.data.source.AudioFilePersistanceContract;
import com.unearth.pushtotalk.data.source.DataSource;

import java.util.Optional;

import io.reactivex.Flowable;

public class LocalDataSource implements DataSource {

    private int DIRTY = 1;
    private final BriteDatabase mDatabaseHelper;

    public LocalDataSource(Context context, BaseSchedulerProvider schedulerProvider) {
        AudioFileDatabaseHelper dbHelper = new AudioFileDatabaseHelper(context);
        SqlBrite sqlBrite = new SqlBrite.Builder().build();
        mDatabaseHelper = sqlBrite.wrapDatabaseHelper(dbHelper, schedulerProvider.io());
    }

    @Override
    public Flowable<Optional<AudioModel>> createAudioFile(AudioModel audioFile) {
        ContentValues values = new ContentValues();
        values.put(AudioFilePersistanceContract.AudioFileEntry.COLUMN_CREATE_DATE, System.currentTimeMillis());
        values.put(AudioFilePersistanceContract.AudioFileEntry.COLUMN_DIRTY, DIRTY);
        values.put(AudioFilePersistanceContract.AudioFileEntry.COLUMN_FILE_PATH, audioFile.getLocalPath());
        mDatabaseHelper.insert(AudioFilePersistanceContract.AudioFileEntry.TABLE_NAME, values, SQLiteDatabase.CONFLICT_REPLACE);
        return null;
    }

    @Override
    public void updateAudioFile() {

    }

    @Override
    public void deleteAudioFile() {

    }
}
