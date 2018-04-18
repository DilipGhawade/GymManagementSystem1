package com.softthenext.gymmanagementsystem.Validation;

import android.widget.EditText;

import java.util.regex.Pattern;

/**
 * Created by AABHALI on 26-12-2017.
 */

public class FormValidation {
    public  static final String EMAIL_REGX ="^[A-Za-z0-9._%+\\-]+@[A-Za-z0-9.\\-]+\\.[A-Za-z]{2,4}$";
    public static final String MOBILENO_REGEX= "^[0-9]{10}$";

    public static final String REQUIRED_MSG="required";
    public static final String EMAIL_MSG ="Invalid Email";
    public static final String MOBILE_NO_MSG="Enter 10 Digit only";

    public static boolean isEmailAddress(EditText editText,boolean required)
    {
        return isValid(editText,EMAIL_REGX,EMAIL_MSG,required);
    }
    public static boolean isMobileNo(EditText editText,boolean required)
    {
        return isValid(editText,MOBILENO_REGEX,MOBILE_NO_MSG,required);
    }

    public static boolean isValid(EditText editText,String regex,String ermsg,boolean required)
    {
        String text = editText.getText().toString().trim();
        editText.setError(null);
        if (required&& !hasText(editText))
        {
            return false;
        }
        if (required && !Pattern.matches(regex,text))
        {
         editText.setError(ermsg);
         return false;
        }
        return true;
    }
    public static boolean hasText(EditText editText)
    {
        String text = editText.getText().toString().trim();
        editText.setError(null);
        if (text.length()==0)
        {
            editText.setError(REQUIRED_MSG);
            return false;
        }
        return true;
    }

}
