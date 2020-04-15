package com.example.catseverywhere.root;

import com.example.catseverywhere.login.LoginActivity;
import com.example.catseverywhere.login.LoginModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, LoginModule.class})
public interface ApplicationComponent {

    void inject(LoginActivity target);
}
