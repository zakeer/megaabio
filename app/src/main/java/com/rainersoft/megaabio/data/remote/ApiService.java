package com.rainersoft.megaabio.data.remote;

import com.rainersoft.megaabio.data.model.request.AllResponseRequest;
import com.rainersoft.megaabio.data.model.request.GetCompaniesRequest;
import com.rainersoft.megaabio.data.model.request.LoginRequest;
import com.rainersoft.megaabio.data.model.request.OrderRequest;
import com.rainersoft.megaabio.data.model.response.AllCustomerRecordsResponse;
import com.rainersoft.megaabio.data.model.response.AllResponse;
import com.rainersoft.megaabio.data.model.response.LoginResponse;
import com.rainersoft.megaabio.data.model.response.NewOrderResponse;
import com.rainersoft.megaabio.data.model.response.Pokemon;
import com.rainersoft.megaabio.data.model.response.PokemonListResponse;
import com.rainersoft.megaabio.data.model.response.ProductMetaDetailListResponse;
import com.rainersoft.megaabio.data.model.response.SingleRecordOrder;
import com.rainersoft.megaabio.data.model.response.company.GetCompaniesResponse;

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

    @POST("products/getAllResponse")
    Single<AllResponse> getAllResponse(@Body AllResponseRequest allResponseRequest);

    @POST("companies/getCompanies")
    Single<GetCompaniesResponse> getCompanies(@Body GetCompaniesRequest getCompaniesRequest);

    @GET("orders/get_single_record?")
    Single<SingleRecordOrder> getSingleRecordOrder(@Query("id") String id);

    @GET("orders/get_meta_company?")
    Single<SingleRecordOrder> getMetaCompany(@Query("id") String id);

    @GET("orders/get_order_list?")
    Single<AllCustomerRecordsResponse> getAllCustomerOrderRecords(@Query("customer_id") String id);

    /**
     * New Order Request
     */
    @POST("orders/insert")
    Single<NewOrderResponse> orderInsert(@Body OrderRequest orderRequest);
}
