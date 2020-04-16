package es.marcmauri.catseverywhere.cats.catbreeds;

import es.marcmauri.catseverywhere.cats.CatBreedViewModel;
import io.reactivex.Observable;

public interface CatBreedsMVP {

    interface View {
        void updateData(CatBreedViewModel viewModel);

        void showSnackBar(String message);

        void navigateToCatBreedDetailsScreen(CatBreedViewModel catBreed);
    }

    interface Presenter {
        void loadData();

        void onCatBreedItemClicked(CatBreedViewModel catBreed);

        void rxJavaUnsubscribe();

        void setView(CatBreedsMVP.View view);
    }

    interface Model {
        Observable<CatBreedViewModel> result();
    }
}
