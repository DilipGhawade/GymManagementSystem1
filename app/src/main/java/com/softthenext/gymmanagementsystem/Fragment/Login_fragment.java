package com.softthenext.gymmanagementsystem.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.softthenext.gymmanagementsystem.ActivityConnectionFailed;
import com.softthenext.gymmanagementsystem.Model.Result;
import com.softthenext.gymmanagementsystem.Pref.SharedPreferencesManager;
import com.softthenext.gymmanagementsystem.R;
import com.softthenext.gymmanagementsystem.Retrofit.ApiInterface;
import com.softthenext.gymmanagementsystem.SplashScreenActivity;
import com.softthenext.gymmanagementsystem.Url.AppUrl;
import com.softthenext.gymmanagementsystem.Util.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by AABHALI on 13-12-2017.
 */

public class Login_fragment extends Fragment implements View.OnClickListener{
    EditText edtusername,edtuserpass;
    Button btnlogin,btncancel;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login,container,false);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        edtusername = view.findViewById(R.id.user_email);
        edtuserpass = view.findViewById(R.id.user_password);
        btnlogin = view.findViewById(R.id.btn_login);
        btncancel = view.findViewById(R.id.btn_cancel);

        btnlogin.setOnClickListener(this);
        btncancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {

            case R.id.btn_login:
                if (emptyEditText()) {
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromInputMethod(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    adminLogin();
                    edtuserpass.setText("");
                    edtusername.setText("");
                }


                break;
            case R.id.btn_cancel:
                getActivity().finish();
                break;
        }

    }

    public void adminLogin()
    {
        final ProgressDialog pd = new ProgressDialog(getContext());
        pd.setTitle("Please Wait");
        pd.setMessage("Login");
        pd.setCancelable(false);
        pd.show();

        String email = edtusername.getText().toString().trim();
        String password = edtuserpass.getText().toString().trim();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppUrl.BaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        Call<Result> call = apiInterface.userLogin(email,password);

        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                pd.dismiss();
                //if (Utils.isConnectedToInterner(getContext()))
                if (!response.body().getError()) {
                    //getActivity().finish();
                    SharedPreferencesManager.getInstance(getContext()).userLogin(response.body().getUser());
                    replacementFragment(new Home_Fragment(),true);
                } else {
                    Toast.makeText(getContext(), "Invalid email or password", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                pd.dismiss();
                Toast.makeText(getContext(),t.getMessage(),Toast.LENGTH_LONG).show();

            }
        });

    }

    public void userLogin()
    {
        String name = edtusername.getText().toString();
        String pass = edtuserpass.getText().toString();

        if (name.equals("admin")&& pass.equals("1234"))
        {
             if (Utils.isConnectedToInterner(getContext()))
        {
            replacementFragment(new Home_Fragment(),true);
        }
        else
        {
            Intent i = new Intent(getContext(), ActivityConnectionFailed.class);
            startActivity(i);
            getActivity().finish();
        }

        }
        else
        {
            Toast.makeText(getContext(),"Invalid user name or password",Toast.LENGTH_LONG).show();
        }
    }
    public void replacementFragment(Fragment fragment,boolean addToBack)
    {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.Main_Container,fragment,fragment.getClass().getName());
       // if (addToBack)
            ft.commit();
        //ft.addToBackStack(null);
    }
   public boolean emptyEditText()
   {
       if (TextUtils.isEmpty(edtusername.getText().toString()))
       {
           edtusername.setError("Enter User Name");
           return false;
       }
       else if (TextUtils.isEmpty(edtuserpass.getText().toString()))
       {
           edtuserpass.setError("Enter Password");
           return false;
       }
       return true;
   }
}
