package es.marcmauri.catseverywhere.cats.catbreeds;

import android.util.Log;
import android.view.View;

import es.marcmauri.catseverywhere.cats.CatBreedViewModel;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.Nullable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class CatBreedsPresenter implements CatBreedsMVP.Presenter {

    private static final String TAG = CatBreedsPresenter.class.getName();

    @Nullable
    private CatBreedsMVP.View view;
    private CatBreedsMVP.Model model;

    private Disposable getDataSubscription = null;

    public CatBreedsPresenter(CatBreedsMVP.Model model) {
        this.model = model;
    }


    @Override
    public void loadCatBreedsFromPage(int pageNumber) {
        if (view != null) {
            view.showProgressBar();
        }

        getDataSubscription = model.getCatBreedsData(pageNumber)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .switchIfEmpty(new Observable<CatBreedViewModel>() {
                    @Override
                    protected void subscribeActual(Observer<? super CatBreedViewModel> observer) {
                        // If no more cats from server, we configure the view for not more requests
                        if (view != null) {
                            view.hiddenProgressBar();
                            view.setIfAllCatsObtained(true);
                            view.showSnackBar("No more cats to show!");
                        }
                    }
                })
                .subscribeWith(new DisposableObserver<CatBreedViewModel>() {

                    @Override
                    public void onNext(CatBreedViewModel catBreedViewModel) {
                        if (view != null) {
                            view.updateCatBreedsData(catBreedViewModel);
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
                            //view.showSnackBar("Cat breeds data fetched successfully!");
                        }
                    }
                });
    }

    @Override
    public void onRecyclerViewScrolled(int visibleItemCount, int totalItemCount, int pastVisibleItems, int dy) {
        if (view != null) {
            if (dy > 0) {
                if ((view.getProgressVisibility() != View.VISIBLE)
                        && ((totalItemCount - visibleItemCount) <= pastVisibleItems)) {
                    if (!view.getIfAllCatsObtained()) {
                        int currentPage = view.getCurrentPage();
                        loadCatBreedsFromPage(++currentPage);
                        view.setCurrentPage(currentPage);
                    }
                }
            }
        }

    }

    @Override
    public void onCatBreedCountryClicked(String selectedCountry, String allCountriesValue) {
        Log.i(TAG, "Country to filter list: " + selectedCountry);
        if (selectedCountry.equalsIgnoreCase(allCountriesValue)) {
            // If All countries selected then remove any filter
            selectedCountry = "";
        }
        if (view != null) {
            view.setListFilter(selectedCountry);
        }
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
