package com.david.workmanagertest.ui.room;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.david.workmanagertest.ui.model.PostItem;

import java.util.List;

/**
 * Created by David Sanjaya on 9/25/2020
 * WorkManagerTest
 */
public class GeneralRepo {
    private GeneralDao generalDao;

    public GeneralRepo(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        generalDao = database.getGeneralDao();
    }

    private void execute(Runnable run) {
        new Thread(run).start();
    }

    public void createPostItem(List<PostItem> postItems) {
        Runnable run = () -> generalDao.insertItem(postItems);
        execute(run);
    }

    public LiveData<List<PostItem>> getPostItem() {
        return generalDao.getAllItems();
    }
}
