package com.back4app.quickstartexampleapp.activity;

import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.back4app.quickstartexampleapp.R;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class CreateUserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

        final EditText etEmail = findViewById(R.id.etEmail);
        final EditText etPassword = findViewById(R.id.etPassword);
        final EditText etUserType = findViewById(R.id.etUserType);
        Button btnCreate = findViewById(R.id.btnCreate);


        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseUser parseUser = new ParseUser();

                parseUser.setUsername(etEmail.getText().toString());
                parseUser.setPassword(etPassword.getText().toString());
                parseUser.put("name","Mustafa");
                parseUser.put("surname","Berk");
                parseUser.put("userType", etUserType.getText().toString());

                parseUser.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null)
                            Toast.makeText(CreateUserActivity.this, "saved", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


    }
}
