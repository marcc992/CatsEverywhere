package es.marcmauri.catseverywhere.cats;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import es.marcmauri.catseverywhere.http.TheCatApiService;

@Module
public class CatsModule {

    @Provides
    public CatsMVP.Presenter provideCatsPresenter(CatsMVP.Model model) {
        return new CatsPresenter(model);
    }

    @Provides
    public CatsMVP.Model provideCatsModel(Repository repository) {
        return new CatsModel(repository);
    }

    @Singleton
    @Provides
    public Repository provideCatsRepository(TheCatApiService theCatApiService) {
        return new CatsRepository(theCatApiService);
    }
}
