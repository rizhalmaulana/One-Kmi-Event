package com.rizal.onekmievent.utils;

import com.rizal.onekmievent.model.NewsResponses;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("v2/top-headlines?country=id")
    Call<NewsResponses> getNewsNow(@Query("apiKey") String apiKey);
}
