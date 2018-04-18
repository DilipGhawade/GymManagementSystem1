package com.softthenext.gymmanagementsystem.Model;

/**
 * Created by AABHALI on 21-12-2017.
 */

public class PackageModel {

    private String Pid;
    private String Pname;
    private String Plaunchdate;
    private String Pamount;
    private String Pduration;

    private  int id;
    private String name;
    private String email;
    private String password;
    private String gender;
    private String mobno;
    private String address;
    private String Bid;
    private String Bname;
    private String Btime;

    private String custname;
    private String custemail;
    private String custaddress;
    private String custgender;
    private String pid;
    private String bid;
    private String fid;


    public String getPids() {
        return pid;
    }
    public String getBids() {
        return bid;
    }
    public String getFids() {
        return fid;
    }

    private String joingngdate;
    private String expirydate;
    private String custheight;
    private String custweight;
    private String custmobno;



    public String getCustname() {
        return custname;
    }

    public String getCustemail() {
        return custemail;
    }

    public String getCustaddress() {
        return custaddress;
    }

    public String getCustgender() {
        return custgender;
    }

    public String getJoingngdate() {
        return joingngdate;
    }

    public String getExpirydate() {
        return expirydate;
    }

    public String getCustheight() {
        return custheight;
    }

    public String getCustweight() {
        return custweight;
    }

    public String getCustmobno() {
        return custmobno;
    }

    public String getPid() {
        return Pid;
    }
    public String getPamount() {
        return Pamount;
    }
    public String getPduration() {
        return Pduration;
    }
    public String getBid() {
        return Bid;
    }

    public String getBname() {
        return Bname;
    }

    public String getBtime() {
        return Btime;
    }



    public String getPname() {
        return Pname;
    }

    public String getPlaunchdate() {
        return Plaunchdate;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getGender() {
        return gender;
    }

    public String getMobno() {
        return mobno;
    }

    public String getAddress() {
        return address;
    }

    public int getId() {
        return id;
    }

public PackageModel(int id,String name, String email,String gender, String mobno, String address)
{
    this.id=id;
    this.name=name;
    this.email=email;
    this.gender=gender;
    this.mobno=mobno;
    this.address= address;
}

    public PackageModel(String name, String email, String password, String gender, String mobno, String address) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.mobno = mobno;
        this.address = address;
    }


    public PackageModel( String bid,String bname, String btime)
    {
        this.Bid=bid;
        this.Bname=bname;
        this.Btime=btime;
    }


    public PackageModel(String pid, String pname, String pduration,  String plaunchdate, String pamount)
    {
        this.Pid=pid;
        this.Pname=pname;
        this.Pamount=pamount;
        this.Pduration=pduration;
        this.Plaunchdate=plaunchdate;

    }


    public void setPid(String pid) {
        Pid = pid;
    }

    public PackageModel(String custname, String custemail, String custaddress, String custgender, String pid, String bid, String fid, String joingngdate, String expirydate, String custweight, String custheight, String custmobno)
    {
        this.custname = custname;
        this.custemail = custemail;
        this.custaddress = custaddress;
        this.custgender = custgender;
        this.pid = pid;
        this.bid = bid;
        this.fid=fid;
        this.joingngdate = joingngdate;
        this.expirydate = expirydate;
        this.custheight = custheight;
        this.custweight = custweight;
        this.custmobno = custmobno;

    }
    public PackageModel(String Pid)
    {
        this.Pid=Pid;
    }
}
