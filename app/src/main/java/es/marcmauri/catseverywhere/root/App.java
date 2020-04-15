package es.marcmauri.catseverywhere.root;

import android.app.Application;

import es.marcmauri.catseverywhere.cats.CatsModule;
import es.marcmauri.catseverywhere.login.LoginModule;

public class App extends Application {

    private ApplicationComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        component = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .loginModule(new LoginModule())
                .catsModule(new CatsModule())
                .build();
    }

    public ApplicationComponent getComponent() {
        return component;
    }
}
