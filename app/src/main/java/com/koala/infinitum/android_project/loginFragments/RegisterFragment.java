package com.koala.infinitum.android_project.loginFragments;


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
import com.koala.infinitum.android_project.mainFragments.myEvents.AddPlaceFragment;

import retrofit2.Response;

public class RegisterFragment extends Fragment {

    private final static String LOGIN = "login";
    private final static String PASSWD = "password";

    private EditText login_text;
    private EditText password_text;
    private EditText password_verif_text;
    private Button register_btn;
    private ProgressBar progressBar;

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

        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);

                if (!password_text.getText().toString().equals(password_verif_text.getText().toString())) {  //// TODO: 31.10.17 add TextWatcher to verificate password
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), "Пароли не совпадают!", Toast.LENGTH_LONG).show();
                    return;
                }
              
                stop();
                authHandler = new LoginService().register(
                        login_text.getText().toString(),
                        password_text.getText().toString(),
                        new ClientCallback<ResponseOneObject<UserValidation>>() {
                            @Override
                            public void onSuccess(Response<ResponseOneObject<UserValidation>> response) {
                                SharedPreferences.Editor editor = getActivity().getApplicationContext().getSharedPreferences("MyPref", 0).edit();
                                AddPlaceFragment.userId=response.body().getData().getId();//hardcode
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
}
