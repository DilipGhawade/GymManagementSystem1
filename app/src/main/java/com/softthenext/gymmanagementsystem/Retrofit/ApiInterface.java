package com.softthenext.gymmanagementsystem.Retrofit;

import com.softthenext.gymmanagementsystem.Model.Customers;
import com.softthenext.gymmanagementsystem.Model.FeesModel;
import com.softthenext.gymmanagementsystem.Model.PackageModel;
import com.softthenext.gymmanagementsystem.Model.PackgModel;
import com.softthenext.gymmanagementsystem.Model.Result;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by AABHALI on 13-12-2017.
 */

public interface ApiInterface  {

    @FormUrlEncoded
    @POST("addpackage")
    Call<Result> addpackage(
            @Field("Pid") String Pid,
            @Field("Pname") String Pname,
            @Field("Pduration") String Pduration,
            @Field("Plaunchdate") String Plaunchdate,
            @Field("Pamount") String Pamount

        );

    @FormUrlEncoded
    @POST("addBatches")
    Call<Result> addingbatch(
            @Field("Bid")String Bid,
            @Field("Bname")String Bname,
            @Field("Btime")String Btime
    );

    @FormUrlEncoded
    @POST("register")
    Call<Result> registeradmin(
            @Field("name") String name,
            @Field("email") String email,
            @Field("password") String password,
            @Field("gender") String gender,
            @Field("mobno") String mobno,
            @Field("address") String address
    );
    @FormUrlEncoded
    @POST("addpresenty")
    Call<Result> addingpresenty(
            @Field("pname")String pname,
            @Field("pdate") String pdate,
            @Field("ppresent")String ppresent
    );


    @FormUrlEncoded
    @POST("login")
    Call<Result> userLogin(@Field("email")String email,
                          @Field("password")String password
                          );

    @FormUrlEncoded
    @POST("addfeess")
    Call<Result> addfees(@Field("fid")String fid,
                         @Field("famount")String famount,
                         @Field("fdate")String fdate,
                         @Field("feecustname")String feecustname,
                         @Field("pid")String pid,
                         @Field("totalfees")String totalfees,
                         @Field("amtreceived")String amtreceived,
                         @Field("discount")String discount,
                         @Field("feetodate")String feetodate

                         );

    @GET("getpackageid")
    public String gettingAllPackageid(Callback<List<PackageModel>>response);

    @FormUrlEncoded
    @POST("custcreation")
    Call<Result> registCustomer(@Field("custname")String custname,
                                @Field("custemail") String custemail,
                                @Field("custaddress") String custaddress,
                                @Field("custgender") String custgender,
                                @Field("pid")String pid,
                                @Field("bid") String bid,
                                @Field("fid") String fid,
                                @Field("joingngdate") String joingngdate,
                                @Field("expirydate") String expirydate,
                                @Field("custheight") String custheight,
                                @Field("custweight") String custweight,
                                @Field("custmobno") String custmobno,
                                @Field("custaltmobno") String custaltmobno,

                                @Field("custage") String custage,
                                @Field("custarea") String custarea,
                                @Field("custisexercised")String custisexercised,
                                @Field("custReason") String custReason,
                                @Field("custoutofshape") String custoutofshape,
                                @Field("custfitgoal") String custfitgoal,
                                @Field("custknowaboutus") String custknowaboutus,
                                @Field("custcity") String custcity,
                                @Field("custmedhistory") String custmedhistory,
                                @Field("custdob") String custdob,
                                @Field("custoverweight") String custoverweight


    );
    @POST("customerName.php")
    Call<List<Customers>> getCustomername();
    @POST("feeslist.php")
    Call<List<FeesModel>> getFeesDetails();

    @POST("packageList.php")
    Call<List<PackgModel>> getPackageDetails();


}
