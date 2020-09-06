package com.rizal.onekmievent.client;

import com.rizal.onekmievent.model.Response;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface MobileService {
    @FormUrlEncoded
    @POST("loginKaryawan.php")
    Call<Response> login(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("last-seen")
    Call<Response> lastSeen(@FieldMap Map<String, String> map);

}
