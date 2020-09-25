package com.david.workmanagertest.ui.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.david.workmanagertest.ui.model.PostItem;

import java.util.List;

/**
 * Created by David Sanjaya on 9/25/2020
 * WorkManagerTest
 */
@Dao
public interface GeneralDao {
    // Insert item
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertItem(List<PostItem> postItems);

    // get all data
    @Query("SELECT * FROM postItem")
    LiveData<List<PostItem>> getAllItems();
}
