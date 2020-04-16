package es.marcmauri.catseverywhere.catbreeds;

import es.marcmauri.catseverywhere.http.apimodel.thecat.CatBreedApi;
import io.reactivex.Observable;

public interface CatBreedsRepository {

    Observable<CatBreedApi> getCatBreedFromNetwork();
    Observable<CatBreedApi> getCatBreedFromCache();
    Observable<CatBreedApi> getCatBreedData();

    Observable<String> getCatBreedImageUrlFromNetwork();
    Observable<String> getCatBreedImageUrlFromCache();
    Observable<String> getCatBreedImageUrl();
}
