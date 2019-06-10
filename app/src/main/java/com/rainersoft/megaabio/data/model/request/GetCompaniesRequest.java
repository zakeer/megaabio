
package com.rainersoft.megaabio.data.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetCompaniesRequest {

    @SerializedName("product_meta_id")
    @Expose
    private String productMetaId;

    public String getProductMetaId() {
        return productMetaId;
    }

    public void setProductMetaId(String productMetaId) {
        this.productMetaId = productMetaId;
    }

}
