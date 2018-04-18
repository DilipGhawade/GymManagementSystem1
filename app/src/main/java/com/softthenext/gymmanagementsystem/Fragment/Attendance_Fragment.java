package com.softthenext.gymmanagementsystem.Fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;

import com.softthenext.gymmanagementsystem.Adapter.CustomerAdapter;
import com.softthenext.gymmanagementsystem.Model.Customers;
import com.softthenext.gymmanagementsystem.R;
import com.softthenext.gymmanagementsystem.Retrofit.ApiInterface;
import com.softthenext.gymmanagementsystem.Url.AppUrl;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by AABHALI on 15-12-2017.
 */

public class Attendance_Fragment extends Fragment {

    RecyclerView recyclerView;
    CustomerAdapter adapter;
    RecyclerView.LayoutManager layoutManager;
    ApiInterface apiInterface;
    List<Customers> customerlist = new ArrayList<>();
    EditText editTextSearch;
    ArrayList<String> names;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_attendance, container, false);

        recyclerView = v.findViewById(R.id.attendance_recyclerview);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        editTextSearch = v.findViewById(R.id.edt_search);

        final ProgressDialog pd = new ProgressDialog(getActivity());
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
                customerlist = response.body();
                adapter = new CustomerAdapter(customerlist,getContext());
                recyclerView.setAdapter(adapter);
                Log.d("Success", "Load Data Successfully");
            }

            @Override
            public void onFailure(Call<List<Customers>> call, Throwable t) {
                pd.dismiss();
                Log.d("Error", "Error Loading data");
            }
        });
        edttextSearch();
        return v;
    }

    public void edttextSearch() {
        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            public void afterTextChanged(Editable s) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                charSequence = charSequence.toString().toLowerCase();
                final List<Customers> customers = new ArrayList<>();
                for (int j = 0; j < customerlist.size(); j++) {

                    final String text = customerlist.get(j).toString().toLowerCase();
                    if (text.contains(charSequence)) {

                        customers.add(customerlist.get(j));
                    }

                }
                layoutManager = new LinearLayoutManager(getContext());
                adapter =  new CustomerAdapter(customers,getContext());
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

        });

    }
}
