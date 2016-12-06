package com.etiennelawlor.hackernews.network;


import com.etiennelawlor.hackernews.network.models.TopStory;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

public interface HackerNewsService {

    String BASE_URL = "https://hacker-news.firebaseio.com/v0/";

//    @GET("/topstories.json")
//    void getTopStoryIds(Callback<List<Long>> cb);

    @GET("topstories.json")
    Observable<List<Long>> getTopStoryIds();

//    @GET("/item/{itemId}.json")
//    void getTopStory(@Path("itemId") Long itemId,
//                     Callback<TopStory> cb);

    @GET("item/{itemId}.json")
    Observable<TopStory> getTopStory(@Path("itemId") Long itemId);

}