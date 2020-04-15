package es.marcmauri.catseverywhere.cats;

import dagger.Module;
import dagger.Provides;

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

    @Provides
    public Repository provideCatsRepository() {
        // todo: el parametro traera el catsApiService
        return new CatsRepository();
    }
}
