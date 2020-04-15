package es.marcmauri.catseverywhere.login;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.catseverywhere.R;
import es.marcmauri.catseverywhere.root.App;
import com.google.android.material.snackbar.Snackbar;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity implements LoginMVP.View {

    private final String TAG = LoginActivity.class.getName();

    @BindView(R.id.activity_login_root_view)
    ViewGroup rootView;

    @BindView(R.id.editText_loginUsername)
    EditText editTextUsername;

    @BindView(R.id.editText_loginPassword)
    EditText editTextPassword;

    @BindView(R.id.progressBar_login)
    ProgressBar progressBar;

    @BindView(R.id.button_login)
    Button btnLogin;

    @BindView(R.id.button_signup)
    Button btnSignUp;

    @Inject
    LoginMVP.Presenter presenter;

    @OnClick(R.id.button_login)
    public void onLogInButtonClick() {
        presenter.onLogInButtonClicked();
    }

    @OnClick(R.id.button_signup)
    public void onSignUpButtonClick() {
        presenter.onSignUpButtonClicked();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Log.i(TAG, "onCreate()");

        ButterKnife.bind(this);

        ((App) getApplication()).getComponent().inject(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume()");
        presenter.setView(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop()");
        presenter.rxJavaUnsuscribe();
    }

    @Override
    public String getUsername() {
        return editTextUsername.getText().toString();
    }

    @Override
    public String getPassword() {
        return editTextPassword.getText().toString();
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
        editTextUsername.setEnabled(false);
        editTextPassword.setEnabled(false);
        btnLogin.setEnabled(false);
        btnSignUp.setEnabled(false);
    }

    @Override
    public void hiddenProgress() {
        editTextUsername.setEnabled(true);
        editTextPassword.setEnabled(true);
        btnLogin.setEnabled(true);
        btnSignUp.setEnabled(true);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showInvalidInput() {
        Snackbar.make(rootView, "You must indicate the username and password", Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showInvalidCredentials() {
        Snackbar.make(rootView, "Invalid credentials", Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showValidCredentials() {
        Snackbar.make(rootView, "User successfully logged in!", Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showCustomSnackBar(String text) {
        Snackbar.make(rootView, text, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void setUsername(String user) {
        editTextUsername.setText(user);
    }

    @Override
    public void setPassword(String pass) {
        editTextPassword.setText(pass);
    }
}
