package com.rainersoft.megaabio.injection.component;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;
import com.rainersoft.megaabio.data.DataManager;
import com.rainersoft.megaabio.injection.ApplicationContext;
import com.rainersoft.megaabio.injection.module.AppModule;

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {

    @ApplicationContext
    Context context();

    Application application();

    DataManager apiManager();
}
