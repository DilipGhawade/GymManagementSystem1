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
 * Created by AABHALI on 15-12-2017.
 */

public class Master_Fragment extends Fragment implements View.OnClickListener{
    LinearLayout linpackagemaster,linbatchmaster;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_master,container,false);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        linpackagemaster = view.findViewById(R.id.package_master);
        linbatchmaster = view.findViewById(R.id.batch_master);

        linpackagemaster.setOnClickListener(this);
        linbatchmaster.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.package_master:
                replacementFragment(new Package_Master_Fragment(),true);
                break;
            case R.id.batch_master:
                replacementFragment(new Batch_Fragment(),true);
               break;
                default:
                    break;
        }

    }
    public void replacementFragment(Fragment fragment, boolean addToBack){
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();
        ft.replace(R.id.Main_Container,fragment,fragment.getClass().getName());
        if (addToBack)
            ft.addToBackStack(null);
        ft.commit();
    }
}
