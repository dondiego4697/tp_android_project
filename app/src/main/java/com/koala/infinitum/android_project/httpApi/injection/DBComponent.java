package com.koala.infinitum.android_project.httpApi.injection;

import com.koala.infinitum.android_project.httpApi.services.LoginService;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by andrey on 12.11.17.
 */
@Component(modules = {ApiModule.class, ExecutorsModule.class})
@Singleton
public interface DBComponent {
  //  void inject(CategoryService categoryService);

    void inject(LoginService loginService);

   //void inject(SplashScreenActivity splashScreenActivity);
    // void inject(PlaceService placeService);

   // void inject(SubscribeService subscribeService);

//    void inject(UserService userService);

 //   void inject(LoginActivity loginActivity);

//    void inject(Api api);

//    void inject (MainActivity mainActivity);
}
