package es.marcmauri.catseverywhere.catbreeds;

import es.marcmauri.catseverywhere.http.apimodel.thecat.CatBreedApi;
import io.reactivex.Observable;
import io.reactivex.functions.BiFunction;

public class CatBreedsModel implements CatBreedsMVP.Model {

    private CatBreedsRepository catBreedsRepository;

    public CatBreedsModel(CatBreedsRepository catBreedsRepository) {
        this.catBreedsRepository = catBreedsRepository;
    }

    @Override
    public Observable<CatBreedViewModel> result() {

        return Observable.zip(catBreedsRepository.getCatBreedData(), catBreedsRepository.getCatBreedImageUrl(), new BiFunction<CatBreedApi, String, CatBreedViewModel>() {
            @Override
            public CatBreedViewModel apply(CatBreedApi catBreedApi, String imageUrl) {
                return new CatBreedViewModel(catBreedApi.getId(), catBreedApi.getName(), catBreedApi.getDescription(), imageUrl);
            }
        });
    }
}
