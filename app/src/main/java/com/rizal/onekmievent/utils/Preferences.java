package com.rizal.onekmievent.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.rizal.onekmievent.model.Peserta;

public class Preferences {
    public static Peserta getPeserta(Context context){
        try{
            String json = getString(context, Static.USER_DATA);
            return new Gson().fromJson(json, Peserta.class);
        }catch (Exception e){
            return null;
        }
    }

    public static String getToken(Context context){
        return getString(context, Static.TOKEN);
    }

    public static void setPeserta(Context context, Peserta peserta){
        putString(context, Static.USER_DATA, new Gson().toJson(peserta));
    }

    private static SharedPreferences.Editor getEditor(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(Static.MyPref, Context.MODE_PRIVATE);
        return preferences.edit();
    }

    public static void putString(Context context, String key, String value){
        SharedPreferences.Editor editor = getEditor(context);
        editor.putString(key, value).commit();
    }

    public static String getString(Context context, String key){
        SharedPreferences preferences = context.getSharedPreferences(Static.MyPref, Context.MODE_PRIVATE);
        return preferences.getString(key, "");
    }

    public static void putBoolean(Context context, String key, boolean value){
        SharedPreferences.Editor editor = getEditor(context);
        editor.putBoolean(key, value).commit();
    }

    public static boolean getBoolean(Context context, String key){
        SharedPreferences preferences = context.getSharedPreferences(Static.MyPref, Context.MODE_PRIVATE);
        return preferences.getBoolean(key, false);
    }

    public static void setLoginFlag(Context context, boolean flag){
        putBoolean(context, Static.LOGIN_KEY, flag);
    }

    public static boolean getLoginFlag(Context context){
        return getBoolean(context, Static.LOGIN_KEY);
    }

    public static void setLoginType(Context context, ConstanKey flag){
        putString(context, Static.LOGIN_TYPE, flag.toString());
    }

    public static ConstanKey getLoginType(Context context){
        String key = getString(context, Static.LOGIN_TYPE);
        if(key.equals(ConstanKey.FLAG_PERSONE.toString())){
            return ConstanKey.FLAG_PERSONE;
        }else if(key.equals(ConstanKey.FLAG_INSTITUSI.toString())){
            return ConstanKey.FLAG_INSTITUSI;
        }else if(key.equals(ConstanKey.FLAG_PENGURUS.toString())){
            return ConstanKey.FLAG_PENGURUS;
        }else{
            return ConstanKey.FLAG_PERSONE;
        }
    }
}
