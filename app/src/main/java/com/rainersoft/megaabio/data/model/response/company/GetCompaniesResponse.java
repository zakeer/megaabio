
package com.rainersoft.megaabio.data.model.response.company;

import java.io.Serializable;
import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetCompaniesResponse implements Serializable, Parcelable
{

    @SerializedName("responseData")
    @Expose
    private List<ResponseDatum> responseData = null;
    @SerializedName("status")
    @Expose
    private String status;
    public final static Creator<GetCompaniesResponse> CREATOR = new Creator<GetCompaniesResponse>() {


        @SuppressWarnings({
            "unchecked"
        })
        public GetCompaniesResponse createFromParcel(Parcel in) {
            return new GetCompaniesResponse(in);
        }

        public GetCompaniesResponse[] newArray(int size) {
            return (new GetCompaniesResponse[size]);
        }

    }
    ;
    private final static long serialVersionUID = -4628448362023942235L;

    protected GetCompaniesResponse(Parcel in) {
        in.readList(this.responseData, (com.rainersoft.megaabio.data.model.response.company.ResponseDatum.class.getClassLoader()));
        this.status = ((String) in.readValue((String.class.getClassLoader())));
    }

    public GetCompaniesResponse() {
    }

    public List<ResponseDatum> getResponseData() {
        return responseData;
    }

    public void setResponseData(List<ResponseDatum> responseData) {
        this.responseData = responseData;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(responseData);
        dest.writeValue(status);
    }

    public int describeContents() {
        return  0;
    }

}
