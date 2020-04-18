package es.marcmauri.catseverywhere.cats.catbreeddetails;

import android.os.Bundle;
import android.util.Log;

import es.marcmauri.catseverywhere.cats.CatBreedViewModel;
import es.marcmauri.catseverywhere.common.ExtraTags;
import io.reactivex.annotations.Nullable;

public class CatBreedDetailsPresenter implements CatBreedDetailsMVP.Presenter {

    private static final String TAG = CatBreedDetailsPresenter.class.getName();

    @Nullable
    private CatBreedDetailsMVP.View view;
    private CatBreedDetailsMVP.Model model;

    public CatBreedDetailsPresenter(CatBreedDetailsMVP.Model model) {
        this.model = model;
    }


    @Override
    public void loadData() {
        if (view != null) {
            view.showProgressBar();

            CatBreedViewModel catBreed;

            Bundle extras = view.getExtras();

            if (extras != null) {
                catBreed = extras.getParcelable(ExtraTags.EXTRA_CAT_BREED_OBJECT);

                if (catBreed == null) {
                    Log.w(TAG, "Extras bundle from view has not '" + ExtraTags.EXTRA_CAT_BREED_OBJECT + "' extra");
                    view.showSnackBar("The breed of the cat could not be fetched");
                    catBreed = model.getUndefinedCatBreed();
                }

            } else {
                Log.w(TAG, "Extras bundle from view is NULL. Getting an undefined CatBreed");
                view.showSnackBar("The breed of the cat could not be fetched");
                catBreed = model.getUndefinedCatBreed();
            }

            view.setImage(catBreed.getImageUrl());
            view.setName(catBreed.getName());
            view.setDescription(catBreed.getDescription());
            view.setCountryCode(catBreed.getCountryCode());
            view.setTemperament(catBreed.getTemperament());
            view.setLink(catBreed.getWikipediaUrl());

            view.hiddenProgressBar();
        }
    }

    @Override
    public void setView(CatBreedDetailsMVP.View view) {
        this.view = view;
    }
}
