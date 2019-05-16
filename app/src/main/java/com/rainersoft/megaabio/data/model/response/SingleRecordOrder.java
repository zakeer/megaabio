
package com.rainersoft.megaabio.data.model.response;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SingleRecordOrder {

    @SerializedName("order_details")
    @Expose
    private OrderDetails orderDetails;
    @SerializedName("customer_details")
    @Expose
    private CustomerDetails customerDetails;
    @SerializedName("order_item_details")
    @Expose
    private List<OrderItemDetail> orderItemDetails = null;
    @SerializedName("status")
    @Expose
    private String status;

    public OrderDetails getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(OrderDetails orderDetails) {
        this.orderDetails = orderDetails;
    }

    public CustomerDetails getCustomerDetails() {
        return customerDetails;
    }

    public void setCustomerDetails(CustomerDetails customerDetails) {
        this.customerDetails = customerDetails;
    }

    public List<OrderItemDetail> getOrderItemDetails() {
        return orderItemDetails;
    }

    public void setOrderItemDetails(List<OrderItemDetail> orderItemDetails) {
        this.orderItemDetails = orderItemDetails;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
