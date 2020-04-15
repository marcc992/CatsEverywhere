package com.example.catseverywhere.login;

import io.reactivex.Observable;

public interface LoginRepository {

    Observable<User> getUser(String user, String pass);

    Observable<User> createUser(String firstName, String lastName, String user, String pass);
}
