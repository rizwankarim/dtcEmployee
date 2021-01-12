
package com.example.dtcemployee.Models.UploadReportImages;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UploadEmployeeImage {

    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("Add_Image_Id")
    @Expose
    private List<AddImageId> addImageId = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<AddImageId> getAddImageId() {
        return addImageId;
    }

    public void setAddImageId(List<AddImageId> addImageId) {
        this.addImageId = addImageId;
    }

}
