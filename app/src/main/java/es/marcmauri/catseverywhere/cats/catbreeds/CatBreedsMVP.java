package es.marcmauri.catseverywhere.cats.catbreeds;

import es.marcmauri.catseverywhere.cats.CatBreedViewModel;
import io.reactivex.Observable;

public interface CatBreedsMVP {

    interface View {
        int getCurrentPage();
        int getProgressVisibility();
        boolean getIfAllCatsObtained();
        int getSpinnerSelectedItemPosition();

        void setCurrentPage(int page);
        void setIfAllCatsObtained(boolean allObtained);
        void setListFilter(String constraint);

        void updateCatBreedsData(CatBreedViewModel catBreed);

        void showProgressBar();
        void hiddenProgressBar();
        void showSnackBar(String message, boolean longTime);

        void navigateToCatBreedDetailsScreen(CatBreedViewModel catBreed);
    }

    interface Presenter {
        void loadCatBreedsFromPage(int pageNumber);

        void onRecyclerViewScrolled(int visibleItemCount, int totalItemCount, int pastVisibleItems, int dy);
        void onCatBreedCountryClicked(String selectedCountry, String allCountriesValue);
        void onCatBreedItemClicked(CatBreedViewModel catBreed);

        void rxJavaUnsubscribe();

        void setView(CatBreedsMVP.View view);
    }

    interface Model {
        Observable<CatBreedViewModel> getCatBreedsData(int pageNumber);
    }
}
