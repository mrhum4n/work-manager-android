package com.david.workmanagertest.ui.viewModel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.david.workmanagertest.ui.model.PostItem;
import com.david.workmanagertest.ui.room.GeneralRepo;
import com.david.workmanagertest.ui.service.ApiRepository;
import com.david.workmanagertest.ui.service.RetrofitClient;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by David Sanjaya on 9/25/2020
 * WorkManagerTest
 */
public class MainActivityVm extends AndroidViewModel {
    private ApiRepository apiRepository;
    private CompositeDisposable compositeDisposable;
    private GeneralRepo generalRepo;
    private LiveData<List<PostItem>> postItemLiveData;

    public MainActivityVm(@NonNull Application application) {
        super(application);
        apiRepository = RetrofitClient.getInstance().create(ApiRepository.class);
        compositeDisposable = new CompositeDisposable();
        generalRepo = new GeneralRepo(application);
        postItemLiveData = generalRepo.getPostItem();
    }

    /*public MainActivityVm(Application application) {
        apiRepository = RetrofitClient.getInstance().create(ApiRepository.class);
        compositeDisposable = new CompositeDisposable();
        generalRepo = new GeneralRepo(application);
        postItemLiveData = generalRepo.getPostItem();
    }*/

    public void loadPostItem(ResponseCallback callback) {
        compositeDisposable.add(apiRepository.getPostItem()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(d -> {
                    if (callback != null) callback.onStart();
                })
                .subscribe(data -> {
                    if (callback != null) callback.onSuccess();
                    generalRepo.createPostItem(data);
                }, throwable -> {
                    if (callback != null) callback.onError(throwable.getMessage());
                }));
    }

    public LiveData<List<PostItem>> getPostItem() {
        return postItemLiveData;
    }

    public interface ResponseCallback {
        void onStart();
        void onSuccess();
        void onError(String message);
    }
}
