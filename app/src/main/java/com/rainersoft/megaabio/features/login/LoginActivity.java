package com.rainersoft.megaabio.features.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.rainersoft.megaabio.R;
import com.rainersoft.megaabio.data.model.request.LoginRequest;
import com.rainersoft.megaabio.data.model.response.LoginResponse;
import com.rainersoft.megaabio.features.base.BaseActivity;
import com.rainersoft.megaabio.features.cart.CartActivity;
import com.rainersoft.megaabio.features.common.ErrorView;
import com.rainersoft.megaabio.injection.component.ActivityComponent;

import java.util.List;
import java.util.logging.Logger;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import io.paperdb.Paper;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

public class LoginActivity extends BaseActivity implements LoginMvpView, ErrorView.ErrorListener {


    @Inject
    LoginPresenter mainPresenter;

    @BindView(R.id.etUsername)
    AutoCompleteTextView etUsername;

    @BindView(R.id.etPassword)
    EditText etPassword;
//
//    @BindView(R.id.progress)
//    ProgressBar progressBar;
//
//
//    @BindView(R.id.toolbar)
//    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        setSupportActionBar(toolbar);
//        errorView.setErrorListener(this);
//        mainPresenter.getPokemon(POKEMON_COUNT);
    }

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void inject(ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

    @Override
    protected void attachView() {
        mainPresenter.attachView(this);
    }

    @Override
    protected void detachPresenter() {
        mainPresenter.detachView();
    }


    @Override
    public void showProgress(boolean show) {
        if (show) {

        } else {

        }
    }

    @Override
    public void showError(Throwable error) {
//        errorView.setVisibility(View.VISIBLE);
        Timber.e(error, "There was an error in login");
    }

    @Override
    public void loginSuccess(LoginResponse loginResponse) {
        if(loginResponse.getStatus().equals("true")) {
            showSuccess("Login Success");
            Paper.book().write(LOGIN_USER, loginResponse.getUser());
            gotoHome();
        } else {
            showError("Invalid Login");
        }
    }

    @Override
    public void onReloadData() {
    }

    @OnClick(R.id.btnLogin)
    public void loginUser(View btnLogin) {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername( etUsername.getText().toString() );
        loginRequest.setPassword( etPassword.getText().toString() );

        mainPresenter.loginUser(loginRequest);
    }
}
