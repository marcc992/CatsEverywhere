package es.marcmauri.catseverywhere.cats.catbreeddetails;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class CatBreedDetailsModule {

    @Provides
    public CatBreedDetailsMVP.Presenter provideCatBreedDetailsPresenter(CatBreedDetailsMVP.Model model) {
        return new CatBreedDetailsPresenter(model);
    }

    @Provides
    public CatBreedDetailsMVP.Model provideCatBreedDetailsModel(CatBreedDetailsRepository repository) {
        return new CatBreedDetailsModel(repository);
    }

    @Singleton
    @Provides
    public CatBreedDetailsRepository provideCatBreedDetailsRepository() {
        return new MemoryCatBreedDetailsRepository();
    }
}
