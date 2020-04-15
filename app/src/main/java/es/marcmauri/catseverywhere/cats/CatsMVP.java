package es.marcmauri.catseverywhere.cats;

import io.reactivex.Observable;

public interface CatsMVP {

    interface View {
        void updateData(CatViewModel viewModel);

        void showSnackBar(String message);
    }

    interface Presenter {
        void loadData();

        void onCatBreedItemClicked(CatViewModel catBreed);

        void rxJavaUnsubscribe();

        void setView(CatsMVP.View view);
    }

    interface Model {
        Observable<CatViewModel> result();
    }
}
