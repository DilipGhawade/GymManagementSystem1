package com.softthenext.gymmanagementsystem.Model;

/**
 * Created by AABHALI on 04-01-2018.
 */

public class PackgModel {
    private String Pid;
    private String Pname;
    private String Plaunchdate;
    private String Pamount;
    private String Pduration;

    public String getPid() {
        return Pid;
    }

    public String getPname() {
        return Pname;
    }

    public String getPlaunchdate() {
        return Plaunchdate;
    }

    public String getPamount() {
        return Pamount;
    }

    public String getPduration() {
        return Pduration;
    }

    public PackgModel(String pid, String pname, String plaunchdate, String pamount, String pduration) {
        Pid = pid;
        Pname = pname;
        Plaunchdate = plaunchdate;
        Pamount = pamount;
        Pduration = pduration;
    }
}
