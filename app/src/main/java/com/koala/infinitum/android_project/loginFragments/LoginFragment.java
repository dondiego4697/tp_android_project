package com.koala.infinitum.android_project.loginFragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.koala.infinitum.android_project.R;
import com.koala.infinitum.android_project.httpApi.services.LoginService;

public class LoginFragment extends Fragment{

    private EditText login_text;
    private EditText password_text;
    private Button login_btn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        login_text = (EditText) view.findViewById(R.id.login);
        password_text = (EditText) view.findViewById(R.id.password);
        login_btn = (Button) view.findViewById(R.id.login_btn);

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new LoginService().login();
            }
        });

        return view;
    }
}
