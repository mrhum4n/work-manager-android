package com.david.workmanagertest.ui.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.david.workmanagertest.ui.model.PostItem;
import com.david.workmanagertest.ui.tools.Const;

/**
 * Created by David Sanjaya on 9/25/2020
 * WorkManagerTest
 */
@Database(
        entities = {PostItem.class},
        version = Const.DB_VERSION
        )
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase instance;
    public abstract GeneralDao getGeneralDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, Const.DB_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
