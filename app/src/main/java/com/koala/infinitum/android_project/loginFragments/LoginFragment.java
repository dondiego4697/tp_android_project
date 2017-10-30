package com.koala.infinitum.android_project.loginFragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.koala.infinitum.android_project.MainActivity;
import com.koala.infinitum.android_project.R;
import com.koala.infinitum.android_project.httpApi.interfaces.ClientCallback;
import com.koala.infinitum.android_project.httpApi.models.Response;
import com.koala.infinitum.android_project.httpApi.models.ResponseOneObject;
import com.koala.infinitum.android_project.httpApi.models.UserValidation;
import com.koala.infinitum.android_project.httpApi.services.LoginService;

public class LoginFragment extends Fragment{

    private EditText login_text;
    private EditText password_text;
    private Button login_btn;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        login_text = (EditText) view.findViewById(R.id.login);
        password_text = (EditText) view.findViewById(R.id.password);
        login_btn = (Button) view.findViewById(R.id.login_btn);

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new LoginService().login(
                        login_text.getText().toString(),
                        password_text.getText().toString(),
                        new ClientCallback<ResponseOneObject<UserValidation>>() {
                    @Override
                    public void onSuccess(retrofit2.Response<ResponseOneObject<UserValidation>> response) {
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        intent.putExtra("token", response.body().getData().getToken());
                        startActivity(intent);
                    }

                    @Override
                    public void onError(String err) {
                        Toast.makeText(getActivity(), err, Toast.LENGTH_LONG).show();
                        Log.d("login_error", err);
                    }
                });
            }
        });

        return view;
    }
}
