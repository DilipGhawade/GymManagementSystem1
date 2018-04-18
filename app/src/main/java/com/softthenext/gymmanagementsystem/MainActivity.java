package com.softthenext.gymmanagementsystem;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;

import com.softthenext.gymmanagementsystem.Fragment.Batch_Fragment;
import com.softthenext.gymmanagementsystem.Fragment.Home_Fragment;
import com.softthenext.gymmanagementsystem.Fragment.Login_fragment;
import com.softthenext.gymmanagementsystem.Pref.SharedPreferencesManager;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (!SharedPreferencesManager.getInstance(this).isLoggedIn()) {
            replacementFragment(new Batch_Fragment(),true);

        }
        else {
            replacementFragment(new Home_Fragment(), true);
        }



    }
    public void replacementFragment(Fragment fragment,boolean addToBack)
    {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.Main_Container,fragment,fragment.getClass().getName());
        if (addToBack);
         ft.addToBackStack(null);
        ft.commit();

    }



    @Override
    public void onBackPressed() {

        if(getSupportFragmentManager().getBackStackEntryCount()==1){
            finish();
        }else {
            super.onBackPressed();
        }
    }
}
