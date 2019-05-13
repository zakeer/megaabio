package com.rainersoft.megaabio.features.main;

import java.util.List;

import com.rainersoft.megaabio.features.base.MvpView;

public interface MainMvpView extends MvpView {

    void showPokemon(List<String> pokemon);

    void showProgress(boolean show);

    void showError(Throwable error);
}
