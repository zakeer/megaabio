package com.rainersoft.megaabio.injection.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import com.rainersoft.megaabio.data.remote.ApiService;
import retrofit2.Retrofit;

/**
 * Created by shivam on 29/5/17.
 */
@Module(includes = {NetworkModule.class})
public class ApiModule {

    @Provides
    @Singleton
    ApiService providePokemonApi(Retrofit retrofit) {
        return retrofit.create(ApiService.class);
    }
}
