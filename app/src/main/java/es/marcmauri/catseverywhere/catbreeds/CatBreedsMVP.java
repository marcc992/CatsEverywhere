package es.marcmauri.catseverywhere.catbreeds;

import io.reactivex.Observable;

public interface CatBreedsMVP {

    interface View {
        void updateData(CatBreedViewModel viewModel);

        void showSnackBar(String message);
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
