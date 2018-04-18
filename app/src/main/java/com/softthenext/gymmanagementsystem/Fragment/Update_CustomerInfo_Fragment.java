package com.softthenext.gymmanagementsystem.Fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.softthenext.gymmanagementsystem.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by AABHALI on 02-01-2018.
 */

public class Update_CustomerInfo_Fragment extends Fragment implements View.OnClickListener{
    EditText edt_age;
    TextView txtdob;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_update_custinfo,container,false);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        txtdob = view.findViewById(R.id.txt_custdob);
        txtdob.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.txt_custdob:
                selectDob();
                break;
        }

    }
    public void selectDob()
    {
        final int year, month,day;
        final Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dpd= new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                calendar.set(year,month,day);
                String date = new SimpleDateFormat("yyyy/mm/dd").format(calendar.getTime());
                txtdob.setText(date);
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);


            }
        },year,month,day);
        dpd.getDatePicker().setMaxDate(System.currentTimeMillis());
        Calendar c = Calendar.getInstance();
        dpd.updateDate(c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH));
        dpd.show();
    }
}
