package com.example.ordersystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ordersystem.Common.Common;
import com.kosalgeek.asynctask.AsyncResponse;
import com.kosalgeek.asynctask.PostResponseAsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class changeProfile extends AppCompatActivity implements AsyncResponse {

    EditText profUser, profPass, profFirst, profLast, profConPass, profContact, profAddress, profOrg, profNumber, profEmail;
    Button profBack, profSubmit;
    private String blockCharacterSet = "~#^|$%&*!?><: ";

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
        setContentView(R.layout.activity_change_profile);

        profUser = (EditText)findViewById(R.id.changeProfUser);
        profPass = (EditText)findViewById(R.id.changeProfPass);
        profFirst = (EditText)findViewById(R.id.changeProfFirstName);
        profLast = (EditText)findViewById(R.id.changeProfLastName);
        profConPass = (EditText)findViewById(R.id.changeProfConPass);
        profContact = (EditText)findViewById(R.id.changeProfContact);
        profAddress = (EditText)findViewById(R.id.changeProfAddress);
        profOrg = (EditText)findViewById(R.id.changeProfOrganization);
        profNumber = (EditText)findViewById(R.id.changeProfCustomerIdNo);
        profEmail = (EditText)findViewById(R.id.changeProfEmail);

        profUser.setFilters(new InputFilter[]{filter});
        profPass.setFilters(new InputFilter[]{filter});
        profFirst.setFilters(new InputFilter[]{filter});
        profLast.setFilters(new InputFilter[]{filter});
        profConPass.setFilters(new InputFilter[]{filter});
        profContact.setFilters(new InputFilter[]{filter});
        profAddress.setFilters(new InputFilter[]{filter});
        profOrg.setFilters(new InputFilter[]{filter});
        profNumber.setFilters(new InputFilter[]{filter});
        profEmail.setFilters(new InputFilter[]{filter});

        profBack = (Button)findViewById(R.id.profChangeBackButton);
        profSubmit = (Button)findViewById(R.id.profSubmitButton);

        profBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 finish();
            }
        });

        profSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeProfile();
            }
        });

        HashMap postData = new HashMap();
        postData.put("mobile", "android");
        postData.put("txtUser", Common.currentUser);
        postData.put("txtPass", Common.currentPass);

        PostResponseAsyncTask task = new PostResponseAsyncTask(this, postData, this);
        task.execute("http://10.0.2.2/client/viewProfile.php");
    }

    private void changeProfile(){
        String confirm = profConPass.getText().toString();
        String pass = profPass.getText().toString();
        if(confirm.equals(pass)) {
            HashMap postData = new HashMap();
            postData.put("mobile", "android");
            postData.put("txtUser", profUser.getText().toString());
            postData.put("txtPass", profPass.getText().toString());
            postData.put("txtFName", profFirst.getText().toString());
            postData.put("txtLName", profLast.getText().toString());
            postData.put("txtContact", profContact.getText().toString());
            postData.put("txtAddress", profAddress.getText().toString());
            postData.put("txtOrg", profOrg.getText().toString());
            postData.put("txtNumber", profNumber.getText().toString());
            postData.put("txtEmail", profEmail.getText().toString());
            postData.put("txtAccountType", Common.currentType);

            PostResponseAsyncTask task = new PostResponseAsyncTask(changeProfile.this, postData, new AsyncResponse() {
                @Override
                public void processFinish(String results) {
                    Log.i("tagconvertstr", "[" + results + "]");
                    Intent intent = new Intent(getApplicationContext(), profile.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivityForResult(intent,0);
                    finish();
                }
            });
            task.execute("http://10.0.2.2/client/changeProfile.php");

        }else{
            Toast.makeText(changeProfile.this, "Wrong confirm password", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void processFinish(String results) {
        Log.i("tagconvertstr", "["+results+"]");
        try {
            JSONArray products = new JSONArray(results);
            for (int i = 0; i < products.length();i++){

                JSONObject dishesObject = products.getJSONObject(i);

                String first_name = dishesObject.getString("profFName");
                String last_name = dishesObject.getString("profLName");
                String username = dishesObject.getString("profUser");
                String password = dishesObject.getString("profPass");
                String contact = dishesObject.getString("profCont");
                String address = dishesObject.getString("profAdd");
                String organization = dishesObject.getString("profOrg");
                String profemail = dishesObject.getString("profEmail");
                String profId = dishesObject.getString("profIdNum");

                profFirst.setText(first_name);
                profLast.setText(last_name);
                profUser.setText(username);
                profPass.setText(password);
                profContact.setText(contact);
                profAddress.setText(address);
                profOrg.setText(organization);
                profNumber.setText(profId);
                profEmail.setText(profemail);



            }
        } catch (JSONException e){
            e.printStackTrace();
        }
    }
    }

