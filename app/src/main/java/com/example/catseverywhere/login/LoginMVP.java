package com.example.catseverywhere.login;

import io.reactivex.Observable;

public interface LoginMVP {

    interface View {
        String getUsername();

        String getPassword();

        void showProgress();

        void hiddenProgress();

        void showInvalidInput();

        void showInvalidCredentials();

        void showValidCredentials();

        void showCustomSnackBar(String text);

        void setUsername(String user);

        void setPassword(String pass);
    }

    interface Presenter {
        void setView(LoginMVP.View view);

        void onLogInButtonClicked();

        void onSignUpButtonClicked();

        void rxJavaUnsuscribe();
    }

    interface Model {
        Observable<User> createUser(String firstName, String lastName, String username, String password);

        Observable<User> getUser(String user, String pass);
    }
}
