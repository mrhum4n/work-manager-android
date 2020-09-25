package com.david.workmanagertest.ui.viewModel;

import androidx.lifecycle.ViewModel;

import com.david.workmanagertest.ui.service.ApiRepository;
import com.david.workmanagertest.ui.service.RetrofitClient;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by David Sanjaya on 9/25/2020
 * WorkManagerTest
 */
public class MainActivityVm extends ViewModel {
    private ApiRepository apiRepository;
    private CompositeDisposable compositeDisposable;

    public MainActivityVm() {
        apiRepository = RetrofitClient.getInstance().create(ApiRepository.class);
        compositeDisposable = new CompositeDisposable();
    }

    public void loadPostItem(ResponseCallback callback) {
        compositeDisposable.add(apiRepository.getPostItem()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(d -> {
                    if (callback != null) callback.onStart();
                })
                .subscribe(data -> {
                    if (callback != null) callback.onSuccess();
                }, throwable -> {
                    if (callback != null) callback.onError(throwable.getMessage());
                }));
    }

    public interface ResponseCallback {
        void onStart();
        void onSuccess();
        void onError(String message);
    }
}
