package com.rainersoft.megaabio.features.product;

import com.rainersoft.megaabio.data.model.response.AllResponse;
import com.rainersoft.megaabio.data.model.response.Product;
import com.rainersoft.megaabio.data.model.response.ProductDetail;
import com.rainersoft.megaabio.data.model.response.company.ResponseDatum;
import com.rainersoft.megaabio.features.base.MvpView;

import java.util.List;

public interface ProductDetailsMvpView extends MvpView {

    void showProgress(boolean show);

    void showError(Throwable error);

    void allResponse(AllResponse allResponse);

    void products(List<Product> productDetails, List<ProductDetail> productDetailList);

    void getCompanies(List<ResponseDatum> companies);
}
