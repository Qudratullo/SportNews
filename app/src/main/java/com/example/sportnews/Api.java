package com.example.sportnews;

import com.example.sportnews.Classes.AllNews;
import com.example.sportnews.Classes.News;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {
    @GET(Constants.ALL_NEWS)
    Call<AllNews> getAllNews(@Query("category") String category);

    @GET(Constants.NEWS)
    Call<News> getNews(@Query("article") String article);
}
