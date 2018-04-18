package com.softthenext.gymmanagementsystem.Model;

import retrofit2.http.PUT;

/**
 * Created by AABHALI on 28-12-2017.
 */

public class Config {

    public static final String PACKAGEID_URL = "https://softtware.000webhostapp.com/gym/gym/public/getpackageid";
    public static final String BATCHGID_URL = "https://softtware.000webhostapp.com/gym/gym/public/getBatchbyid";
    public static final String FEESID_URL = "https://softtware.000webhostapp.com/gym/gym/public/getFeesbyid";
    public static final String CUSTOMER_NAME_URL="https://softtware.000webhostapp.com/gym/gym/public/customerbyname";
    //Tags used in the JSON String
    public static final String TAG_PACKAGEID = "Pid";
    public static final String TAG_BATCHID = "Bid";
    public static final String TAG_FEESID = "fid";
    public static final String TAG_CUSTOMER_NAME = "custname";
    //JSON array name
    public static final String JSON_ARRAY = "users";
}
