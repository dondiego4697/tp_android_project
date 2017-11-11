package com.koala.infinitum.android_project;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.koala.infinitum.android_project.httpApi.interfaces.ClientCallback;
import com.koala.infinitum.android_project.httpApi.models.ResponseOneObject;
import com.koala.infinitum.android_project.httpApi.models.UserValidation;
import com.koala.infinitum.android_project.httpApi.services.LoginService;

public class SplashScreenActivity extends AppCompatActivity {

    private final static String LOGIN = "login";
    private final static String PASSWD = "password";

    private String login;
    private String password;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences prefs = getApplicationContext().getSharedPreferences("MyPref", 0);
        login = prefs.getString(LOGIN, "");
        password = prefs.getString(PASSWD, "");

        if (!login.equals("") && !password.equals("")){
            new LoginService().login(
                    login,
                    password,
                    new ClientCallback<ResponseOneObject<UserValidation>>() {
                        @Override
                        public void onSuccess(retrofit2.Response<ResponseOneObject<UserValidation>> response) {
                            Intent intent = new Intent(getBaseContext(), MainActivity.class);
                            intent.putExtra("token", response.body().getData().getToken());
                            startActivity(intent);
                        }

                        @Override
                        public void onError(String err) {
                            Toast.makeText(getBaseContext(), err, Toast.LENGTH_LONG).show();
                        }
                    }, getBaseContext());
        } else {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }
}
