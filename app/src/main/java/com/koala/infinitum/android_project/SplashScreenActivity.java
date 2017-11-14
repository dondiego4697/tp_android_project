package com.koala.infinitum.android_project;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.koala.infinitum.android_project.httpApi.interfaces.ClientCallback;
import com.koala.infinitum.android_project.httpApi.models.ResponseOneObject;
import com.koala.infinitum.android_project.httpApi.models.UserValidation;
import com.koala.infinitum.android_project.httpApi.services.LoginService;
import com.koala.infinitum.android_project.support.SharedPrefApi;

public class SplashScreenActivity extends AppCompatActivity {

    private String login;
    private String password;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        login = SharedPrefApi.getSharedLogin(getApplicationContext());
        password = SharedPrefApi.getSharedPassword(getApplicationContext());

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
