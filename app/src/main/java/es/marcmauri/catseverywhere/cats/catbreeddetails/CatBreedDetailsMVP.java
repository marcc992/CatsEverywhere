package es.marcmauri.catseverywhere.cats.catbreeddetails;

import android.os.Bundle;

import es.marcmauri.catseverywhere.cats.CatBreedViewModel;

public interface CatBreedDetailsMVP {

    interface View {
        void setImage(String url);
        void setName(String name);
        void setDescription(String description);
        void setCountryCode(String countryCode);
        void setTemperament(String temperament);
        void setLink(String link);

        void showProgressBar();
        void hiddenProgressBar();
        void showSnackBar(String message);

        Bundle getExtras();
    }

    interface Presenter {
        void loadData();

        void setView(CatBreedDetailsMVP.View view);
    }

    interface Model {
        CatBreedViewModel getUndefinedCatBreed();
    }
}
