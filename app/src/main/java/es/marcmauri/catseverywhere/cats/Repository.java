package es.marcmauri.catseverywhere.cats;

import es.marcmauri.catseverywhere.http.apimodel.thecat.CatBreedApi;
import io.reactivex.Observable;

public interface Repository {

    Observable<CatBreedApi> getCatBreedFromNetwork();
    Observable<CatBreedApi> getCatBreedFromCache();
    Observable<CatBreedApi> getCatBreedData();

    Observable<String> getCatBreedImageUrlFromNetwork();
    Observable<String> getCatBreedImageUrlFromCache();
    Observable<String> getCatBreedImageUrl();
}
