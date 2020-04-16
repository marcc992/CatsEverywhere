package es.marcmauri.catseverywhere.cats.catbreeds;

import java.util.List;

import es.marcmauri.catseverywhere.cats.CatBreedViewModel;
import es.marcmauri.catseverywhere.cats.CountryViewModel;
import io.reactivex.Observable;

public interface CatBreedsMVP {

    interface View {
        void updateData(CatBreedViewModel viewModel);
        void updateSpinner(CountryViewModel country);

        void showProgressBar();
        void hiddenProgressBar();
        void showSnackBar(String message);

        void navigateToCatBreedDetailsScreen(CatBreedViewModel catBreed);
    }

    interface Presenter {
        void loadCountries();
        void loadAllData();
        //void loadDataByCountry(String country_code);

        void onCatBreedCountryClicked(CountryViewModel country);
        void onCatBreedItemClicked(CatBreedViewModel catBreed);

        void rxJavaUnsubscribe();

        void setView(CatBreedsMVP.View view);
    }

    interface Model {
        Observable<CatBreedViewModel> getCatBreedsData();
        Observable<CountryViewModel> getCountries();
    }
}
