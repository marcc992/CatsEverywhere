package es.marcmauri.catseverywhere.cats;

import io.reactivex.Observable;

public interface Repository {

    Observable<CatViewModel> getCatBreedsFromNetwork();
    Observable<CatViewModel> getCatBreedsFromCache();
    Observable<CatViewModel> getCatBreedsData();
}
