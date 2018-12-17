package com.unearth.pushtotalk.data.source.local;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.squareup.sqlbrite2.BriteDatabase;
import com.squareup.sqlbrite2.SqlBrite;
import com.unearth.pushtotalk.data.source.AudioFileDatabaseHelper;
import com.unearth.pushtotalk.data.source.AudioFilePersistanceContract;
import com.unearth.pushtotalk.data.source.AudioModel;
import com.unearth.pushtotalk.data.source.DataSource;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class LocalDataSource implements DataSource {

    private static LocalDataSource INSTANCE;
    private int DIRTY = 1;
    private final BriteDatabase mDatabaseHelper;

    public static LocalDataSource getInstance(
            @NonNull Context context) {

        if (INSTANCE == null) {
            INSTANCE = new LocalDataSource(context);
        }
        return INSTANCE;
    }

    public LocalDataSource(Context context) {
        AudioFileDatabaseHelper dbHelper = new AudioFileDatabaseHelper(context);
        SqlBrite sqlBrite = new SqlBrite.Builder().build();
        mDatabaseHelper = sqlBrite.wrapDatabaseHelper(dbHelper, Schedulers.io());
    }

    @Override
    public Observable<AudioModel>createAudioFile(AudioModel audioFile) {
        ContentValues values = new ContentValues();
        values.put(AudioFilePersistanceContract.AudioFileEntry.COLUMN_CREATE_DATE, System.currentTimeMillis());
        values.put(AudioFilePersistanceContract.AudioFileEntry.COLUMN_DIRTY, DIRTY);
        values.put(AudioFilePersistanceContract.AudioFileEntry.COLUMN_FILE_PATH, audioFile.getLocalPath());
        mDatabaseHelper.insert(AudioFilePersistanceContract.AudioFileEntry.TABLE_NAME, values, SQLiteDatabase.CONFLICT_REPLACE);
        return null;
    }

    @Override
    public void updateAudioFile(AudioModel audioModel) {

    }

    @Override
    public void deleteAudioFile(long id) {

    }
}
