package com.rainersoft.megaabio.features.login;

import com.rainersoft.megaabio.features.base.MvpView;

import java.util.List;

public interface LoginMvpView extends MvpView {

    void showPokemon(List<String> pokemon);

    void showProgress(boolean show);

    void showError(Throwable error);
}
