package es.marcmauri.catseverywhere.catbreeds;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.Nullable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class CatBreedsPresenter implements CatBreedsMVP.Presenter {

    @Nullable
    private CatBreedsMVP.View view;
    private CatBreedsMVP.Model model;

    private Disposable catBreedsSubscription = null;

    public CatBreedsPresenter(CatBreedsMVP.Model model) {
        this.model = model;
    }


    @Override
    public void loadData() {
        catBreedsSubscription = model.result()
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
                            view.showSnackBar("Error fetching cat breeds...");
                        }
                    }

                    @Override
                    public void onComplete() {
                        view.showSnackBar("Cat breeds data fetched successfully!");
                    }
                });
    }

    @Override
    public void onCatBreedItemClicked(CatBreedViewModel catBreed) {
        // todo: Go to the Cat breed details screen
        view.showSnackBar("Cat breed " + catBreed.getBreedName() + " clicked!");
    }

    @Override
    public void rxJavaUnsubscribe() {
        if (catBreedsSubscription != null && !catBreedsSubscription.isDisposed()) {
            catBreedsSubscription.dispose();
        }
    }

    @Override
    public void setView(CatBreedsMVP.View view) {
        this.view = view;
    }
}