package es.marcmauri.catseverywhere.cats.catbreeds;

import es.marcmauri.catseverywhere.http.apimodel.thecat.CatBreedApi;
import io.reactivex.Observable;

public interface CatBreedsRepository {

    Observable<CatBreedApi> getCatBreedFromNetwork(int pageNumber);
    Observable<CatBreedApi> getCatBreedFromCache(int pageNumber);
    Observable<CatBreedApi> getCatBreedData(int pageNumber);

    Observable<String> getCatBreedImageUrlFromNetwork(int pageNumber);
    Observable<String> getCatBreedImageUrlFromCache(int pageNumber);
    Observable<String> getCatBreedImageUrl(int pageNumber);
}
