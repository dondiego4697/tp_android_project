package com.koala.infinitum.android_project.loginFragments;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
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
import com.koala.infinitum.android_project.support.SharedPrefApi;

import retrofit2.Response;

public class RegisterFragment extends Fragment {

    private EditText login_text;
    private EditText password_text;
    private EditText password_verif_text;
    private Button register_btn;
    private ProgressBar progressBar;

    private TextInputLayout login_text_layout;
    private TextInputLayout password_text_layout;
    private TextInputLayout password_text_verif_layout;


    public static ClientCallback<ResponseOneObject<UserValidation>> authHandler;
  
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        login_text = (EditText) view.findViewById(R.id.login);
        password_text = (EditText) view.findViewById(R.id.password);
        password_verif_text = (EditText) view.findViewById(R.id.password_verification);
        register_btn = (Button) view.findViewById(R.id.register_btn);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar_register);

        login_text_layout = (TextInputLayout) view.findViewById(R.id.input_layout_login);
        password_text_layout = (TextInputLayout) view.findViewById(R.id.input_layout_password);
        password_text_verif_layout = (TextInputLayout) view.findViewById(R.id.input_layout_password_verification);

        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!submitForm()){
                    return;
                }

                if (!password_text.getText().toString().equals(password_verif_text.getText().toString())) {  //// TODO: 31.10.17 add TextWatcher to verificate password
                    //progressBar.setVisibility(View.GONE);
                    //Toast.makeText(getActivity(), "Пароли не совпадают!", Toast.LENGTH_LONG).show();
                    password_text_verif_layout.setError(getString(R.string.passwords_not_match));
                    return;
                } else {
                    password_text_verif_layout.setErrorEnabled(false);
                }

                progressBar.setVisibility(View.VISIBLE);
              
                stop();
                authHandler = new LoginService().register(
                        login_text.getText().toString(),
                        password_text.getText().toString(),
                        new ClientCallback<ResponseOneObject<UserValidation>>() {
                            @Override
                            public void onSuccess(Response<ResponseOneObject<UserValidation>> response) {
                                SharedPrefApi.setSharedUserInfo(getActivity().getApplicationContext(),
                                        response.body().getData().getId(),
                                        login_text.getText().toString(), password_text.getText().toString());
                                progressBar.setVisibility(View.GONE);
                                Intent intent = new Intent(getActivity(), MainActivity.class);
                                intent.putExtra("token", response.body().getData().getToken());
                                startActivity(intent);
                            }

                            @Override
                            public void onError(String err) {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(getActivity(), err, Toast.LENGTH_LONG).show();
                            }
                        });
            }
        });

        return view;


    }

    @Override
    public void onStop() {
        super.onStop();
        stop();
    }

    public void stop() {
        authHandler = null;// отписываемся
    }

    private Boolean submitForm(){
        if (!validateLogin()){
            return false;
        } else if(!validatePassword()){
            return false;
        }

        return true;
    }

    private Boolean validateLogin(){
        if (login_text.getText().toString().trim().isEmpty()) {
            login_text_layout.setError(getString(R.string.empty_login));
            return false;
        } else {
            login_text_layout.setErrorEnabled(false);
        }
        return true;
    }

    private Boolean validatePassword(){
        if (password_text.getText().toString().trim().isEmpty()) {
            password_text_layout.setError(getString(R.string.empty_password));
            return false;
        } else {
            password_text_layout.setErrorEnabled(false);
        }
        return true;
    }

}
