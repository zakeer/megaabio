
package com.rainersoft.megaabio.data.model.response.company;

import java.io.Serializable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseDatum implements Serializable, Parcelable
{

    @SerializedName("product_meta_id")
    @Expose
    private String productMetaId;
    @SerializedName("product_name")
    @Expose
    private String productName;
    @SerializedName("company_id")
    @Expose
    private String companyId;
    @SerializedName("company_name")
    @Expose
    private String companyName;
    public final static Creator<ResponseDatum> CREATOR = new Creator<ResponseDatum>() {


        @SuppressWarnings({
            "unchecked"
        })
        public ResponseDatum createFromParcel(Parcel in) {
            return new ResponseDatum(in);
        }

        public ResponseDatum[] newArray(int size) {
            return (new ResponseDatum[size]);
        }

    }
    ;
    private final static long serialVersionUID = 3921009939198907237L;

    protected ResponseDatum(Parcel in) {
        this.productMetaId = ((String) in.readValue((String.class.getClassLoader())));
        this.productName = ((String) in.readValue((String.class.getClassLoader())));
        this.companyId = ((String) in.readValue((String.class.getClassLoader())));
        this.companyName = ((String) in.readValue((String.class.getClassLoader())));
    }

    public ResponseDatum() {
    }

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

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(productMetaId);
        dest.writeValue(productName);
        dest.writeValue(companyId);
        dest.writeValue(companyName);
    }

    public int describeContents() {
        return  0;
    }

}
