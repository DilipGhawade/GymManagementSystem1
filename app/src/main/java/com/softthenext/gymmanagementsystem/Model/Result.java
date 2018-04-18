package com.softthenext.gymmanagementsystem.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by AABHALI on 21-12-2017.
 */

public class Result {
    @SerializedName("error")
    private Boolean error;

    @SerializedName("message")
    private String message;

    @SerializedName("user")
    private PackageModel packageModel;

    public Result(Boolean error, String message, PackageModel packageModel) {
        this.error = error;
        this.message = message;
        this.packageModel = packageModel;
    }

    public Boolean getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public PackageModel getUser() {
        return packageModel;
    }
}
