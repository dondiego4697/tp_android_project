package com.koala.infinitum.android_project.support;


import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefApi {

    public final static String LOGIN = "login";
    public final static String PASSWD = "password";
    public final static String ID = "id";

    public final static Integer USER_ID_INVALID = -1;


    public static String getSharedLogin(Context appContext){
        SharedPreferences prefs = appContext.getSharedPreferences("MyPref", 0);
        return prefs.getString(LOGIN, "");
    }

    public static String getSharedPassword(Context appContext){
        SharedPreferences prefs = appContext.getSharedPreferences("MyPref", 0);
        return prefs.getString(PASSWD, "");
    }

    public static Integer getSharedId(Context appContext){
        SharedPreferences prefs = appContext.getSharedPreferences("MyPref", 0);
        return prefs.getInt(ID, USER_ID_INVALID);
    }

    public static void setSharedUserInfo(Context appContext,
                                      Integer id,
                                      String login, String password){
        SharedPreferences.Editor editor =  appContext.getSharedPreferences("MyPref", 0).edit();
        editor.putInt(ID, id);
        editor.putString(LOGIN, login);
        editor.putString(PASSWD, password);
        editor.apply();
    }

    public static void removeSharedUserInfo(Context appContext){
        SharedPreferences.Editor editor =  appContext.getSharedPreferences("MyPref", 0).edit();
        editor.remove(LOGIN);
        editor.remove(PASSWD);
        editor.remove(ID);
        editor.apply();
    }
}
