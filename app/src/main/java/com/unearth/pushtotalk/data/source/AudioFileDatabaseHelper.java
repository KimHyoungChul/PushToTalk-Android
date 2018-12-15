package com.unearth.pushtotalk.data.source;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Matthew Roberts on 12/13/2018.
 */

public class AudioFileDatabaseHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "Audio.db";

    private static final String TEXT_TYPE = " TEXT";

    private static final String INT_TYPE = " INTEGER";

    private static final String COMMA_SEP = ",";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + AudioFilePersistanceContract.AudioFileEntry.TABLE_NAME + " (" +
                    AudioFilePersistanceContract.AudioFileEntry._ID + TEXT_TYPE + " PRIMARY KEY," +
                    AudioFilePersistanceContract.AudioFileEntry.COLUMN_CREATE_DATE + INT_TYPE + COMMA_SEP +
                    AudioFilePersistanceContract.AudioFileEntry.COLUMN_FILE_PATH + TEXT_TYPE +
                    AudioFilePersistanceContract.AudioFileEntry.COLUMN_MEDIA_URL + TEXT_TYPE +
                    AudioFilePersistanceContract.AudioFileEntry.COLUMN_DIRTY + INT_TYPE +
                    AudioFilePersistanceContract.AudioFileEntry.COLUMN_REMOTE_ID + TEXT_TYPE +
                    " )";

    public AudioFileDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}