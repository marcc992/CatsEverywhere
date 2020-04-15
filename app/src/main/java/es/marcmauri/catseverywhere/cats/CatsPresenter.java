package es.marcmauri.catseverywhere.cats;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.Nullable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class CatsPresenter implements CatsMVP.Presenter {

    @Nullable
    private CatsMVP.View view;
    private CatsMVP.Model model;

    private Disposable catBreedsSubscription = null;

    public CatsPresenter(CatsMVP.Model model) {
        this.model = model;
    }


    @Override
    public void loadData() {
        catBreedsSubscription = model.result()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<CatViewModel>() {

                    @Override
                    public void onNext(CatViewModel catViewModel) {
                        if (view != null) {
                            view.updateData(catViewModel);
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
    public void onCatBreedItemClicked(CatViewModel catBreed) {
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
    public void setView(CatsMVP.View view) {
        this.view = view;
    }
}
