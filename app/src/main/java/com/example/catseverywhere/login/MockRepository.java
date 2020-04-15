package com.example.catseverywhere.login;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;

public class MockRepository implements LoginRepository {

    private User mockUser = new User("John", "Doe", "user", "P@ss");


    @Override
    public Observable<User> getUser(String user, String pass) {
        if (mockUser.getUsername().equalsIgnoreCase(user) && mockUser.getPassword().equals(pass)) {
            mockUser.setValid(true);
            return Observable.just(mockUser).delay(1, TimeUnit.SECONDS);
        } else {
            // User constructor makes a User invalid (user.valid = false)
            return Observable.just(new User("", "", user, pass)).delay(2, TimeUnit.SECONDS);
        }
    }

    @Override
    public Observable<User> createUser(String firstName, String lastName, String user, String pass) {
        return Observable.empty();
    }
}
