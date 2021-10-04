package com.example.ordersystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


import com.example.ordersystem.Model.Dish;
import com.example.ordersystem.ViewHolder.DishAdapter;
import com.kosalgeek.asynctask.AsyncResponse;
import com.kosalgeek.asynctask.PostResponseAsyncTask;

import java.util.HashMap;

import com.example.ordersystem.Common.Common;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class Home extends AppCompatActivity implements AsyncResponse, View.OnClickListener {
    Button logB, regB;
    EditText userT, passT;
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
        setContentView(R.layout.activity_home);

        logB = (Button)findViewById(R.id.logButton);
        userT = (EditText)findViewById(R.id.userLog);
        passT = (EditText)findViewById(R.id.passLog);
        regB = (Button)findViewById(R.id.regButton);


        userT.setFilters(new InputFilter[]{filter});
        passT.setFilters(new InputFilter[]{filter});
        logB.setOnClickListener(this);

        userT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userT.getText().clear();
            }
        });
        passT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passT.getText().clear();
            }
        });

        regB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent orderMenuIntent = new Intent(Home.this, register.class);
                startActivity(orderMenuIntent);
            }
        });
    }
    @Override
    public void processFinish(String result) {
        if(result.equals("Login Failed")) {
            Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show();
        }else{
            Log.i("tagconvertstr", "[" + result + "]");
            try {
                JSONArray products = new JSONArray(result);
                for (int i = 0; i < products.length(); i++) {

                    JSONObject dishesObject = products.getJSONObject(i);

                    String accountfName = dishesObject.getString("accfName");
                    String accountlName = dishesObject.getString("acclName");
                    String accountType = dishesObject.getString("accType");
                    Toast.makeText(this, accountType, Toast.LENGTH_SHORT).show();
                    String accountName = accountfName + " " + accountlName;
                    Common.currentName = accountName;
                    Common.currentUser = userT.getText().toString();
                    Common.currentPass = passT.getText().toString();
                    Common.currentType = accountType;
                    Intent orderMenuIntent = new Intent(Home.this, orderMenu.class);
                    startActivity(orderMenuIntent);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }



    @Override
    public void onClick(View v) {

            HashMap postData = new HashMap();
            postData.put("mobile", "android");
            postData.put("txtUser", String.valueOf(userT.getText()));
            postData.put("txtPass", String.valueOf(passT.getText()));

            PostResponseAsyncTask task = new PostResponseAsyncTask(Home.this, postData, Home.this);
            task.execute("http://10.0.2.2/client/loginCustomer.php");

    }

}
