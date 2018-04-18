package com.softthenext.gymmanagementsystem.Fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
 * Created by AABHALI on 26-12-2017.
 */

public class Admin_Creation_Fragment extends Fragment implements View.OnClickListener{
    EditText edtadminusername,edtadminpassword,edtadminemail,edtadminmobno,edtadminadd;
    RadioGroup radioGroup;
    Button btnsubmit, btncancel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_user_admin,container,false);

        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        edtadminusername = view.findViewById(R.id.edtadmin_username);
        edtadminemail = view.findViewById(R.id.edtadmin_useremail);
        edtadminpassword = view.findViewById(R.id.edtadmin_userpassword);
        edtadminadd = view.findViewById(R.id.edtadmin_useraddress);
        edtadminmobno = view.findViewById(R.id.edtadmin_mobileno);

        editTextValidation();

        radioGroup = view.findViewById(R.id.gendr_grp);

        btnsubmit = view.findViewById(R.id.btn_adminsubmit);
        btncancel = view.findViewById(R.id.btn_admincancel);
        btnsubmit.setOnClickListener(this);
        btncancel.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btn_adminsubmit:
                if (checkValidation())
                registerAdmin();

                break;
            case R.id.btn_admincancel:
                clearTextBox();
                break;
        }
    }

    public void clearTextBox()
    {
        edtadminmobno.setText("");
        edtadminadd.setText("");
        edtadminpassword.setText("");
        edtadminemail.setText("");
        edtadminusername.setText("");
    }

    public void registerAdmin()
    {
       final ProgressDialog pd = new ProgressDialog(getContext());
        pd.setCancelable(true);
        pd.setTitle("Please Wait");
        pd.setMessage("Adding Admin");
        pd.show();

        final RadioButton radiosex = getView().findViewById(radioGroup.getCheckedRadioButtonId());
        if(radioGroup.getCheckedRadioButtonId()!=-1) {


            String name = edtadminusername.getText().toString().trim();
            String email = edtadminemail.getText().toString().trim();
            String password = edtadminpassword.getText().toString().trim();
            String gender = radiosex.getText().toString();
            String mobno = edtadminmobno.getText().toString().trim();
            String address = edtadminadd.getText().toString().trim();


            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(AppUrl.BaseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            ApiInterface apiInterface = retrofit.create(ApiInterface.class);

            PackageModel packageModel = new PackageModel(name, email, password, gender, mobno, address);

            Call<Result> call = apiInterface.registeradmin(
                    packageModel.getName(),
                    packageModel.getEmail(),
                    packageModel.getPassword(),
                    packageModel.getGender(),
                    packageModel.getMobno(),
                    packageModel.getAddress()
            );


            call.enqueue(new Callback<Result>() {
                @Override
                public void onResponse(Call<Result> call, Response<Result> response) {
                    pd.dismiss();

                    if (!response.body().getError())
                    {
                        Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        Toast.makeText(getContext(), "Please Fill all Field", Toast.LENGTH_LONG).show();
                    }

                }

                @Override
                public void onFailure(Call<Result> call, Throwable t) {
                    pd.dismiss();

                    Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();

                }
            });

        }

    }
    public boolean checkValidation() {
        boolean ret = true;

        if (!FormValidation.hasText(edtadminusername)) ret = true;
        if (!FormValidation.hasText(edtadminadd)) ret = true;
        if (!FormValidation.hasText(edtadminemail)) ret = true;
        if (!FormValidation.hasText(edtadminmobno)) ret = true;
        if (!FormValidation.hasText(edtadminpassword)) ret = true;


        return ret;
    }

    public void editTextValidation()
    {
        edtadminusername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                FormValidation.hasText(edtadminusername);

            }
        });

        edtadminemail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                FormValidation.hasText(edtadminemail);
            }
        });
        edtadminpassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                FormValidation.hasText(edtadminpassword);
            }
        });

        edtadminadd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                FormValidation.hasText(edtadminadd);
            }
        });
        edtadminmobno.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                FormValidation.hasText(edtadminmobno);
            }
        });

    }
}
