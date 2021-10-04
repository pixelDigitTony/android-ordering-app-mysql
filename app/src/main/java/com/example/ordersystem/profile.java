package com.example.ordersystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.ordersystem.Common.Common;
import com.example.ordersystem.Model.Dish;
import com.example.ordersystem.ViewHolder.DishAdapter;
import com.kosalgeek.asynctask.AsyncResponse;
import com.kosalgeek.asynctask.PostResponseAsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class profile extends AppCompatActivity implements AsyncResponse {
    TextView profUser, profPass, profFirst, profLast, profContact, profAddress, profOrg, profNumber, profEmail;
    Button profBack, profChange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profUser = (TextView)findViewById(R.id.profUser);
        profPass = (TextView)findViewById(R.id.profPass);
        profFirst = (TextView)findViewById(R.id.profFirstName);
        profLast = (TextView)findViewById(R.id.profLastName);
        profContact = (TextView)findViewById(R.id.profContact);
        profAddress = (TextView)findViewById(R.id.profAddress);
        profOrg = (TextView)findViewById(R.id.profOrganization);
        profNumber = (TextView)findViewById(R.id.profCustomerIdNo);
        profEmail = (TextView)findViewById(R.id.profEmail);


        profBack = (Button)findViewById(R.id.profBackButton);
        profChange = (Button)findViewById(R.id.profChangeButton);

        profBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        profChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent orderMenuIntent = new Intent(profile.this, changeProfile.class);
                startActivity(orderMenuIntent);
            }
        });

        HashMap postData = new HashMap();
        postData.put("mobile", "android");
        postData.put("txtUser", Common.currentUser);
        postData.put("txtPass", Common.currentPass);

        PostResponseAsyncTask task = new PostResponseAsyncTask(this, postData, this);
        task.execute("http://10.0.2.2/client/viewProfile.php");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
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
