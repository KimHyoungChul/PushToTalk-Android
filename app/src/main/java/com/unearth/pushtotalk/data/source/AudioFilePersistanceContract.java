package com.unearth.pushtotalk.data.source;

import android.provider.BaseColumns;

/**
 * Created by Matthew Roberts on 12/13/2018.
 */

public class AudioFilePersistanceContract {

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    private AudioFilePersistanceContract() {}

    /* Inner class that defines the table contents */
    public static abstract class AudioFileEntry implements BaseColumns {
        public static final String TABLE_NAME = "audio";
        public static final String COLUMN_CREATE_DATE = "createDate";
        public static final String COLUMN_FILE_PATH = "filePath";
        public static final String COLUMN_MEDIA_URL = "mediaUrl";
        public static final String COLUMN_DIRTY = "dirty";
        public static final String COLUMN_REMOTE_ID = "remoteId";
    }
}
