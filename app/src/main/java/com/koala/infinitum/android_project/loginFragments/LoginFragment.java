package com.koala.infinitum.android_project.loginFragments;


import android.content.Intent;
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

public class LoginFragment extends Fragment {

    private EditText login_text;
    private EditText password_text;
    private ProgressBar progressBar;

    private TextInputLayout login_text_layout;
    private TextInputLayout password_text_layout;


    public static ClientCallback<ResponseOneObject<UserValidation>> authHandler;//сделать singleton через фреймворк

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);


        login_text = (EditText) view.findViewById(R.id.login);
        password_text = (EditText) view.findViewById(R.id.password);
        Button login_btn = (Button) view.findViewById(R.id.login_btn);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        login_text_layout = (TextInputLayout) view.findViewById(R.id.input_layout_login);
        password_text_layout = (TextInputLayout) view.findViewById(R.id.input_layout_password);
      /*  PlaceBody placeBody= new PlaceBody();
        placeBody.setCategoryId(2);
        placeBody.setCreatorId(2);
        placeBody.setDescription("pll");
        List<Double> list = new ArrayList<>();
        list.add(1.4);
        list.add(1.2);
        placeBody.setPoint(list);
        placeBody.setTimeStart("2007-10-04T23:08:10.0");
        placeBody.setTitle("llo");
        ClientCallback<Responses<Place>> place = new PlaceService().getAll(3,0,true,"cinema", new ClientCallback<Responses<Place>>() {
            @Override
            public void onSuccess(Response<Responses<Place>> response) {
                Integer id = response.body().getData().get(0).getId();
                login_text.setText(id.toString());
            }


            @Override
            public void onError(String err) {
                login_text.setText(err);
            }
        }); *////тестил
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!submitForm()){
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);

                stop();
                authHandler=new LoginService().login( //подписываемся
                        login_text.getText().toString(),
                        password_text.getText().toString(),
                        new ClientCallback<ResponseOneObject<UserValidation>>() {
                            @Override
                            public void onSuccess(retrofit2.Response<ResponseOneObject<UserValidation>> response) {
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
                        }, getContext());
            }
        });

        return view;
    }

    @Override
    public void onStop() {
        super.onStop();
        stop();
    }

    public void  stop(){
        authHandler=null;// отписываемся
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
