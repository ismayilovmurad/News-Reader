package com.martiandeveloper.newsreader.tools;

import com.martiandeveloper.newsreader.model.Main;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Service {
    @GET("top-headlines")
    Call<Main> getNews(@Query("language") String language, @Query("sortBy") String sortBy, @Query("apiKey") String apiKey, @Query("page") int page);

    @GET("everything")
    Call<Main> getSearchNews(@Query("q") String q, @Query("sortBy") String sortBy, @Query("apiKey") String apiKey, @Query("page") int page);

    @GET("top-headlines")
    Call<Main> getCategoryNews(@Query("category") String category, @Query("sortBy") String sortBy, @Query("apiKey") String apiKey, @Query("page") int page);
}
