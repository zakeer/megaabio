
package com.rainersoft.megaabio.data.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MetaDetails {

    @SerializedName("product_meta_id")
    @Expose
    private String productMetaId;
    @SerializedName("product_name")
    @Expose
    private String productName;

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

}
