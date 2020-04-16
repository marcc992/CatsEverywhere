package es.marcmauri.catseverywhere.root;

import es.marcmauri.catseverywhere.catbreeds.CatBreedsActivity;
import es.marcmauri.catseverywhere.catbreeds.CatBreedsModule;
import es.marcmauri.catseverywhere.http.TheCatApiModule;
import es.marcmauri.catseverywhere.login.LoginActivity;
import es.marcmauri.catseverywhere.login.LoginModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        ApplicationModule.class,
        LoginModule.class,
        CatBreedsModule.class,
        TheCatApiModule.class
})
public interface ApplicationComponent {

    void inject(LoginActivity target);

    void inject(CatBreedsActivity target);
}
