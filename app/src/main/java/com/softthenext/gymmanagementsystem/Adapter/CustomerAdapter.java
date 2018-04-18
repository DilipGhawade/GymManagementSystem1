package com.softthenext.gymmanagementsystem.Adapter;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.softthenext.gymmanagementsystem.MainActivity;
import com.softthenext.gymmanagementsystem.Model.CustModel;
import com.softthenext.gymmanagementsystem.Model.Customers;
import com.softthenext.gymmanagementsystem.Model.PresentyModel;
import com.softthenext.gymmanagementsystem.Model.Result;
import com.softthenext.gymmanagementsystem.R;
import com.softthenext.gymmanagementsystem.Retrofit.ApiInterface;
import com.softthenext.gymmanagementsystem.Url.AppUrl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by AABHALI on 01-01-2018.
 */

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.CustomerViewHolder> implements Spinner.OnItemSelectedListener{


    Context context;

    List<Customers> customers = new ArrayList<>() ;
    public CustomerAdapter( List<Customers> customers,Context context)
    {
        this.customers=customers;
        this.context=context;


    }

    @Override
    public CustomerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.attendance_item,parent,false);
        context=parent.getContext();
        return new CustomerViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CustomerViewHolder holder, int position) {

        holder.txtcustomername.setText(customers.get(position).getCustname());

    }

    @Override
    public int getItemCount() {
        return customers.size();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public class CustomerViewHolder extends RecyclerView.ViewHolder implements AdapterView.OnItemSelectedListener,View.OnClickListener
    {


        TextView txtcustomername,txtdate;
        Calendar calander;
        SimpleDateFormat simpledateformat;
        String Date;
        RadioGroup radioGroupprsenty;
        Button btnprsenty;



        public CustomerViewHolder(View itemView) {
            super(itemView);
            //this.context=context;
            txtcustomername = itemView.findViewById(R.id.txt_custname);
            txtdate = itemView.findViewById(R.id.txt_attendanceDate);
            radioGroupprsenty = itemView.findViewById(R.id.radiogrp_presenty);
            btnprsenty = itemView.findViewById(R.id.btn_takePresenty);
            btnprsenty.setOnClickListener(this);
            getDate();

        }

        public void makePresenty()
        {
           final ProgressDialog pd = new ProgressDialog(context);
            pd.setMessage("Taking Prsenty");
            pd.show();
            RadioButton rdbtnPrsenty = itemView.findViewById(radioGroupprsenty.getCheckedRadioButtonId());

            if (radioGroupprsenty.getCheckedRadioButtonId()!=-1)
            {
                String pname = txtcustomername.getText().toString().trim();
                String pdate = txtdate.getText().toString().trim();
                String ppresent = rdbtnPrsenty.getText().toString().trim();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(AppUrl.BaseUrl)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                ApiInterface apiInterface = retrofit.create(ApiInterface.class);

                PresentyModel presentyModel = new PresentyModel(pname,pdate,ppresent);
                Call<Result> call = apiInterface.addingpresenty(
                        presentyModel.getPname(),
                        presentyModel.getPdate(),
                        presentyModel.getPpresent()
                );

                call.enqueue(new Callback<Result>() {
                    @Override
                    public void onResponse(Call<Result> call, Response<Result> response) {
                        pd.dismiss();
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onFailure(Call<Result> call, Throwable t) {

                    }
                });


            }



        }
        public void getDate()
        {
            calander = Calendar.getInstance();
            simpledateformat = new SimpleDateFormat("yyyy/MM/dd");
            Date = simpledateformat.format(calander.getTime());
            txtdate.setText(Date);
        }



        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }

        @Override
        public void onClick(View view) {
            switch (view.getId())
            {
                case R.id.btn_takePresenty:
                    makePresenty();
                    break;
            }
        }
    }


}
