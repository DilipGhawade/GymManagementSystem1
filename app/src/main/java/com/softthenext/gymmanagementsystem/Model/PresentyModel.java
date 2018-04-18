package com.softthenext.gymmanagementsystem.Model;

/**
 * Created by AABHALI on 04-01-2018.
 */

public class PresentyModel {
    private String pname;
    private String pdate;
    private String ppresent;

    public String getPname() {
        return pname;
    }

    public String getPdate() {
        return pdate;
    }

    public String getPpresent() {
        return ppresent;
    }

    public PresentyModel(String pname, String pdate, String ppresent) {
        this.pname = pname;
        this.pdate = pdate;
        this.ppresent = ppresent;
    }
}
