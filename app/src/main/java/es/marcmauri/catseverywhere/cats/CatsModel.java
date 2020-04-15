package es.marcmauri.catseverywhere.cats;

import es.marcmauri.catseverywhere.http.apimodel.thecat.CatBreedApi;
import io.reactivex.Observable;
import io.reactivex.functions.BiFunction;

public class CatsModel implements CatsMVP.Model {

    private Repository repository;

    public CatsModel(Repository repository) {
        this.repository = repository;
    }

    @Override
    public Observable<CatViewModel> result() {

        return Observable.zip(repository.getCatBreedData(), repository.getCatBreedImageUrl(), new BiFunction<CatBreedApi, String, CatViewModel>() {
            @Override
            public CatViewModel apply(CatBreedApi catBreedApi, String imageUrl) {
                return new CatViewModel(catBreedApi.getName(), catBreedApi.getDescription(), imageUrl);
            }
        });
    }
}
