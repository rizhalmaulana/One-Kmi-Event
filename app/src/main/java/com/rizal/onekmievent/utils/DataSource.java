package com.rizal.onekmievent.utils;

import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.rizal.onekmievent.model.NewsResponses;

import static com.rizal.onekmievent.utils.ApiClient.BASE_URL;


public class DataSource {
    private final static String API_KEY = "fa5dd2a41a2945a1a309f5d96c8baa17";
    public static final String URL_NEWS_NOW = BASE_URL + "/v2/top-headlines?country=id&apiKey={apiKey}";

    public void getNews(String movieEndpoint, final NewsSourceCallback callback) {
        AndroidNetworking.get(movieEndpoint)
                .addPathParameter("apiKey", API_KEY)
                .setTag(DataSource.class)
                .setPriority(Priority.IMMEDIATE)
                .build()
                .getAsObject(NewsResponses.class, new ParsedRequestListener<NewsResponses>() {
                    @Override
                    public void onResponse(NewsResponses response) {
                        callback.onSuccess(response);
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("ERROR", "onError: ", anError);
                    }
                });
    }
}
