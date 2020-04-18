package es.marcmauri.catseverywhere.cats.catbreeds;

import es.marcmauri.catseverywhere.cats.CatBreedViewModel;
import es.marcmauri.catseverywhere.cats.CountryViewModel;
import io.reactivex.Observable;
import io.reactivex.Observer;
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
                            view.updateIfExistMoreCats(false);
                            view.showSnackBar("No more cats to show!");
                        }
                    }
                })
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
                            //5. On complete, hidden the progress bar
                            view.hiddenProgressBar();
                            view.showSnackBar("Cat breeds data fetched successfully!");
                        }
                    }
                });
    }

    @Override
    public void onCatBreedCountryClicked(String country) {
        if (view != null) {
            view.showSnackBar("Country: " + country);
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
