package com.softthenext.gymmanagementsystem.Fragment;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.softthenext.gymmanagementsystem.Model.Config;
import com.softthenext.gymmanagementsystem.Model.FeesModel;
import com.softthenext.gymmanagementsystem.Model.PackageModel;
import com.softthenext.gymmanagementsystem.Model.Result;
import com.softthenext.gymmanagementsystem.R;
import com.softthenext.gymmanagementsystem.Retrofit.ApiInterface;
import com.softthenext.gymmanagementsystem.Url.AppUrl;
import com.softthenext.gymmanagementsystem.Validation.FormValidation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by AABHALI on 16-12-2017.
 */

public class Fees_Fragment extends Fragment implements View.OnClickListener{

    EditText edtfid,edtfamt,edtfeefromdate,edtfeetodate,edttotfeeamount,edtamtreceived
            ,edtdiscount;
    Button btnsub,btncan;
    Spinner spinner_custname,spinner_packageId;
    ArrayList<String> custname;
    JSONArray cutName;
    ArrayList<String> packageID;
    JSONArray packageId;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_fees,container,false);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        edtfid = view.findViewById(R.id.edt_feeid);
        edtfamt = view.findViewById(R.id.edt_feeoutstanding);
        edtfeefromdate = view.findViewById(R.id.edt_feedatefrom);
        edtfeetodate = view.findViewById(R.id.edt_feedateto);
        edttotfeeamount = view.findViewById(R.id.edt_feeamount);
        edtamtreceived = view.findViewById(R.id.edt_amtreceived);
        edtdiscount = view.findViewById(R.id.edt_feediscount);




        btnsub = view.findViewById(R.id.fee_submit);
        btncan = view.findViewById(R.id.fee_cancel);

        spinner_custname = view.findViewById(R.id.spinner_cust_name);
        spinner_packageId = view.findViewById(R.id.spinner_packag_id);
        custname = new ArrayList<String>();
        packageID = new ArrayList<String>();
        getCustName();
        getPackageId();

        btncan.setOnClickListener(this);
        btnsub.setOnClickListener(this);
        edtfeefromdate.setOnClickListener(this);
        edtfeetodate.setOnClickListener(this);
        edtfamt.setOnClickListener(this);


        checkeditTextEmpty();
    }

    public void setDate()
    {
        final Calendar calendar = Calendar.getInstance();
        int yy = calendar.get(Calendar.YEAR);
        int mm = calendar.get(Calendar.MONTH);
        int dd = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String date = ""+String.valueOf(year) +"-"+String.valueOf(monthOfYear+1)
                        +"-"+String.valueOf(dayOfMonth);
                edtfeefromdate.setText(date);

                //tfDate.setText(date);
            }
        }, yy, mm, dd);
        datePicker.show();
    }
    public void setToDate()
    {
        final Calendar calendar = Calendar.getInstance();
        int yy = calendar.get(Calendar.YEAR);
        int mm = calendar.get(Calendar.MONTH);
        int dd = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String date = ""+String.valueOf(year) +""+String.valueOf(monthOfYear-1)
                        +"-"+String.valueOf(dayOfMonth);
                edtfeetodate.setText(date);
                //edtfeetodate.setText(date);
                //tfDate.setText(date);
            }
        }, yy, mm, dd);
        datePicker.show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.fee_submit:
                if (checkValidation())
                    insertfees();
                break;

            case R.id.fee_cancel:
                clearEdittext();
                break;
            case R.id.edt_feedatefrom:
                setDate();
                break;
            case R.id.edt_feedateto:
                setToDate();
                break;
            case R.id.edt_feeoutstanding:
                calculatetotalOutstandingamt();
                break;

        }

    }

    public void insertfees()
    {
        final ProgressDialog pd = new ProgressDialog(getContext());
        pd.setTitle("Loading");
        pd.setMessage("Adding Fees");
        pd.setCancelable(false);
        pd.show();

        String fid = edtfid.getText().toString().trim();
        String famount = edtfamt.getText().toString().trim();
        String fdate = edtfeefromdate.getText().toString().trim();
        String feecustname = spinner_custname.getSelectedItem().toString().trim();
        String pid = spinner_packageId.getSelectedItem().toString().trim();
        String totalfees = edttotfeeamount.getText().toString().trim();
        String amtreceived = edtamtreceived.getText().toString().trim();
        String discount = edtdiscount.getText().toString().trim();
        String feetodate = edtfeetodate.getText().toString().trim();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppUrl.BaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        FeesModel feesModel = new FeesModel(fid,famount,fdate,feecustname,pid,
                totalfees,amtreceived, discount,feetodate);

        Call<Result> call = apiInterface.addfees(
                feesModel.getFid(),
                feesModel.getFamount(),
                feesModel.getFdate(),
                feesModel.getFeecustname(),
                feesModel.getPid(),
                feesModel.getTotalfees(),
                feesModel.getAmtreceived(),
                feesModel.getDiscount(),
                feesModel.getFeetodate()
        );

        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                pd.dismiss();
                Toast.makeText(getContext(),response.body().getMessage(),Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                pd.dismiss();
                Toast.makeText(getContext(),t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
    public boolean checkValidation() {
        boolean ret = true;

        if (!FormValidation.hasText(edtfeefromdate)) ret = true;
        if (!FormValidation.hasText(edtfamt)) ret = true;
        if (!FormValidation.hasText(edtfid)) ret = true;
        if (!FormValidation.hasText(edtfamt)) ret = true;
        return ret;
    }
    public void clearEdittext()
    {
        edtfid.setText("");
        edtfamt.setText("");
        edtfeefromdate.setText("");
    }
    public void checkeditTextEmpty()
    {
        edtfeefromdate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                FormValidation.hasText(edtfeefromdate);
            }
        });
        edtfamt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                FormValidation.hasText(edtfamt);
            }
        });

        edtfid.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                FormValidation.hasText(edtfid);
            }
        });
        edtfamt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                FormValidation.hasText(edtfamt);
            }
        });
    }

    public void getCustName()
    {
        StringRequest stringRequest = new StringRequest(Config.CUSTOMER_NAME_URL, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject j = null;
                try {
                    //Parsing the fetched Json String to JSON Object
                    j = new JSONObject(response);

                    //Storing the Array of JSON String to our JSON Array
                    cutName = j.getJSONArray(Config.JSON_ARRAY);

                    //Calling method getStudents to get the students from the JSON Array
                    //getStudents(result);
                    getCustName(cutName);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        //Adding request to the queue
        requestQueue.add(stringRequest);
    }
    // Getting all Package id
    private void getCustName(JSONArray j){
        //Traversing through all the items in the json array
        for(int i=0;i<j.length();i++){
            try {
                //Getting json object
                JSONObject json = j.getJSONObject(i);
                //Adding the name of the student to array list
                custname.add(json.getString(Config.TAG_CUSTOMER_NAME));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        //Setting adapter to show the items in the spinner
        spinner_custname.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, custname));
    }

    public void getPackageId()
    {
        StringRequest stringRequest = new StringRequest(Config.PACKAGEID_URL, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject j = null;
                try {
                    //Parsing the fetched Json String to JSON Object
                    j = new JSONObject(response);

                    //Storing the Array of JSON String to our JSON Array
                    packageId = j.getJSONArray(Config.JSON_ARRAY);

                    //Calling method getStudents to get the students from the JSON Array
                    //getStudents(result);
                    getPackageId(packageId);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        //Adding request to the queue
        requestQueue.add(stringRequest);
    }
    // Getting all Package id
    private void getPackageId(JSONArray j){
        //Traversing through all the items in the json array
        for(int i=0;i<j.length();i++){
            try {
                //Getting json object
                JSONObject json = j.getJSONObject(i);
                //Adding the name of the student to array list
                packageID.add(json.getString(Config.TAG_PACKAGEID));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        //Setting adapter to show the items in the spinner
        spinner_packageId.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, packageID));
    }

    public void calculatetotalOutstandingamt()
    {
        Double totfeeamt,amtreceived,discount,outstanding;
        totfeeamt = Double.parseDouble(edttotfeeamount.getText().toString());
        amtreceived = Double.parseDouble(edtamtreceived.getText().toString());
        discount = Double.parseDouble(edtdiscount.getText().toString());
        outstanding = totfeeamt-(amtreceived+discount);
        edtfamt.setText(""+outstanding);

    }
}
