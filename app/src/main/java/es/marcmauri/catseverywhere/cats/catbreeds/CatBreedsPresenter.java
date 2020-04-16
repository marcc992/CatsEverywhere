package es.marcmauri.catseverywhere.cats.catbreeds;

import es.marcmauri.catseverywhere.cats.CatBreedViewModel;
import es.marcmauri.catseverywhere.cats.CountryViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.Nullable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class CatBreedsPresenter implements CatBreedsMVP.Presenter {

    @Nullable
    private CatBreedsMVP.View view;
    private CatBreedsMVP.Model model;

    private Disposable getDataSubscription = null;
    private Disposable getCountriesSubscription = null;

    public CatBreedsPresenter(CatBreedsMVP.Model model) {
        this.model = model;
    }


    @Override
    public void loadCountries() {
        getDataSubscription = model.getCountries()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<CountryViewModel>() {
                    @Override
                    public void onNext(CountryViewModel country) {
                        if (view != null) {
                            view.updateSpinner(country);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        if (view != null) {
                            view.hiddenProgressBar();
                            view.showSnackBar("Error fetching cat breed countries...");
                        }
                    }

                    @Override
                    public void onComplete() {
                        if (view != null) {
                            view.hiddenProgressBar();
                            view.showSnackBar("Cat breed countries fetched successfully!");
                        }
                    }
                });
    }

    @Override
    public void loadAllData() {
        if (view != null) {
            view.showProgressBar();
        }

        getDataSubscription = model.getCatBreedsData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<CatBreedViewModel>() {

                    @Override
                    public void onNext(CatBreedViewModel catBreedViewModel) {
                        if (view != null) {
                            view.updateData(catBreedViewModel);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        if (view != null) {
                            view.hiddenProgressBar();
                            view.showSnackBar("Error fetching cat breeds...");
                        }
                    }

                    @Override
                    public void onComplete() {
                        if (view != null) {
                            view.hiddenProgressBar();
                            view.showSnackBar("Cat breeds data fetched successfully!");
                        }
                    }
                });
    }

    @Override
    public void onCatBreedCountryClicked(CountryViewModel country) {
        if (view != null) {
            view.showSnackBar("Country: " + country.getName());
        }
        // TODO: Implementar carga de razas por pais
    }

    @Override
    public void onCatBreedItemClicked(CatBreedViewModel catBreed) {
        if (view != null) {
            view.navigateToCatBreedDetailsScreen(catBreed);
        }
    }

    @Override
    public void rxJavaUnsubscribe() {
        if (getDataSubscription != null && !getDataSubscription.isDisposed()) {
            getDataSubscription.dispose();
        }
    }

    @Override
    public void setView(CatBreedsMVP.View view) {
        this.view = view;
    }
}
