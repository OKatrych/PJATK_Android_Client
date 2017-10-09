package eu.warble.pjapp.ui.login;


import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.CompositeMultiplePermissionsListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.multi.SnackbarOnAnyDeniedMultiplePermissionsListener;

import java.util.List;

import eu.warble.pjapp.R;
import eu.warble.pjapp.ui.base.BaseActivity;
import eu.warble.pjapp.ui.main.MainActivity;

public class LoginActivity extends BaseActivity<LoginPresenter> {

    private Button loginBtn;
    private TextInputEditText loginField, passwordField;
    private ProgressBar progressBar;

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_login);
        hideActionBar();
        initViews();
    }

    private void hideActionBar(){
        ActionBar bar = getSupportActionBar();
        if (bar != null)
            bar.hide();
    }

    private void initViews() {
        loginBtn = findViewById(R.id.loginBtn);
        loginField = findViewById(R.id.loginField);
        passwordField = findViewById(R.id.passwordField);
        progressBar = findViewById(R.id.loginProgressBar);
        loginBtn.setOnClickListener(view -> login());
    }

    private void login(){
        loginBtn.setEnabled(false);
        progressBar.setVisibility(View.VISIBLE);
        presenter.login(loginField.getText().toString(), passwordField.getText().toString());
    }

    public void showError(int stringResId){
        loginBtn.setEnabled(true);
        progressBar.setVisibility(View.GONE);
        Snackbar.make(findViewById(R.id.loginMainLayout), getString(stringResId), Snackbar.LENGTH_SHORT)
                .show();
    }

    public void showError(String error){
        loginBtn.setEnabled(true);
        progressBar.setVisibility(View.GONE);
        Snackbar.make(findViewById(R.id.loginMainLayout), error, Snackbar.LENGTH_SHORT)
                .show();
    }

    public void startMainActivity(){
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}
