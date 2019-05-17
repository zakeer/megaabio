package com.rainersoft.megaabio.features.login;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.rainersoft.megaabio.R;
import com.rainersoft.megaabio.features.base.BaseActivity;
import com.rainersoft.megaabio.features.common.ErrorView;
import com.rainersoft.megaabio.injection.component.ActivityComponent;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

public class LoginActivity extends BaseActivity implements LoginMvpView, ErrorView.ErrorListener {

    private static final int POKEMON_COUNT = 20;

    @Inject
    LoginPresenter mainPresenter;

//    @BindView(R.id.view_error)
//    ErrorView errorView;
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
    public void showPokemon(List<String> pokemon) {

    }

    @Override
    public void showProgress(boolean show) {
        if (show) {

//            errorView.setVisibility(View.GONE);
        } else {
//            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void showError(Throwable error) {
//        errorView.setVisibility(View.VISIBLE);
        Timber.e(error, "There was an error retrieving the pokemon");
    }

    @Override
    public void onReloadData() {
        mainPresenter.getPokemon(POKEMON_COUNT);
    }
}
