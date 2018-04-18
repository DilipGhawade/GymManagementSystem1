package com.softthenext.gymmanagementsystem.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.softthenext.gymmanagementsystem.R;

/**
 * Created by AABHALI on 09-01-2018.
 */

public class WhatsApp_Fragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_whatsapp_info,container,false);
        return v;
    }
}
