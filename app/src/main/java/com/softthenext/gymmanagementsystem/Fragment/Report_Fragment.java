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

public class Report_Fragment extends Fragment implements View.OnClickListener{
    LinearLayout linMember,linactivemember,linfees_report,linpac_list;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_report,container,false);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        linMember = view.findViewById(R.id.mem_list);
        linactivemember = view.findViewById(R.id.active_mem);
        linfees_report = view.findViewById(R.id.fees_report);
        linpac_list = view.findViewById(R.id.pac_list);

        linMember.setOnClickListener(this);
        linactivemember.setOnClickListener(this);
        linfees_report.setOnClickListener(this);
        linpac_list.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.mem_list:
                replaceFragment(new MemberList_Fragment(),true);
                break;
            case R.id.fees_report:
                replaceFragment(new FeesList_Fragment(),true);
                break;
            case R.id.pac_list:
                replaceFragment(new PackageList_Fragment(),true);
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
