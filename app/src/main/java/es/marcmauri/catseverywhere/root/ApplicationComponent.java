package es.marcmauri.catseverywhere.root;

import es.marcmauri.catseverywhere.cats.CatsActivity;
import es.marcmauri.catseverywhere.cats.CatsModule;
import es.marcmauri.catseverywhere.login.LoginActivity;
import es.marcmauri.catseverywhere.login.LoginModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        ApplicationModule.class,
        LoginModule.class,
        CatsModule.class
})
public interface ApplicationComponent {

    void inject(LoginActivity target);

    void inject(CatsActivity target);
}
