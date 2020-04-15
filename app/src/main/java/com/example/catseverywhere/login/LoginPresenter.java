package com.example.catseverywhere.login;

import android.util.Log;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.Nullable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class LoginPresenter implements LoginMVP.Presenter {

    private final String TAG = LoginPresenter.class.getName();

    @Nullable
    private LoginMVP.View view;
    private LoginMVP.Model model;

    private Disposable loginSubscription = null;


    public LoginPresenter(LoginMVP.Model model) {
        this.model = model;
    }

    @Override
    public void setView(LoginMVP.View view) {
        this.view = view;
    }

    @Override
    public void onLogInButtonClicked() {
        Log.d(TAG, "onLogInButtonClicked()");

        view.showProgress();

        if (view.getUsername().trim().isEmpty() || view.getPassword().trim().isEmpty()) {
            view.showInvalidInput();
            view.hiddenProgress();

        } else {
            loginSubscription = model.getUser(view.getUsername(), view.getPassword())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableObserver<User>() {
                        @Override
                        public void onNext(User user) {
                            if (view != null) {
                                if (user.isValid()) {
                                    view.showValidCredentials();
                                } else {
                                    view.showInvalidCredentials();
                                }
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                            if (view != null) {
                                view.showCustomSnackBar("Error on fetching user details");
                            }
                        }

                        @Override
                        public void onComplete() {
                            if (view != null) {
                                view.hiddenProgress();
                            }
                        }
                    });
        }
    }

    @Override
    public void onSignUpButtonClicked() {
        Log.d(TAG, "onSignUpButtonClicked()");
        // Todo: This method must be implemented in a real world
        view.showCustomSnackBar("WARNING: Method not implemented");
    }

    @Override
    public void rxJavaUnsuscribe() {
        if (loginSubscription != null && !loginSubscription.isDisposed()) {
            loginSubscription.dispose();
        }
    }
}
