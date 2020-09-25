package com.david.workmanagertest.workManager;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.ExistingWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.david.workmanagertest.room.GeneralRepo;
import com.david.workmanagertest.service.ApiRepository;
import com.david.workmanagertest.service.RetrofitClient;
import com.david.workmanagertest.tools.Const;

import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by David Sanjaya on 9/25/2020
 * WorkManagerTest
 */
public class MyWorker extends Worker {
    private CompositeDisposable compositeDisposable;
    private GeneralRepo generalRepo;
    private Context context;
    public static final String WORKER_TAG = "my_worker";

    public MyWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        compositeDisposable = new CompositeDisposable();
        generalRepo = new GeneralRepo((Application) context);
        this.context = context;
    }

    @NonNull
    @Override
    public Result doWork() {
        ApiRepository apiRepository = RetrofitClient.getInstance().create(ApiRepository.class);
        compositeDisposable.add(apiRepository.getPostItem()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(d -> {
                })
                .subscribe(data -> {
                    generalRepo.createPostItem(data);
                }, throwable -> {
                }));
        // worker reload
        OneTimeWorkRequest workRequest = new OneTimeWorkRequest.Builder(MyWorker.class)
                .setInitialDelay(10, TimeUnit.SECONDS)
                .build();
        WorkManager.getInstance(context).enqueueUniqueWork(
                WORKER_TAG,
                ExistingWorkPolicy.REPLACE,
                workRequest);
        Log.e(Const.TAG, "doWork: Doing some work");
        return Result.success();
    }
}
