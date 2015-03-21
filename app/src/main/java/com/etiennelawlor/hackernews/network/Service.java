package com.etiennelawlor.hackernews.network;

import com.etiennelawlor.hackernews.network.models.TopStory;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.Path;

public interface Service {

    @Headers("Accept: application/json")
    @GET("/topstories.json?print=pretty")
    void getTopStoryIds(Callback<List<Long>> cb);

    @Headers("Accept: application/json")
    @GET("/item/{itemId}.json?print=pretty")
    void getTopStory(@Path("itemId") Long itemId,
                     Callback<TopStory> cb);

}