package com.softthenext.gymmanagementsystem.Fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.IntegerRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.softthenext.gymmanagementsystem.Model.PackageModel;
import com.softthenext.gymmanagementsystem.Model.Result;
import com.softthenext.gymmanagementsystem.R;
import com.softthenext.gymmanagementsystem.Retrofit.ApiInterface;
import com.softthenext.gymmanagementsystem.Url.AppUrl;
import com.softthenext.gymmanagementsystem.Validation.FormValidation;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by AABHALI on 13-12-2017.
 */

public class Package_Master_Fragment extends Fragment implements View.OnClickListener {
    EditText edtpid, edtpname, edtpduration, edtpamount, edtplauchdate;
    Button btnsubmit, btncancel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_package_master, container, false);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        edtpid = view.findViewById(R.id.edt_package_id);
        edtpname = view.findViewById(R.id.edt_package_name);
        edtpduration = view.findViewById(R.id.edt_package_duration);
        edtpamount = view.findViewById(R.id.edt_package_amount);
        edtplauchdate = view.findViewById(R.id.edt_package_launchdate);

        btnsubmit = view.findViewById(R.id.btn_package_submit);
        btncancel = view.findViewById(R.id.btn_package_cancel);
        btnsubmit.setOnClickListener(this);
        btncancel.setOnClickListener(this);
        editTextValidation();

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_package_submit:
                if (checkValidation())
                    addingPackage();
                break;
            case R.id.btn_package_cancel:
                clearPackageEdtText();
                break;
        }

    }

    public void addingPackage() {
        final ProgressDialog pd = new ProgressDialog(getContext());
        pd.setTitle("Please Wait");
        pd.setMessage("Adding Package");
        pd.setCancelable(false);
        pd.show();


            String pid =(edtpid.getText().toString().trim());
            String pname = edtpname.getText().toString().trim();
            String pduration = (edtpduration.getText().toString().trim());
            String plaunchdate = edtplauchdate.getText().toString().trim();
            String pamount = (edtpamount.getText().toString().trim());



            Retrofit retrofit = new Retrofit.Builder().baseUrl(AppUrl.BaseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            ApiInterface apiInterface = retrofit.create(ApiInterface.class);

            PackageModel packageModel = new PackageModel(pid, pname, pduration, plaunchdate, pamount);
            Call<Result> call = apiInterface.addpackage(
                    packageModel.getPid(),
                    packageModel.getPname(),
                    packageModel.getPduration(),
                    packageModel.getPlaunchdate(),
                    packageModel.getPamount()

            );

            call.enqueue(new Callback<Result>() {
                @Override
                public void onResponse(Call<Result> call, Response<Result> response) {
                    pd.dismiss();
                    Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(Call<Result> call, Throwable t) {
                    pd.dismiss();
                    Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();

                }
            });


    }

    public void clearPackageEdtText() {
        edtpamount.setText("");
        edtpduration.setText("");
        edtpid.setText("");
        edtpname.setText("");
        edtplauchdate.setText("");
    }

    public boolean checkValidation() {
        boolean ret = true;

        if (!FormValidation.hasText(edtpname)) ret = true;
        if (!FormValidation.hasText(edtpid)) ret = true;
        if (!FormValidation.hasText(edtpamount)) ret = true;
        if (!FormValidation.hasText(edtpduration)) ret = true;
        if (!FormValidation.hasText(edtplauchdate)) ret = true;


        return ret;
    }

    public void editTextValidation() {
        edtpname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                FormValidation.hasText(edtpname);

            }
        });

        edtpamount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                FormValidation.hasText(edtpamount);

            }
        });

        edtpid.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                FormValidation.hasText(edtpid);
            }
        });
        edtpduration.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                FormValidation.hasText(edtpduration);
            }
        });
        edtplauchdate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                FormValidation.hasText(edtplauchdate);
            }
        });
    }
}
