package es.marcmauri.catseverywhere.login;

import dagger.Module;
import dagger.Provides;

@Module
public class LoginModule {

    @Provides
    public LoginMVP.Presenter provideLoginPresenter(LoginMVP.Model model) {
        return new LoginPresenter(model);
    }

    @Provides
    public LoginMVP.Model provideLoginModel(LoginRepository repository) {
        return new LoginModel(repository);
    }

    // todo: provide repo
    @Provides
    LoginRepository provideLoginRepository() {
        return new MockRepository();
    }
}
