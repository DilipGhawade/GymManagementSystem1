package com.softthenext.gymmanagementsystem.Fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.softthenext.gymmanagementsystem.MainActivity;
import com.softthenext.gymmanagementsystem.Model.Config;
import com.softthenext.gymmanagementsystem.Model.CustModel;
import com.softthenext.gymmanagementsystem.Model.PackageModel;
import com.softthenext.gymmanagementsystem.Model.Result;
import com.softthenext.gymmanagementsystem.R;
import com.softthenext.gymmanagementsystem.Retrofit.ApiInterface;
import com.softthenext.gymmanagementsystem.Url.AppUrl;
import com.softthenext.gymmanagementsystem.Validation.FormValidation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by AABHALI on 16-12-2017.
 */

public class Cust_Entry_Fragment extends Fragment implements View.OnClickListener,AdapterView.OnItemSelectedListener{
    EditText edtcustname,edtcustemail,edtcustaddress,edtcustmobno
            ,edtcustjoindate,edtcustendate,edtcustheight,edtcustweight,edtcustaltmobno,edtcutarea,edtcustage;
    Spinner spinnerpid,spinnerfid,spinnerbid,spinnercity;
    Button btnsub,btncan;
    RadioGroup radioGroup;
    RadioGroup isExercisegroup,preExercisegroupreason,outofshapegroup,
    overweightgroup,fitnessgoalgroup,aboutusgroup;
    TextView txtdob,txtmedicalhistory,txtselectedchkbox;
    CheckBox medhistoryCheckbox;
    //Array list for spinner
    private ArrayList<String> packageid;
    private ArrayList<String> batchid;
    private ArrayList<String> feesid;

    //JSON Array for Spinner
    private JSONArray result;
    private JSONArray batchjsonArray;
    private JSONArray feesjsonArray;
    Calendar calendar ;
    DatePickerDialog datePickerDialog ;
    int Year, Month, Day ;

    // for selecting multiple Checkbox
    String[] listItems;
    boolean[] checkedItems;
    ArrayList<Integer> mUserItems = new ArrayList<>();
       @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_cust_entry,container,false);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        edtcustname = view.findViewById(R.id.edt_cust_name);
        edtcustemail = view.findViewById(R.id.edt_cust_email);
        edtcustaddress = view.findViewById(R.id.edt_cust_address);
        edtcustmobno = view.findViewById(R.id.edt_cust_mobno);
        edtcustjoindate = view.findViewById(R.id.edt_cust_joindate);
        edtcustendate = view.findViewById(R.id.edt_cust_expdate);
        edtcustheight = view.findViewById(R.id.edt_cust_height);
        edtcustweight = view.findViewById(R.id.edt_cust_weight);
        edtcustaltmobno = view.findViewById(R.id.edt_cust_altmobno);
        edtcutarea = view.findViewById(R.id.edt_cust_area);
        edtcustage = view.findViewById(R.id.edt_cust_age);

        medhistoryCheckbox = view.findViewById(R.id.chk_backpain);
        txtdob = view.findViewById(R.id.txt_custdob);
        txtmedicalhistory = view.findViewById(R.id.txt_medical_history);
        txtselectedchkbox = view.findViewById(R.id.txt_selected_medicalhistory);



        editTextValidation();
        edtcustjoindate.setOnClickListener(this);
        edtcustendate.setOnClickListener(this);

        btnsub = view.findViewById(R.id.btn_cust_submit);
        btncan = view.findViewById(R.id.btn_cust_can);

        radioGroup = view.findViewById(R.id.radiosexgroup);

        isExercisegroup = view.findViewById(R.id.radio_isexercised);
        preExercisegroupreason = view.findViewById(R.id.radioreason);
        outofshapegroup = view.findViewById(R.id.radiooutofshape);
        overweightgroup = view.findViewById(R.id.radiooverweight);
        fitnessgoalgroup =view.findViewById(R.id.radiogoal);
        aboutusgroup = view.findViewById(R.id.radioaoutus);


        btncan.setOnClickListener(this);
        btnsub.setOnClickListener(this);
        txtdob.setOnClickListener(this);
        txtmedicalhistory.setOnClickListener(this);

        spinnerpid = view.findViewById(R.id.spinner_pid);
        spinnerfid = view.findViewById(R.id.spinner_fid);
        spinnerbid = view.findViewById(R.id.spinner_bid);
        spinnercity = view.findViewById(R.id.spinner_selectCity);

        packageid = new ArrayList<String>();
        batchid = new ArrayList<String>();
        feesid = new ArrayList<String>();
       getPackageId();
     getAllBatchId();
     getAllfeesId();
     getCity();

    }
    public void setDate() {
        final Calendar calendar = Calendar.getInstance();
        int yy = calendar.get(Calendar.YEAR);
        int mm = calendar.get(Calendar.MONTH);
        int dd = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String date = ""+String.valueOf(year) +"-"+String.valueOf(monthOfYear+1)
                        +"-"+String.valueOf(dayOfMonth);
                txtdob.setText(date);
                //edtfeetodate.setText(date);
                //tfDate.setText(date);
            }
        }, yy, mm, dd);
        datePicker.show();
    }
    public void setJoiningDate() {
       /* final Calendar calendar = Calendar.getInstance();
        int yy = calendar.get(Calendar.YEAR);
        int mm = calendar.get(Calendar.MONTH);
        int dd = calendar.get(Calendar.DAY_OF_MONTH);
        final DatePickerDialog datePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String date = "" + String.valueOf(year) + "-" + String.valueOf(monthOfYear)
                        + "-" + String.valueOf(dayOfMonth);

                //datePicker.getDatePicker().setMaxDate(System.currentTimeMillis());
                edtcustjoindate.setText(date);
                //tfDate.setText(date);
            }
        }, yy, mm, dd);
        datePicker.show();*/
        final Calendar calendar = Calendar.getInstance();
        int yy = calendar.get(Calendar.YEAR);
        int mm = calendar.get(Calendar.MONTH);
        int dd = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String date = ""+String.valueOf(year) +"-"+String.valueOf(monthOfYear+1)
                        +"-"+String.valueOf(dayOfMonth);
                edtcustjoindate.setText(date);
                //edtfeetodate.setText(date);
                //tfDate.setText(date);
            }
        }, yy, mm, dd);
        datePicker.show();
    }
    public void setExpDate() {
        /*final Calendar calendar = Calendar.getInstance();
        int yy = calendar.get(Calendar.YEAR);
        int mm = calendar.get(Calendar.MONTH);
        int dd = calendar.get(Calendar.DAY_OF_MONTH);
        final DatePickerDialog datePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String date = "" + String.valueOf(year) + "-" + String.valueOf(monthOfYear)
                        + "-" + String.valueOf(dayOfMonth);

                //datePicker.getDatePicker().setMaxDate(System.currentTimeMillis());
                edtcustendate.setText(date);
                //tfDate.setText(date);
            }
        }, yy, mm, dd);
        datePicker.show();*/
        final Calendar calendar = Calendar.getInstance();
        int yy = calendar.get(Calendar.YEAR);
        int mm = calendar.get(Calendar.MONTH);
        int dd = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String date = ""+String.valueOf(year) +"-"+String.valueOf(monthOfYear+1)
                        +"-"+String.valueOf(dayOfMonth);
                edtcustendate.setText(date);
                //edtfeetodate.setText(date);
                //tfDate.setText(date);
            }
        }, yy, mm, dd);
        datePicker.show();
    }
    public void getCity()
    {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item,getResources().getStringArray(R.array.cityName));
        spinnercity.setAdapter(arrayAdapter);
    }
    // Registering Customer

    public void addCustomerEntry()
    {
        final ProgressDialog pd = new ProgressDialog(getContext());
       // pd.setCancelable(true);
        pd.setMessage("Adding Customer");
        pd.show();


        final RadioButton radiosex = (RadioButton)getView().findViewById(radioGroup.getCheckedRadioButtonId());
        final RadioButton radioisexercised = (RadioButton)getView().findViewById(isExercisegroup.getCheckedRadioButtonId());
        final RadioButton radiopreexercised = (RadioButton)getView().findViewById(preExercisegroupreason.getCheckedRadioButtonId());
        final RadioButton radioioutofshapegroup = (RadioButton)getView().findViewById(outofshapegroup.getCheckedRadioButtonId());
        final RadioButton radiooverweightgroup = (RadioButton)getView().findViewById(overweightgroup.getCheckedRadioButtonId());
        final RadioButton radiofitnessgoalgroup = (RadioButton)getView().findViewById(fitnessgoalgroup.getCheckedRadioButtonId());
        final RadioButton radioabout = (RadioButton)getView().findViewById(aboutusgroup.getCheckedRadioButtonId());


        if (aboutusgroup.getCheckedRadioButtonId()!=-1)
        if (fitnessgoalgroup.getCheckedRadioButtonId()!=-1)
        if (overweightgroup.getCheckedRadioButtonId()!=-1)
        if (outofshapegroup.getCheckedRadioButtonId()!=-1)
        if (isExercisegroup.getCheckedRadioButtonId()!=-1)
        if (preExercisegroupreason.getCheckedRadioButtonId()!=-1)
        if (radioGroup.getCheckedRadioButtonId()!=-1)
       {
           String custname = edtcustname.getText().toString().trim();
           String custemail = edtcustemail.getText().toString().trim();
           String custaddress = edtcustaddress.getText().toString().trim();
           String custgender = radiosex.getText().toString().trim();
           String pid = spinnerpid.getSelectedItem().toString().trim();
           String bid = spinnerbid.getSelectedItem().toString().trim();
           String fid = spinnerfid.getSelectedItem().toString().trim();
           String joingngdate = edtcustjoindate.getText().toString().trim();
           String expirydate = edtcustendate.getText().toString().trim();
           String custheight = edtcustheight.getText().toString().trim();
           String custweight = edtcustweight.getText().toString().trim();
           String custmobno = edtcustmobno.getText().toString().trim();
           String custaltmobno = edtcustaltmobno.getText().toString().trim();
           String custage = edtcustage.getText().toString().trim();
           String custarea = edtcutarea.getText().toString().trim();
           String custisexercised = radioisexercised.getText().toString().trim();
           String custReason = radiopreexercised.getText().toString().trim();
           String custoutofshape = radioioutofshapegroup.getText().toString().trim();
           String custfitgoal = radiofitnessgoalgroup.getText().toString().trim();
           String custoverweight = radiooverweightgroup.getText().toString().trim();
           String custknowaboutus = radioabout.getText().toString().trim();
           String custcity = spinnercity.getSelectedItem().toString().trim();
           String custmedhistory= txtselectedchkbox.getText().toString().trim();
           String custdob = txtdob.getText().toString().trim();

           Retrofit retrofit = new Retrofit.Builder()
                   .baseUrl(AppUrl.BaseUrl)
                   .addConverterFactory(GsonConverterFactory.create())
                   .build();

           ApiInterface apiInterface = retrofit.create(ApiInterface.class);

           CustModel custModel = new CustModel(custname,custemail,custaddress,custgender,pid
           ,bid,fid,joingngdate,expirydate,custheight,custweight,custmobno,
                   custaltmobno,custage, custarea, custisexercised,custReason,
                   custoutofshape,custfitgoal,custknowaboutus,custcity,custmedhistory,
                   custdob,custoverweight

                   );

           Call<Result> call = apiInterface.registCustomer(
                   custModel.getCustname(),
                   custModel.getCustemail(),
                   custModel.getCustaddress(),
                   custModel.getCustgender(),
                    custModel.getPid(),
                   custModel.getBid(),
                   custModel.getFid(),
                   custModel.getJoingngdate(),
                   custModel.getExpirydate(),
                   custModel.getCustheight(),
                   custModel.getCustweight(),
                   custModel.getCustmobno(),
                   custModel.getCustaltmobno(),
                   custModel.getCustage(),
                   custModel.getCustarea(),
                   custModel.getCustisexercised(),
                   custModel.getCustReason(),
                   custModel.getCustoutofshape(),
                   custModel.getCustfitgoal(),
                   custModel.getCustknowaboutus(),
                   custModel.getCustcity(),
                   custModel.getCustmedhistory(),
                   custModel.getCustdob(),
                   custModel.getCustoverweight()


           );

           call.enqueue(new Callback<Result>() {
               @Override
               public void onResponse(Call<Result> call, retrofit2.Response<Result> response) {
                  pd.dismiss();
                   Toast.makeText(getContext(),response.body().getMessage(),Toast.LENGTH_LONG).show();

                   /*if (!response.body().getError())
                   {
                       Toast.makeText(getContext(),response.body().getMessage(),Toast.LENGTH_LONG).show();
                   }
                   else
                   {
                       Toast.makeText(getContext(),"Enter All Field",Toast.LENGTH_LONG).show();
                   }*/
               }

               @Override
               public void onFailure(Call<Result> call, Throwable t) {
                   pd.dismiss();
                   Toast.makeText(getContext(),t.getMessage(),Toast.LENGTH_LONG).show();

               }
           });

       }
    }
    public void selectDOB()
    {


    }
    public void getPackageId()
    {
        StringRequest stringRequest = new StringRequest(Config.PACKAGEID_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject j = null;
                try {
                    //Parsing the fetched Json String to JSON Object
                    j = new JSONObject(response);

                    //Storing the Array of JSON String to our JSON Array
                    result = j.getJSONArray(Config.JSON_ARRAY);

                    //Calling method getStudents to get the students from the JSON Array
                    //getStudents(result);
                    getPackageIds(result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        //Adding request to the queue
        requestQueue.add(stringRequest);
    }
// Getting all Package id
    private void getPackageIds(JSONArray j){
        //Traversing through all the items in the json array
        for(int i=0;i<j.length();i++){
            try {
                //Getting json object
                JSONObject json = j.getJSONObject(i);
                //Adding the name of the student to array list
                packageid.add(json.getString(Config.TAG_PACKAGEID));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        //Setting adapter to show the items in the spinner
        spinnerpid.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, packageid));
    }

    // Spinner showing All Batch id

    public void getAllBatchId()
    {
        StringRequest stringRequest = new StringRequest(Config.BATCHGID_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject j = null;
                try {
                    //Parsing the fetched Json String to JSON Object
                    j = new JSONObject(response);

                    //Storing the Array of JSON String to our JSON Array
                    batchjsonArray = j.getJSONArray(Config.JSON_ARRAY);

                    //Calling method getStudents to get the students from the JSON Array
                    //getStudents(result);
                    getBatchIds(batchjsonArray);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        //Adding request to the queue
        requestQueue.add(stringRequest);
    }

    private void getBatchIds(JSONArray j){
        //Traversing through all the items in the json array
        for(int i=0;i<j.length();i++){
            try {
                //Getting json object
                JSONObject json = j.getJSONObject(i);
                //Adding the name of the student to array list
                batchid.add(json.getString(Config.TAG_BATCHID));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        //Setting adapter to show the items in the spinner
        spinnerbid.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, batchid));
    }
    // Getting Fess ids
    public void getAllfeesId()
    {
        StringRequest stringRequest = new StringRequest(Config.FEESID_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject j = null;
                try {
                    //Parsing the fetched Json String to JSON Object
                    j = new JSONObject(response);

                    //Storing the Array of JSON String to our JSON Array
                    feesjsonArray = j.getJSONArray(Config.JSON_ARRAY);

                    //Calling method getStudents to get the students from the JSON Array
                    //getStudents(result);
                    feesIds(feesjsonArray);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        //Adding request to the queue
        requestQueue.add(stringRequest);
    }

    private void feesIds(JSONArray j){
        //Traversing through all the items in the json array
        for(int i=0;i<j.length();i++){
            try {
                //Getting json object
                JSONObject json = j.getJSONObject(i);
                //Adding the name of the student to array list
                feesid.add(json.getString(Config.TAG_FEESID));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        //Setting adapter to show the items in the spinner
        spinnerfid.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, feesid));
    }


    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btn_cust_submit:
                if (checkValidation())
                addCustomerEntry();
                break;
            case R.id.btn_cust_can:
                clearEdittext();
                break;
            case R.id.txt_custdob:
                setDate();
                break;
            case R.id.txt_medical_history:
                getmedicalHistory();
                break;
            case R.id.edt_cust_joindate:
                setJoiningDate();
                break;
            case R.id.edt_cust_expdate:
                setExpDate();
                break;


        }
    }
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
    }
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    public void editTextValidation()
    {
        edtcustname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                FormValidation.hasText(edtcustname);
            }
        });
        edtcustmobno.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                FormValidation.hasText(edtcustmobno);
            }
        });

        edtcustheight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                FormValidation.hasText(edtcustheight);
            }
        });
        edtcustemail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                FormValidation.hasText(edtcustemail);
            }
        });

        edtcustweight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                FormValidation.hasText(edtcustweight);
            }
        });
        edtcustjoindate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                FormValidation.hasText(edtcustjoindate);
            }
        });

        edtcustendate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                FormValidation.hasText(edtcustendate);
            }
        });
        edtcustaddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                FormValidation.hasText(edtcustaddress);
            }
        });
        edtcustaltmobno.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                FormValidation.hasText(edtcustaltmobno);
            }
        });
        edtcustage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                FormValidation.hasText(edtcustage);

            }
        });
        edtcutarea.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                FormValidation.hasText(edtcutarea);
            }
        });
    }
    public boolean checkValidation() {
        boolean ret = true;

        if (!FormValidation.hasText(edtcustaddress)) ret = true;
        if (!FormValidation.hasText(edtcustemail)) ret = true;
        if (!FormValidation.hasText(edtcustendate)) ret = true;
        if (!FormValidation.hasText(edtcustheight)) ret = true;
        if (!FormValidation.hasText(edtcustjoindate)) ret = true;
        if (!FormValidation.hasText(edtcustmobno)) ret = true;
        if (!FormValidation.hasText(edtcustname)) ret = true;
        if (!FormValidation.hasText(edtcustweight)) ret = true;
        if (!FormValidation.hasText(edtcustaltmobno)) ret = true;
        if (!FormValidation.hasText(edtcustage)) ret = true;
        if (!FormValidation.hasText(edtcutarea)) ret = true;
        return ret;
    }
    public void clearEdittext()
    {
        edtcustaddress.setText("");
        edtcustendate.setText("");
        edtcustjoindate.setText("");
        edtcustweight.setText("");
        edtcustmobno.setText("");
        edtcustname.setText("");
        edtcustheight.setText("");
        edtcustemail.setText("");
        edtcutarea.setText("");
        edtcustaltmobno.setText("");
        edtcustage.setText("");

    }
    public void getmedicalHistory()
    {
        listItems = getResources().getStringArray(R.array.MedicalHistory);
        checkedItems = new boolean[listItems.length];

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext());
        mBuilder.setTitle("Select Disease");
        mBuilder.setMultiChoiceItems(listItems, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                if(b){
                    mUserItems.add(i);
                }else{
                    mUserItems.remove((Integer.valueOf(i)));
                }
            }
        });
        mBuilder.setCancelable(false);
        mBuilder.setPositiveButton(R.string.ok_label, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                String item = "";
                for (int i = 0; i < mUserItems.size(); i++) {
                    item = item + listItems[mUserItems.get(i)];
                    if (i != mUserItems.size() - 1) {
                        item = item + ", ";
                    }
                }
                txtselectedchkbox.setText(item);
            }
        });

        mBuilder.setNegativeButton(R.string.dismiss_label, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        mBuilder.setNeutralButton(R.string.clear_all_label, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                for (int i = 0; i < checkedItems.length; i++) {
                    checkedItems[i] = false;
                    mUserItems.clear();
                    txtselectedchkbox.setText("");
                }
            }
        });

        AlertDialog mDialog = mBuilder.create();
        mDialog.show();

    }
 }


