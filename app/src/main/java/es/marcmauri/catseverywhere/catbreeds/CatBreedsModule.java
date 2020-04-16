package es.marcmauri.catseverywhere.catbreeds;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import es.marcmauri.catseverywhere.http.TheCatApiService;

@Module
public class CatBreedsModule {

    @Provides
    public CatBreedsMVP.Presenter provideCatsPresenter(CatBreedsMVP.Model model) {
        return new CatBreedsPresenter(model);
    }

    @Provides
    public CatBreedsMVP.Model provideCatsModel(CatBreedsRepository catBreedsRepository) {
        return new CatBreedsModel(catBreedsRepository);
    }

    @Singleton
    @Provides
    public CatBreedsRepository provideCatsRepository(TheCatApiService theCatApiService) {
        return new TheCatApiCatBreedsRepository(theCatApiService);
    }
}
