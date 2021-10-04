package com.example.ordersystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.kosalgeek.asynctask.AsyncResponse;
import com.kosalgeek.asynctask.PostResponseAsyncTask;

import java.util.HashMap;
import java.util.logging.Filter;

public class register extends AppCompatActivity implements AsyncResponse, View.OnClickListener{

    EditText userT, passT, lnameT, fnameT, contactT, homeAddressT, organizationT, emailT, idNumberT;
    Button cancelB, subB;
    private String blockCharacterSet = "~#^|$%&*!?><:";

    private InputFilter filter = new InputFilter() {

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

            if (source != null && blockCharacterSet.contains(("" + source))) {
                return "";
            }
            return null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        userT = (EditText)findViewById(R.id.user);
        passT = (EditText)findViewById(R.id.pass);
        fnameT = (EditText)findViewById(R.id.firstName);
        lnameT = (EditText)findViewById(R.id.lastName);
        cancelB = (Button)findViewById(R.id.cancelButton);
        contactT = (EditText)findViewById(R.id.contact);
        homeAddressT = (EditText)findViewById(R.id.address);
        emailT = (EditText)findViewById(R.id.email);
        organizationT = (EditText)findViewById(R.id.organization);
        idNumberT = (EditText)findViewById(R.id.customerIdNo);
        subB = (Button)findViewById(R.id.subButton);
        subB.setOnClickListener(this);

        userT.setFilters(new InputFilter[]{filter});
        passT.setFilters(new InputFilter[]{filter});
        fnameT.setFilters(new InputFilter[]{filter});
        lnameT.setFilters(new InputFilter[]{filter});
        contactT.setFilters(new InputFilter[]{filter});
        homeAddressT.setFilters(new InputFilter[]{filter});
        organizationT.setFilters(new InputFilter[]{filter});
        emailT.setFilters(new InputFilter[]{filter});
        idNumberT.setFilters(new InputFilter[]{filter});


        cancelB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Cancelled", Toast.LENGTH_SHORT).show();
                userT.getText().clear();
                passT.getText().clear();
                fnameT.getText().clear();
                lnameT.getText().clear();
                contactT.getText().clear();
                homeAddressT.getText().clear();
                organizationT.getText().clear();
                emailT.getText().clear();
                idNumberT.getText().clear();
                startActivity(new Intent(register.this, Home.class));
            }
        });


    }

    @Override
    public void processFinish(String result) {
        Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
        startActivity(new Intent(register.this, Home.class));
    }

    @Override
    public void onClick(View v) {

        HashMap postData = new HashMap();
        postData.put("mobile", "android");
        postData.put("txtFirstName", fnameT.getText().toString());
        postData.put("txtLastName", lnameT.getText().toString());
        postData.put("txtUser", userT.getText().toString());
        postData.put("txtPass", passT.getText().toString());
        postData.put("txtContact", contactT.getText().toString());
        postData.put("txtHomeAddress", homeAddressT.getText().toString());
        postData.put("txtOrganization", organizationT.getText().toString());
        postData.put("txtEmail", emailT.getText().toString());
        postData.put("txtIDNumber", idNumberT.getText().toString());

        PostResponseAsyncTask task = new PostResponseAsyncTask(this, postData, this);
        task.execute("http://10.0.2.2/client/register.php");


    }
}
