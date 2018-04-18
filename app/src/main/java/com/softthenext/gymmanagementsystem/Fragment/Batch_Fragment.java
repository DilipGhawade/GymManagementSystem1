package com.softthenext.gymmanagementsystem.Fragment;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialogFragment;
import com.codetroopers.betterpickers.calendardatepicker.MonthAdapter;
import com.softthenext.gymmanagementsystem.Model.PackageModel;
import com.softthenext.gymmanagementsystem.Model.Result;
import com.softthenext.gymmanagementsystem.R;
import com.softthenext.gymmanagementsystem.Retrofit.ApiInterface;
import com.softthenext.gymmanagementsystem.Url.AppUrl;
import com.softthenext.gymmanagementsystem.Validation.FormValidation;

import android.text.format.DateFormat;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by AABHALI on 26-12-2017.
 */

public class Batch_Fragment extends Fragment implements View.OnClickListener,TimePickerDialog.OnTimeSetListener {
  EditText edtbid, edtbname, edtbtime;
  Button btnsubmit, btncancel, batch_selectdate;
  int date, month, year, hour, minute, second;
  int dayfianl, monthfianl, yearfianl, hourfianl, minutefianl, secondfinal;
  EditText edtdate;


  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

    View v = inflater.inflate(R.layout.fragment_batch_master, container, false);
    return v;
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    edtbid = view.findViewById(R.id.edt_batchid);
    edtbname = view.findViewById(R.id.edt_batchname);
    edtbtime = view.findViewById(R.id.edt_batchtime);

    btnsubmit = view.findViewById(R.id.batch_submit);
    btncancel = view.findViewById(R.id.batch_cancel);
    edtdate = view.findViewById(R.id.edt_batchdate);

    btnsubmit.setOnClickListener(this);
    btncancel.setOnClickListener(this);
    edtdate.setOnClickListener(this);

    editTextCheckEmpty();

    batch_selectdate = view.findViewById(R.id.batch_selectdate);
    batch_selectdate.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {

        Calendar now = Calendar.getInstance();
        CalendarDatePickerDialogFragment cdp = new CalendarDatePickerDialogFragment();

        Calendar calendarStarDate = Calendar.getInstance();
        calendarStarDate.add(Calendar.YEAR, -3);

        Log.d("test", "start" + calendarStarDate.getTime());
        MonthAdapter.CalendarDay calendarDay = new MonthAdapter.CalendarDay();
        calendarDay.setDay(calendarStarDate.get(Calendar.YEAR), calendarStarDate.get(Calendar.MONTH), calendarStarDate.get(Calendar.DAY_OF_MONTH));

        Log.d("end", "start" + now.getTime());
        MonthAdapter.CalendarDay calendarend = new MonthAdapter.CalendarDay();
        calendarend.setDay(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));

        // cdp.setDateRange(calendarDay, calendarend);

        now.add(Calendar.MONTH, 0);
        cdp.setPreselectedDate(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));


        cdp.show(getActivity().getSupportFragmentManager(), "Material Calendar Example");

        cdp.setOnDateSetListener(new CalendarDatePickerDialogFragment.OnDateSetListener() {
          @Override
          public void onDateSet(CalendarDatePickerDialogFragment dialog, int i, int i2, int i3) {

            yearfianl = i;
            monthfianl = i2;
            dayfianl = i3;
            Calendar c = Calendar.getInstance();
            hour = c.get(Calendar.HOUR_OF_DAY);
            minute = c.get(Calendar.MINUTE);
            showTime(hour,minute);

            TimePickerDialog tpd = new TimePickerDialog(getActivity(), Batch_Fragment.this, hour, minute, DateFormat.is24HourFormat(getActivity()));
            //tpd.setCurrentHour(Calendar.getInstance().get(Calendar.HOUR_OF_DAY));
            tpd.show();
            //fromDate = year + "/" + (monthOfYear + 1) + "/" + dayOfMonth;
            //Log.d("test", "Date:" + fromDate);
            // txtFromDate.setText(dayOfMonth+ "/" + (monthOfYear + 1)+ "/" +year);

          }
        });
            /*Calendar c = Calendar.getInstance();
            c.get(Calendar.YEAR);
            c.get(Calendar.MONTH);
            c.get(Calendar.DAY_OF_MONTH);
            CalendarDatePickerDialogFragment dpd;
            dpd = new CalendarDatePickerDialogFragment(getActivity(),
              Batch_Fragment.this,year,month,day);
            dpd.show();*/
      }
    });
  }


  @Override
  public void onClick(View view) {
    switch (view.getId()) {
      case R.id.batch_submit:

        if (checkValidation())
          addBatchDetails();
        break;
      case R.id.batch_cancel:

        clearEdittext();
        break;
      case R.id.edt_batchdate:
        selectdatetime();
        break;
    }

  }

  public void selectdatetime() {

  }

  public void clearEdittext() {
    edtbid.setText("");
    edtbname.setText("");
    edtbtime.setText("");
  }

  public void addBatchDetails() {
    final ProgressDialog pd = new ProgressDialog(getContext());
    pd.setTitle("Loading...");
    pd.setMessage("Adding Batch Pleas Wait");
    pd.setCancelable(false);
    pd.show();

    String bid = edtbid.getText().toString().trim();
    String bname = edtbname.getText().toString().trim();
    String btime = edtbtime.getText().toString().trim();

    Retrofit retrofit = new Retrofit.Builder()
      .baseUrl(AppUrl.BaseUrl)
      .addConverterFactory(GsonConverterFactory.create())
      .build();

    ApiInterface apiInterface = retrofit.create(ApiInterface.class);

    PackageModel packageModel = new PackageModel(bid, bname, btime);

    Call<Result> call = apiInterface.addingbatch(
      packageModel.getBid(),
      packageModel.getBname(),
      packageModel.getBtime()
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

  public boolean checkValidation() {
    boolean ret = true;

    if (!FormValidation.hasText(edtbid)) ret = true;
    if (!FormValidation.hasText(edtbname)) ret = true;
    if (!FormValidation.hasText(edtbtime)) ret = true;
    return ret;
  }

  public void editTextCheckEmpty() {
    edtbtime.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

      }

      @Override
      public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

      }

      @Override
      public void afterTextChanged(Editable editable) {

        FormValidation.hasText(edtbtime);
      }
    });

    edtbname.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

      }

      @Override
      public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

      }

      @Override
      public void afterTextChanged(Editable editable) {

        FormValidation.hasText(edtbname);
      }
    });
    edtbid.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

      }

      @Override
      public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

      }

      @Override
      public void afterTextChanged(Editable editable) {

        FormValidation.hasText(edtbid);
      }
    });
  }


/*  @Override
  public void onDateSet(CalendarDatePickerDialogFragment datePicker, int i, int i1, int i2) {
    yearfianl =i;
    monthfianl = i1;
    day = i2;
    Calendar  c =Calendar.getInstance();
    hour = c.get(Calendar.HOUR_OF_DAY);
    minute = c.get(Calendar.MINUTE);

    TimePickerDialog tpd = new TimePickerDialog(getActivity(), Batch_Fragment.this,hour,minute,DateFormat.is24HourFormat(getActivity()));
    tpd.show();
  }*/


  @Override
  public void onTimeSet(TimePicker timePicker, int i5, int i4) {
    hourfianl = i5;
    minutefianl = i4;
    edtdate.setText("" + yearfianl + "" +
      "/" + monthfianl + "" +
      "/" + dayfianl + "" +
      "/" + hourfianl + "" +
      "/" + minutefianl);

  }

  private String format = "";

  public void showTime(int hour, int minute) {
    if (hour == 0) {
      hour += 12;
      format = "AM";
    } else if (hour == 12) {
      format = "PM";
    } else if (hour > 12) {
      hour -= 12;
      format = "PM";
    } else {
      format = "AM";
    }
  }
}
