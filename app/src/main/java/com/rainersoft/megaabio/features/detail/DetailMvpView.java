package com.rainersoft.megaabio.features.detail;

import com.rainersoft.megaabio.data.model.response.Pokemon;
import com.rainersoft.megaabio.data.model.response.Statistic;
import com.rainersoft.megaabio.features.base.MvpView;

public interface DetailMvpView extends MvpView {

    void showPokemon(Pokemon pokemon);

    void showStat(Statistic statistic);

    void showProgress(boolean show);

    void showError(Throwable error);
}
