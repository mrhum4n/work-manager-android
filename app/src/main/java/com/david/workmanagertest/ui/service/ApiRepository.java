package com.david.workmanagertest.ui.service;

import com.david.workmanagertest.ui.model.PostItem;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by David Sanjaya on 9/25/2020
 * WorkManagerTest
 */
public interface ApiRepository {
    @GET("posts")
    Observable<List<PostItem>> getPostItem();
}
