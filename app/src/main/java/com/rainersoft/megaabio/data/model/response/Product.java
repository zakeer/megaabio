
package com.rainersoft.megaabio.data.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Product {

    @SerializedName("product_id")
    @Expose
    private String productId;
    @SerializedName("product_name")
    @Expose
    private String productName;
    @SerializedName("company_name")
    @Expose
    private String companyName;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("type_name")
    @Expose
    private String typeName;
    @SerializedName("Prod_case")
    @Expose
    private String prodCase;

    @SerializedName("Price_per_unit")
    @Expose
    private String pricePerUnit;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("retail_price")
    @Expose
    private String retailPrice;

    @SerializedName("quantity")
    @Expose
    private int quantity = 0;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getPricePerUnit() {
        return pricePerUnit;
    }

    public void setPricePerUnit(String pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }

    public String getProdCase() {
        return prodCase;
    }

    public void setProdCase(String prodCase) {
        this.prodCase = prodCase;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(String retailPrice) {
        this.retailPrice = retailPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void increamentQuanity() {
        this.quantity++;
    }

    public void decreamentQuanity() {
        if(this.quantity <= 0) {
            this.quantity = 0;
        } else {
            this.quantity--;
        }

    }
}
