
package com.rainersoft.megaabio.data.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AllResponseRequest {

    @SerializedName("company_id")
    @Expose
    private String companyId;
    @SerializedName("product_meta_id")
    @Expose
    private String productMetaId;

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getProductMetaId() {
        return productMetaId;
    }

    public void setProductMetaId(String productMetaId) {
        this.productMetaId = productMetaId;
    }

}
