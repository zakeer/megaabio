
package com.rainersoft.megaabio.data.model.request;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderRequest {

    @SerializedName("cust_id")
    @Expose
    private String custId;
    @SerializedName("amount")
    @Expose
    private Integer amount;
    @SerializedName("order_details")
    @Expose
    private List<OrderDetail> orderDetails = null;

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }

}
