package es.marcmauri.catseverywhere.cats.catbreeds;

import es.marcmauri.catseverywhere.cats.CountryViewModel;
import es.marcmauri.catseverywhere.http.apimodel.thecat.CatBreedApi;
import io.reactivex.Observable;

public interface CatBreedsRepository {

    Observable<CountryViewModel> getCatBreedCountriesFromNetwork();
    Observable<CountryViewModel> getCatBreedCountriesFromCache();
    Observable<CountryViewModel> getCatBreedCountriesData();

    Observable<CatBreedApi> getCatBreedFromNetwork();
    Observable<CatBreedApi> getCatBreedFromCache();
    Observable<CatBreedApi> getCatBreedData();

    Observable<String> getCatBreedImageUrlFromNetwork();
    Observable<String> getCatBreedImageUrlFromCache();
    Observable<String> getCatBreedImageUrl();
}
