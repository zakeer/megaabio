
package com.rainersoft.megaabio.data.model.response;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductMetaDetailListResponse {

    @SerializedName("meta_response")
    @Expose
    private List<MetaResponseProduct> metaResponseProduct = null;
    @SerializedName("status")
    @Expose
    private Boolean status;

    public List<MetaResponseProduct> getMetaResponseProduct() {
        return metaResponseProduct;
    }

    public void setMetaResponseProduct(List<MetaResponseProduct> metaResponseProduct) {
        this.metaResponseProduct = metaResponseProduct;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

}
