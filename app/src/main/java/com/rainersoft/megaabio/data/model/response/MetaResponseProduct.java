
package com.rainersoft.megaabio.data.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MetaResponseProduct {

    @SerializedName("product_meta_id")
    @Expose
    private String productMetaId;
    @SerializedName("product_name")
    @Expose
    private String productName;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("product_meta_status")
    @Expose
    private String productMetaStatus;
    @SerializedName("comapny_id")
    @Expose
    private String comapnyId;

    public String getProductMetaId() {
        return productMetaId;
    }

    public void setProductMetaId(String productMetaId) {
        this.productMetaId = productMetaId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getProductMetaStatus() {
        return productMetaStatus;
    }

    public void setProductMetaStatus(String productMetaStatus) {
        this.productMetaStatus = productMetaStatus;
    }

    public String getComapnyId() {
        return comapnyId;
    }

    public void setComapnyId(String comapnyId) {
        this.comapnyId = comapnyId;
    }

}
