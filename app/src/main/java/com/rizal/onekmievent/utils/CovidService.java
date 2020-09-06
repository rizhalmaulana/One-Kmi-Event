package com.rizal.onekmievent.utils;

import com.rizal.onekmievent.model.DataIndonesia;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CovidService {
    @GET("indonesia")
    Call<List<DataIndonesia>> getKasus();
}
