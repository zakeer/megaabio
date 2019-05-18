package com.rainersoft.megaabio.data.remote;

import com.rainersoft.megaabio.data.model.request.LoginRequest;
import com.rainersoft.megaabio.data.model.request.OrderRequest;
import com.rainersoft.megaabio.data.model.response.AllResponse;
import com.rainersoft.megaabio.data.model.response.LoginResponse;
import com.rainersoft.megaabio.data.model.response.NewOrderResponse;
import com.rainersoft.megaabio.data.model.response.Pokemon;
import com.rainersoft.megaabio.data.model.response.PokemonListResponse;
import com.rainersoft.megaabio.data.model.response.ProductMetaDetailListResponse;
import com.rainersoft.megaabio.data.model.response.SingleRecordOrder;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    @GET("pokemon")
    Single<PokemonListResponse> getPokemonList(@Query("limit") int limit);

    @GET("pokemon/{name}")
    Single<Pokemon> getPokemon(@Path("name") String name);

    /**
     * User Login Request
     */
    @POST("login/loginUser")
    Single<LoginResponse> loginUser(@Body LoginRequest loginRequest);

    @GET("productmeta/getMetaDetails")
    Single<ProductMetaDetailListResponse> getMetaDetails();

    @GET("products/getAllResponse")
    Single<AllResponse> getAllResponse();

    @GET("orders/get_single_record?id={id}")
    Single<SingleRecordOrder> getSingleRecordOrder(@Path("id") String id);

    @GET("orders/getAllCustomerRecords?customer_id={id}")
    Single<Pokemon> getAllCustomerOrderRecords(@Path("id") String id);

    /**
     * New Order Request
     */
    @POST("orders/insert")
    Single<NewOrderResponse> orderInsert(@Body OrderRequest orderRequest);
}
