package com.bidyut.app.fastpx.service;

import com.bidyut.app.fastpx.BuildConfig;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

public interface PxService {
    public static final String API_URL = "https://api.500px.com/v1";
    public static final String CONSUMER_KEY = BuildConfig.PX_API_KEY;

    @GET("/photos/search?image_size=4&consumer_key=" + CONSUMER_KEY)
    void searchPhotos(@Query("term") String query, Callback<SearchResults> callback);
}
