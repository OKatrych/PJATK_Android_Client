package eu.warble.pjapp.ui.login;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.widget.Button;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import eu.warble.pjapp.R;
import eu.warble.pjapp.ui.MainActivity;
import eu.warble.pjapp.ui.base.BaseActivity;

public class LoginActivity extends BaseActivity<LoginPresenter> {

    private CircularProgressButton loginBtn;
    private Button loginAsGuestBtn;
    private TextInputEditText loginField, passwordField;

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTheme(R.style.AppThemeWithoutActionBar);
        setContentView(R.layout.activity_login);
        initViews();
    }

    private void initViews() {
        loginBtn = findViewById(R.id.loginBtn);
        loginAsGuestBtn = findViewById(R.id.loginGuestBtn);
        loginField = findViewById(R.id.login);
        passwordField = findViewById(R.id.password);
        loginBtn.setOnClickListener(view -> login());
        loginAsGuestBtn.setOnClickListener(view -> startMainActivityWithGuestMode());
    }

    void showLoadingProgress(){
        loginBtn.startAnimation();
        loginField.setEnabled(false);
        passwordField.setEnabled(false);
        loginBtn.setEnabled(false);
        loginAsGuestBtn.setEnabled(false);
    }

    void hideLoadingProgress(){
        loginBtn.revertAnimation();
        loginField.setEnabled(true);
        passwordField.setEnabled(true);
        loginBtn.setEnabled(true);
        loginAsGuestBtn.setEnabled(true);
    }

    private void login(){
        loginBtn.setEnabled(false);
        showLoadingProgress();
        presenter.login(loginField.getText().toString(), passwordField.getText().toString());
    }

    public void showError(int stringResId){
        loginBtn.setEnabled(true);
        hideLoadingProgress();
        Snackbar.make(findViewById(R.id.loginMainLayout), getString(stringResId), Snackbar.LENGTH_SHORT)
                .show();
    }

    public void showError(String error){
        loginBtn.setEnabled(true);
        hideLoadingProgress();
        Snackbar.make(findViewById(R.id.loginMainLayout), error, Snackbar.LENGTH_SHORT)
                .show();
    }

    public void startMainActivity(){
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    public void startMainActivityWithGuestMode() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("guestMode", true);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loginBtn.dispose();
    }
}
