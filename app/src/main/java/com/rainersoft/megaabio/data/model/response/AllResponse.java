
package com.rainersoft.megaabio.data.model.response;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AllResponse {

    @SerializedName("meta_details")
    @Expose
    private MetaDetails metaDetails;
    @SerializedName("company_details")
    @Expose
    private List<CompanyDetail> companyDetails = null;
    @SerializedName("product_details")
    @Expose
    private List<ProductDetail> productDetails = null;
    @SerializedName("products")
    @Expose
    private List<Product> products = null;
    @SerializedName("status")
    @Expose
    private Boolean status;

    public MetaDetails getMetaDetails() {
        return metaDetails;
    }

    public void setMetaDetails(MetaDetails metaDetails) {
        this.metaDetails = metaDetails;
    }

    public List<CompanyDetail> getCompanyDetails() {
        return companyDetails;
    }

    public void setCompanyDetails(List<CompanyDetail> companyDetails) {
        this.companyDetails = companyDetails;
    }

    public List<ProductDetail> getProductDetails() {
        return productDetails;
    }

    public void setProductDetails(List<ProductDetail> productDetails) {
        this.productDetails = productDetails;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

}
