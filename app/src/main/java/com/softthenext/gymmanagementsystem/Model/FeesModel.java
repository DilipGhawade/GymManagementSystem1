package com.softthenext.gymmanagementsystem.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by AABHALI on 22-12-2017.
 */

public class FeesModel {


   private String fid;
   private String famount;
   private String fdate;
   private String feecustname;
   private String pid;
   private String totalfees;
   private String amtreceived;
   private String discount;
   private String feetodate;

   public String getFeecustname() {
      return feecustname;
   }

   public String getPid() {
      return pid;
   }

   public String getTotalfees() {
      return totalfees;
   }

   public String getAmtreceived() {
      return amtreceived;
   }

   public String getDiscount() {
      return discount;
   }

   public String getFeetodate() {
      return feetodate;
   }

   public String getFid() {
      return fid;
   }

   public String getFamount() {
      return famount;
   }

   public String getFdate() {
      return fdate;
   }

   public FeesModel(String fid, String famount, String fdate, String feecustname, String pid, String totalfees, String amtreceived, String discount, String feetodate) {
      this.fid = fid;
      this.famount = famount;
      this.fdate = fdate;
      this.feecustname = feecustname;
      this.pid = pid;
      this.totalfees = totalfees;
      this.amtreceived = amtreceived;
      this.discount = discount;
      this.feetodate = feetodate;
   }
}
