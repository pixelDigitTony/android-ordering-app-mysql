package com.example.ordersystem;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ordersystem.Common.Common;
import com.example.ordersystem.Common.PassBill;
import com.example.ordersystem.Interface.ItemClickListener;
import com.example.ordersystem.Model.Bill;
import com.example.ordersystem.Model.Order;
import com.example.ordersystem.Model.Request;
import com.example.ordersystem.ViewHolder.BillAdapter;
import com.example.ordersystem.ViewHolder.OrderAdapter;
import com.kosalgeek.asynctask.AsyncResponse;
import com.kosalgeek.asynctask.PostResponseAsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class orderBill extends AppCompatActivity implements AsyncResponse {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    List<Bill> bills = new ArrayList<>();
    List<Request> requests;
    BillAdapter adapter;
    Button returnButton;
    TextView txtTotalPrice, txtOrderName, txtOrderDate, txtOrderStatus, txtCaterBy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_bill);

        recyclerView = (RecyclerView)findViewById(R.id.billList);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        txtTotalPrice = (TextView)findViewById(R.id.totalBill);
        txtOrderStatus = (TextView)findViewById(R.id.txtStatus);
        txtOrderName = (TextView)findViewById(R.id.txtName);
        txtCaterBy = (TextView)findViewById(R.id.caterText);
        txtOrderDate = (TextView)findViewById(R.id.txtBillDate);
        returnButton = (Button)findViewById(R.id.go_back);

        Intent i = getIntent();
        String orderTotal = PassBill.billTOTAL;
        String orderDate = PassBill.billDATE;
        String orderName = PassBill.billNAME;
        String orderStatus = PassBill.billSTATUS;


        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        HashMap postData = new HashMap();
        postData.put("mobile", "android");
        postData.put("txtUser", Common.currentUser);
        postData.put("txtPass", Common.currentPass);
        postData.put("txtOrderID", String.valueOf(PassBill.billID));
        PostResponseAsyncTask task = new PostResponseAsyncTask(orderBill.this, postData, orderBill.this);
        task.execute("http://10.0.2.2/client/orderBill.php");

        txtTotalPrice.setText(orderTotal);
        txtOrderDate.setText(orderDate);
        txtOrderName.setText(orderName);
        txtOrderStatus.setText(orderStatus);


        caterName();
    }

    private void caterName() {
        HashMap postData = new HashMap();
        postData.put("mobile", "android");
        postData.put("txtUser", Common.currentUser);
        postData.put("txtPass", Common.currentPass);
        postData.put("txtAccountType", Common.currentType);

        postData.put("txtCater", PassBill.billCATER);

        PostResponseAsyncTask task = new PostResponseAsyncTask(orderBill.this, postData, new AsyncResponse() {
            @Override
            public void processFinish(String results) {

                Log.i("tagconvertstr", "["+results+"]");
                try {
                    JSONArray products = new JSONArray(results);
                    for (int i = 0; i < products.length();i++){

                        JSONObject dishesObject = products.getJSONObject(i);

                        String caterfname = dishesObject.getString("accfName");
                        String caterlname = dishesObject.getString("acclName");

                        txtCaterBy.setText(caterfname + " " +caterlname);
                    }

                } catch (JSONException e){
                    e.printStackTrace();
                }
            }
        });
        task.execute("http://10.0.2.2/client/name.php");
    }


    @Override
    public void processFinish(String result) {
        Log.i("tagconvertstr", "["+result+"]");
        try {
            JSONArray products = new JSONArray(result);
            for (int i = 0; i < products.length();i++){

                JSONObject dishesObject = products.getJSONObject(i);

                String billId = dishesObject.getString(String.valueOf("idOrder"));
                String billTotal = dishesObject.getString("total");
                String billDate = dishesObject.getString(String.valueOf("date"));
                String billQuantity = dishesObject.getString("quantity");
                String billName = dishesObject.getString("dishName");
                Double billCost = dishesObject.getDouble("dishCost");

                bills.add(new Bill(billId, billTotal, billDate, billQuantity, billName, billCost));

            }
            adapter = new BillAdapter(this, bills);
            recyclerView.setAdapter(adapter);
        } catch (JSONException e){
            e.printStackTrace();
        }

    }


}
