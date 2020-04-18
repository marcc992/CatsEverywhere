package es.marcmauri.catseverywhere.cats.catbreeddetails;

import es.marcmauri.catseverywhere.cats.CatBreedViewModel;

public class MemoryCatBreedDetailsRepository implements CatBreedDetailsRepository {

    private CatBreedViewModel catBreed = null;

    @Override
    public CatBreedViewModel getUndefinedCatBreed() {
        if (catBreed == null) {
            catBreed = new CatBreedViewModel(
                    "Undefined",
                    "Undefined",
                    "Undefined",
                    "Undefined",
                    "Undefined",
                    "Undefined",
                    "",
                    "");
        }

        return catBreed;
    }
}
