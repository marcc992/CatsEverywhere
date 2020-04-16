package es.marcmauri.catseverywhere.cats.catbreeddetails;

import es.marcmauri.catseverywhere.cats.CatBreedViewModel;

public class CatBreedDetailsModel implements CatBreedDetailsMVP.Model {

    private CatBreedDetailsRepository repository;


    public CatBreedDetailsModel(CatBreedDetailsRepository repository) {
        this.repository = repository;
    }

    @Override
    public CatBreedViewModel getUndefinedCatBreed() {
        return repository.getUndefinedCatBreed();
    }
}
