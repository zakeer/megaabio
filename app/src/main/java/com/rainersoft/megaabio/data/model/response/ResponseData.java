
package com.rainersoft.megaabio.data.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseData {

    @SerializedName("order_date")
    @Expose
    private String orderDate;
    @SerializedName("order_status")
    @Expose
    private Object orderStatus;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("item_counts")
    @Expose
    private String itemCounts;
    @SerializedName("order_id")
    @Expose
    private String orderId;
    @SerializedName("cust_name")
    @Expose
    private String custName;
    @SerializedName("cust_id")
    @Expose
    private String custId;

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public Object getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Object orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getItemCounts() {
        return itemCounts;
    }

    public void setItemCounts(String itemCounts) {
        this.itemCounts = itemCounts;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

}
