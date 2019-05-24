package com.rainersoft.megaabio.data;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.rainersoft.megaabio.data.model.request.AllResponseRequest;
import com.rainersoft.megaabio.data.model.request.LoginRequest;
import com.rainersoft.megaabio.data.model.request.OrderRequest;
import com.rainersoft.megaabio.data.model.response.AllResponse;
import com.rainersoft.megaabio.data.model.response.LoginResponse;
import com.rainersoft.megaabio.data.model.response.NewOrderResponse;
import com.rainersoft.megaabio.data.model.response.ProductMetaDetailListResponse;
import com.rainersoft.megaabio.data.remote.ApiService;
import io.reactivex.Single;

/**
 * Created by shivam on 29/5/17.
 */
@Singleton
public class DataManager {

    private ApiService apiService;

    @Inject
    public DataManager(ApiService apiService) {
        this.apiService = apiService;
    }

    public Single<List<String>> getPokemonList(int limit) {
        return apiService
                .getPokemonList(limit)
                .toObservable()
                .flatMapIterable(namedResources -> namedResources.results)
                .map(namedResource -> namedResource.name)
                .toList();
    }


    public Single<LoginResponse> loginUser(LoginRequest loginRequest) {
        return apiService.loginUser(loginRequest);
    }

    public Single<ProductMetaDetailListResponse> getMetaDetails() {
        return apiService.getMetaDetails();
    }

    public Single<AllResponse> getAllResponse(AllResponseRequest allResponseRequest) {
        return apiService.getAllResponse(allResponseRequest);
    }

    public Single<NewOrderResponse> orderInsert(OrderRequest orderRequest) {
        return apiService.orderInsert(orderRequest);
    }


}
