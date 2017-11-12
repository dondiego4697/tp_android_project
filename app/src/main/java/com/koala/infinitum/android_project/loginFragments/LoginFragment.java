package com.koala.infinitum.android_project.loginFragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.koala.infinitum.android_project.MainActivity;
import com.koala.infinitum.android_project.R;
import com.koala.infinitum.android_project.httpApi.interfaces.ClientCallback;
import com.koala.infinitum.android_project.httpApi.models.ResponseOneObject;
import com.koala.infinitum.android_project.httpApi.models.UserValidation;
import com.koala.infinitum.android_project.httpApi.services.LoginService;

import static android.content.Context.MODE_PRIVATE;

public class LoginFragment extends Fragment {

    private final static String LOGIN = "login";
    private final static String PASSWD = "password";

    private EditText login_text;
    private EditText password_text;
    private Button login_btn;
    private ProgressBar progressBar;

    SharedPreferences prefs;


    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);


        login_text = (EditText) view.findViewById(R.id.login);
        password_text = (EditText) view.findViewById(R.id.password);
        login_btn = (Button) view.findViewById(R.id.login_btn);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                new LoginService().login(
                        login_text.getText().toString(),
                        password_text.getText().toString(),
                        new ClientCallback<ResponseOneObject<UserValidation>>() {
                            @Override
                            public void onSuccess(retrofit2.Response<ResponseOneObject<UserValidation>> response) {
                                SharedPreferences.Editor editor =  getActivity().getApplicationContext().getSharedPreferences("MyPref", 0).edit();
                                editor.putString(LOGIN, login_text.getText().toString());
                                editor.putString(PASSWD, password_text.getText().toString());
                                editor.apply();
                                progressBar.setVisibility(View.GONE);
                                Intent intent = new Intent(getActivity(), MainActivity.class);
                                intent.putExtra("token", response.body().getData().getToken());
                                intent.putExtra(LOGIN, login_text.getText().toString());
                                startActivity(intent);
                            }

                            @Override
                            public void onError(String err) {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(getActivity(), err, Toast.LENGTH_LONG).show();
                            }
                        }, getContext());
            }
        });

        return view;
    }
}
