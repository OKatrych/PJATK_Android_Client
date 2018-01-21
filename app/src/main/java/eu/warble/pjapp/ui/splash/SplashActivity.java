package eu.warble.pjapp.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;

import eu.warble.pjapp.R;
import eu.warble.pjapp.ui.MainActivity;
import eu.warble.pjapp.ui.base.BaseActivity;
import eu.warble.pjapp.ui.login.LoginActivity;

public class SplashActivity extends BaseActivity<SplashPresenter> {

    @Override
    protected SplashPresenter createPresenter() {
        return new SplashPresenter(this);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTheme(R.style.SplashTheme);
    }

    public void showMessage(String message){
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
                .show();
    }

    public void showConnectionError(){
        Snackbar.make(findViewById(android.R.id.content), getString(R.string.connect_error), Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.retry, l -> presenter.loadData())
                .setActionTextColor(ContextCompat.getColor(SplashActivity.this, R.color.colorAccent))
                .show();
    }

    public void startLoginActivity(){
        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    public void startMainActivity(){
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    public void startMainActivityWithNoInternetMode() {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("internet", false);
        startActivity(intent);
        finish();
    }
}
