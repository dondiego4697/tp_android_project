package com.koala.infinitum.android_project.support;


import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefApi {

    private final static String LOGIN = "login";
    private final static String PASSWD = "password";
    private final static String ID = "id";

    private final static String SHARED_CONST = "MyPref";

    private final static Integer USER_ID_INVALID = -1;


    public static String getSharedLogin(Context appContext){
        SharedPreferences prefs = appContext.getSharedPreferences(SHARED_CONST, Context.MODE_PRIVATE);
        return prefs.getString(LOGIN, "");
    }

    public static String getSharedPassword(Context appContext){
        SharedPreferences prefs = appContext.getSharedPreferences(SHARED_CONST, Context.MODE_PRIVATE);
        return prefs.getString(PASSWD, "");
    }

    public static Integer getSharedId(Context appContext){
        SharedPreferences prefs = appContext.getSharedPreferences(SHARED_CONST, Context.MODE_PRIVATE);
        return prefs.getInt(ID, USER_ID_INVALID);
    }

    public static void setSharedUserInfo(Context appContext,
                                      Integer id,
                                      String login, String password){
        SharedPreferences.Editor editor =  appContext.getSharedPreferences(SHARED_CONST, Context.MODE_PRIVATE).edit();
        editor.putInt(ID, id);
        editor.putString(LOGIN, login);
        editor.putString(PASSWD, password);
        editor.apply();
    }

    public static void removeSharedUserInfo(Context appContext){
        SharedPreferences.Editor editor =  appContext.getSharedPreferences(SHARED_CONST, Context.MODE_PRIVATE).edit();
        editor.remove(LOGIN);
        editor.remove(PASSWD);
        editor.remove(ID);
        editor.apply();
    }
}
