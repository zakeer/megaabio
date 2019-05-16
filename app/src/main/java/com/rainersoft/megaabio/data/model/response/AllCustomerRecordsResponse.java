
package com.rainersoft.megaabio.data.model.response;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AllCustomerRecordsResponse {

    @SerializedName("response_data")
    @Expose
    private List<ResponseData> responseData = null;
    @SerializedName("status")
    @Expose
    private String status;

    public List<ResponseData> getResponseData() {
        return responseData;
    }

    public void setResponseData(List<ResponseData> responseData) {
        this.responseData = responseData;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
