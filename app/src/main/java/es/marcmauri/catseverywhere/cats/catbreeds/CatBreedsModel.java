package es.marcmauri.catseverywhere.cats.catbreeds;

import es.marcmauri.catseverywhere.cats.CatBreedViewModel;
import es.marcmauri.catseverywhere.cats.CountryViewModel;
import es.marcmauri.catseverywhere.http.apimodel.thecat.CatBreedApi;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;

public class CatBreedsModel implements CatBreedsMVP.Model {

    private CatBreedsRepository catBreedsRepository;

    public CatBreedsModel(CatBreedsRepository catBreedsRepository) {
        this.catBreedsRepository = catBreedsRepository;
    }

    @Override
    public Observable<CatBreedViewModel> getCatBreedsData() {

        return Observable.zip(catBreedsRepository.getCatBreedData(), catBreedsRepository.getCatBreedImageUrl(),
                new BiFunction<CatBreedApi, String, CatBreedViewModel>() {
                    @Override
                    public CatBreedViewModel apply(CatBreedApi catBreedApi, String imageUrl) {
                        return new CatBreedViewModel(catBreedApi.getId(), catBreedApi.getName(),
                                catBreedApi.getDescription(), catBreedApi.getCountryCode(),
                                catBreedApi.getTemperament(), catBreedApi.getWikipediaUrl(),
                                imageUrl);
                    }
                });
    }

    @Override
    public Observable<CountryViewModel> getCountries() {
        return catBreedsRepository.getCatBreedCountriesData();
    }
}
