package com.koala.infinitum.android_project;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.koala.infinitum.android_project.loginFragments.LoginFragment;

public class LoginActivity extends AppCompatActivity{

    private Fragment loginFragment;
    private Fragment registerFragment;
    private FragmentTransaction transaction;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginFragment = new LoginFragment();
    }


}
