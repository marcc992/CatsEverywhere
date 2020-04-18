package es.marcmauri.catseverywhere.root;

import es.marcmauri.catseverywhere.cats.catbreeddetails.CatBreedDetailsActivity;
import es.marcmauri.catseverywhere.cats.catbreeddetails.CatBreedDetailsModule;
import es.marcmauri.catseverywhere.cats.catbreeds.CatBreedsActivity;
import es.marcmauri.catseverywhere.cats.catbreeds.CatBreedsModule;
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
        CatBreedDetailsModule.class,
        TheCatApiModule.class
})
public interface ApplicationComponent {

    void inject(LoginActivity target);

    void inject(CatBreedsActivity target);

    void inject(CatBreedDetailsActivity target);
}
