package com.softthenext.gymmanagementsystem.Fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.softthenext.gymmanagementsystem.Adapter.MemberListAdapter;
import com.softthenext.gymmanagementsystem.Model.Customers;
import com.softthenext.gymmanagementsystem.R;
import com.softthenext.gymmanagementsystem.Retrofit.ApiInterface;
import com.softthenext.gymmanagementsystem.Url.AppUrl;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by AABHALI on 01-01-2018.
 */

public class MemberList_Fragment extends Fragment {
    MemberListAdapter adapter;
    RecyclerView recyclerView;
    List<Customers> customers;
    RecyclerView.LayoutManager layoutManager;
    ApiInterface apiInterface;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_all_memberlist,container,false);

        recyclerView = v.findViewById(R.id.mem_recyclerview);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        final ProgressDialog pd = new ProgressDialog(getContext());
        pd.setMessage("Loading...");
        pd.setCancelable(false);
        pd.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiInterface = retrofit.create(ApiInterface.class);
        Call<List<Customers>> call = apiInterface.getCustomername();

        call.enqueue(new Callback<List<Customers>>() {
            @Override
            public void onResponse(Call<List<Customers>> call, Response<List<Customers>> response) {
                pd.dismiss();
                customers = response.body();
                adapter = new MemberListAdapter(customers);
                recyclerView.setAdapter(adapter);
                Log.d("success","Successfully Fetched Data");
            }

            @Override
            public void onFailure(Call<List<Customers>> call, Throwable t) {
                pd.dismiss();
                Log.d("Error","Error in Fetching Data");

            }
        });

        return v;
    }
}
