package com.softthenext.gymmanagementsystem.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.softthenext.gymmanagementsystem.CustListActivity;
import com.softthenext.gymmanagementsystem.Pref.SharedPreferencesManager;
import com.softthenext.gymmanagementsystem.R;

/**
 * Created by AABHALI on 13-12-2017.
 */

public class Home_Fragment extends Fragment implements View.OnClickListener{
    LinearLayout linmaster,lincustentry,linfees,linattendance,
            linsms,lintools,linreport,linlogout,linWhatsApp;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_home,container,false);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
/*
        if (!SharedPreferencesManager.getInstance(getContext()).isLoggedIn()) {
            replaceFragment(new Login_fragment(),true);

        }*/

        linmaster = view.findViewById(R.id.master);
        lincustentry = view.findViewById(R.id.cust_entry);
        linfees = view.findViewById(R.id.fees);
        linattendance = view.findViewById(R.id.attendance);
        linsms = view.findViewById(R.id.sms);
        lintools = view.findViewById(R.id.tools);
        linreport = view.findViewById(R.id.report);
        linlogout = view.findViewById(R.id.logout);
        linWhatsApp = view.findViewById(R.id.whatsapp_report);


        linmaster.setOnClickListener(this);
        lincustentry.setOnClickListener(this);
        linfees.setOnClickListener(this);
        linattendance.setOnClickListener(this);
        linsms.setOnClickListener(this);
        lintools.setOnClickListener(this);
        linreport.setOnClickListener(this);
        linlogout.setOnClickListener(this);
        linWhatsApp.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.master:
                replaceFragment(new Master_Fragment(),true);
                break;
            case R.id.attendance:
                /*Intent i = new Intent(getActivity(), CustListActivity.class);
                startActivity(i);
                getActivity().finish();*/
                replaceFragment(new Attendance_Fragment(),true);
                break;
            case R.id.cust_entry:
                replaceFragment(new Cust_Entry_Fragment(),true);
                break;
            case R.id.fees:
                replaceFragment(new Fees_Fragment(),true);
                break;
            case R.id.sms:
                replaceFragment(new Sms_Fragment(),true);
                break;
            case R.id.tools:
                replaceFragment(new Tools_fragment(),true);
                break;
            case R.id.report:
                replaceFragment(new Report_Fragment(),true);
                break;

            case R.id.logout:
                SharedPreferencesManager.getInstance(getContext()).logout();
                replaceFragment(new Login_fragment(),true);
                break;
            case R.id.whatsapp_report:
                replaceFragment(new WhatsApp_Fragment(),true);
        }

    }

    public void replaceFragment(Fragment fragment,boolean addToBack)
    {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.Main_Container,fragment,fragment.getClass().getName());
        if (addToBack)
            ft.addToBackStack(null);
        ft.commit();
    }


}
