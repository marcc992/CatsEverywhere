package es.marcmauri.catseverywhere.login;

import android.util.Log;

import io.reactivex.Observable;

public class LoginModel implements LoginMVP.Model {

    private final String TAG = LoginModel.class.getName();

    private LoginRepository repository;


    public LoginModel(LoginRepository repository) {
        this.repository = repository;
    }

    @Override
    public Observable<User> createUser(String firstName, String lastName, String username, String password) {
        return repository.createUser(firstName, lastName, username, password);
    }

    @Override
    public Observable<User> getUser(String user, String pass) {
        Log.d(TAG, "getUser(" + user + ", " + pass.length() + "*)");

        return repository.getUser(user, pass);
    }
}
