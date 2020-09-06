package com.rizal.onekmievent.model;

import android.content.Context;

import com.rizal.onekmievent.client.MobileService;
import com.rizal.onekmievent.utils.Static;

public class ApiUtils {
    public static String API = Static.BASE_URL;

    public static MobileService MobileService(Context context){
        return RetrofitClient.getClient(context, API).create(MobileService.class);
    }
}
