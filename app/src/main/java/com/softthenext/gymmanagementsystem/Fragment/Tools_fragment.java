package com.softthenext.gymmanagementsystem.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.softthenext.gymmanagementsystem.R;

/**
 * Created by AABHALI on 16-12-2017.
 */

public class Tools_fragment extends Fragment implements View.OnClickListener{
    LinearLayout usercreation,changepwd,updatcustomerinfo;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tools,container,false);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        usercreation = view.findViewById(R.id.add_adminuser);
        changepwd = view.findViewById(R.id.change_pwd);
        updatcustomerinfo = view.findViewById(R.id.update_custInfo);

        usercreation.setOnClickListener(this);
        changepwd.setOnClickListener(this);
        updatcustomerinfo.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.add_adminuser:
                replaceFragment(new Admin_Creation_Fragment(),true);
                break;
            case R.id.update_custInfo:
                replaceFragment(new Update_CustomerInfo_Fragment(),true);
                break;
            case R.id.change_pwd:

                break;
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
