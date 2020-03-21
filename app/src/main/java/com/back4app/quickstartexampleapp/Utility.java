package com.back4app.quickstartexampleapp;

import android.widget.EditText;

public class Utility {

    public  static  boolean isEmpty(EditText editText)
    {
        return  editText.getText().toString().trim().isEmpty();
    }

    public  static  String getText(EditText editText)
    {
        return  editText.getText().toString().trim();
    }


}
