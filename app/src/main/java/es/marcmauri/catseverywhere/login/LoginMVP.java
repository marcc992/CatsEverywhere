package es.marcmauri.catseverywhere.login;

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

        void navigateToCatBreedsScreen();
    }

    interface Presenter {
        void setView(LoginMVP.View view);

        void onLogInButtonClicked();

        void onSignUpButtonClicked();

        void rxJavaUnsubscribe();
    }

    interface Model {
        Observable<User> createUser(String firstName, String lastName, String username, String password);

        Observable<User> getUser(String user, String pass);
    }
}
