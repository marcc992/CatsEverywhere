package es.marcmauri.catseverywhere.cats.catbreeds;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import es.marcmauri.catseverywhere.http.TheCatApiService;

@Module
public class CatBreedsModule {

    @Provides
    public CatBreedsMVP.Presenter provideCatBreedsPresenter(CatBreedsMVP.Model model) {
        return new CatBreedsPresenter(model);
    }

    @Provides
    public CatBreedsMVP.Model provideCatBreedsModel(CatBreedsRepository catBreedsRepository) {
        return new CatBreedsModel(catBreedsRepository);
    }

    @Singleton
    @Provides
    public CatBreedsRepository provideCatBreedsRepository(TheCatApiService theCatApiService) {
        return new TheCatApiCatBreedsRepository(theCatApiService);
    }
}
