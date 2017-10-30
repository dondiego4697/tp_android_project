package com.koala.infinitum.android_project;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.koala.infinitum.android_project.loginFragments.LoginFragment;
import com.koala.infinitum.android_project.loginFragments.RegisterFragment;

public class LoginActivity extends AppCompatActivity {

    private Fragment loginFragment;
    private Fragment registerFragment;
    private FragmentTransaction transaction;

    private EditText login_text;
    private EditText password_text;
    private EditText password_verif_text;

    //private Button login_btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginFragment = new LoginFragment();
        registerFragment = new RegisterFragment();
        transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.login_activity, loginFragment);
        transaction.commit();
        //login_btn = (Button) findViewById(R.id.login_btn);
    }

    public void switchToRegister(View v){
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.login_activity, registerFragment);
        transaction.commit();

    }

    public void switchToLogin(View v){
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.login_activity, loginFragment);
        transaction.commit();
    }


}
