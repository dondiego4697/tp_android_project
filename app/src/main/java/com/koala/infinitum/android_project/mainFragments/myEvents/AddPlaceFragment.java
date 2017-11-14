package com.koala.infinitum.android_project.mainFragments.myEvents;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.koala.infinitum.android_project.R;
import com.koala.infinitum.android_project.httpApi.interfaces.ClientCallback;
import com.koala.infinitum.android_project.httpApi.models.Category;
import com.koala.infinitum.android_project.httpApi.models.Place;
import com.koala.infinitum.android_project.httpApi.models.PlaceBody;
import com.koala.infinitum.android_project.httpApi.models.Responses;
import com.koala.infinitum.android_project.httpApi.services.CategoryService;
import com.koala.infinitum.android_project.httpApi.services.PlaceService;
import com.koala.infinitum.android_project.mapSearch.MapSearchActivity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Response;

/**
 * Created by andrey on 14.11.17.
 */

public class AddPlaceFragment extends Fragment {


    private EditText title;
    private EditText description;
    private Button create_btn;
    private Button cancel_btn;
    private EditText category;
    private ProgressBar progressBar;


    public static Integer userId;///hardcode

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_addplace, container, false);
        title = (EditText) view.findViewById(R.id.tittle);
        category = (EditText) view.findViewById(R.id.category);
        description = (EditText) view.findViewById(R.id.description);
        create_btn = (Button) view.findViewById(R.id.create_btn);
        cancel_btn = (Button) view.findViewById(R.id.cancel_btn);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);

        System.out.println(userId);
        create_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                new CategoryService().getCategories(
                        new ClientCallback<Responses<Category>>() {

                            @Override
                            public void onSuccess(Response<Responses<Category>> response) {
                                Log.d("success", "success get-all category");
                                boolean flagContains = false;
                                int categoryId=0;
                                List<Category> list = response.body().getData();

                                for (int i = 0; i < list.size(); i++) {
                                    String slug = list.get(i).getSlug();
                                    Log.d("slug",slug);
                                    Log.d("Edit category slug",category.getText().toString());
                                    if (slug.equals(category.getText().toString())) {
                                        Log.d("flagContains","true");
                                        flagContains = true;
                                        categoryId=list.get(i).getId();
                                        break;
                                    }
                                }

                                if (!flagContains) {
                                    Toast.makeText(getActivity(), "нет такой категории", Toast.LENGTH_LONG).show();
                                    return;
                                }
                                Log.d("success","find slug category");
                                PlaceBody placeBody = new PlaceBody();
                                placeBody.setCategoryId(categoryId);
                                placeBody.setCreatorId(userId);
                                placeBody.setDescription(description.getText().toString());
                                List<Double> listPoint = new ArrayList<>();
                                listPoint.add(getArguments().getDouble(MapSearchActivity.latitude));
                                listPoint.add(getArguments().getDouble(MapSearchActivity.longitude));
                                placeBody.setPoint(listPoint);
                                Date date = new Date();
                                placeBody.setTimeStart(date.toString());
                                placeBody.setTitle(title.getText().toString());
                                Log.d("status request","prepare request finish");
                                new PlaceService().createPlace(placeBody,
                                        new ClientCallback<Responses<Place>>() {
                                            @Override
                                            public void onSuccess(Response<Responses<Place>> response) {
                                                Log.d("status request","success");
                                                progressBar.setVisibility(View.GONE);
                                                Toast.makeText(getActivity(), "событие создано", Toast.LENGTH_LONG).show();//hardcode
                                                ((MapSearchActivity) getActivity()).showFragmentMap();
                                            }


                                            @Override
                                            public void onError(String err) {
                                                Log.d("status request","error");
                                                progressBar.setVisibility(View.GONE);
                                                Toast.makeText(getActivity(), err, Toast.LENGTH_LONG).show();
                                            }
                                        });
                            }

                            @Override
                            public void onError(String err) {
                                Toast.makeText(getActivity(), "нет такой категории!", Toast.LENGTH_LONG).show();
                            }
                        });

            }
        });
        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MapSearchActivity) getActivity()).showFragmentMap();
            }
        });

        return view;
    }
}
