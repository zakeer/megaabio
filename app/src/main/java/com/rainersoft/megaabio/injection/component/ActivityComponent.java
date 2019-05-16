package com.rainersoft.megaabio.injection.component;

import dagger.Subcomponent;

import com.rainersoft.megaabio.features.detail.DetailActivity;
import com.rainersoft.megaabio.features.login.LoginActivity;
import com.rainersoft.megaabio.features.main.MainActivity;
import com.rainersoft.megaabio.injection.PerActivity;
import com.rainersoft.megaabio.injection.module.ActivityModule;

@PerActivity
@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(MainActivity mainActivity);
    void inject(DetailActivity detailActivity);
    void inject(LoginActivity loginActivity);
}
