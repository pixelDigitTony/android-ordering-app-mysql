package com.example.ordersystem;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.ordersystem.Common.Common;
import com.example.ordersystem.Common.PassBill;
import com.example.ordersystem.Interface.ItemClickListener;
import com.example.ordersystem.Model.Request;
import com.example.ordersystem.ViewHolder.OrderAdapter;
import com.kosalgeek.asynctask.AsyncResponse;
import com.kosalgeek.asynctask.PostResponseAsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class orderStatus extends AppCompatActivity implements AsyncResponse, ItemClickListener {


    public RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;
    OrderAdapter adapter;
    Button goBack, statusRefresh;
    List<Request> requestList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status);

        statusRefresh = (Button)findViewById(R.id.refreshButton_viewOrder);

        statusRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestList.clear();
                loadOrders();
                adapter.notifyDataSetChanged();
            }
        });



        requestList = new ArrayList<>();

        recyclerView = (RecyclerView)findViewById(R.id.listOrders);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        goBack = (Button)findViewById(R.id.go_back_viewOrder);

        loadOrders();

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }




    private void loadOrders() {
        HashMap postData = new HashMap();
        postData.put("mobile", "android");
        postData.put("txtUser", Common.currentUser);
        postData.put("txtPass", Common.currentPass);
        postData.put("txtType", Common.currentType);

        PostResponseAsyncTask task = new PostResponseAsyncTask(orderStatus.this, postData, orderStatus.this);
        task.execute("http://10.0.2.2/client/viewOrders.php");
    }

    @Override
    public void processFinish(String results) {
        int x =1;
        try {
            Log.i("tagconvertstr", "["+results+"]");
            JSONArray products = new JSONArray(results);
            for (int i = products.length() - 1; i >= 0;i--){

                JSONObject dishesObject = products.getJSONObject(i);

                int requestNumber = dishesObject.getInt("idOrders");
                String requestTime = dishesObject.getString(String.valueOf("dateTime"));
                String requestStatus = dishesObject.getString("status");
                String requestCater = dishesObject.getString("cater");
                String requestFirstName = dishesObject.getString("firstName");
                String requestLastName = dishesObject.getString("lastName");
                String requestTotal = dishesObject.getString("total");
                String requestName = requestFirstName + " " + requestLastName;

                requestList.add(new Request(x, requestNumber, requestTime, requestStatus, requestCater, requestName, requestTotal));
                x++;
            }
            adapter = new OrderAdapter(this, requestList, this);
            recyclerView.setAdapter(adapter);
        } catch (JSONException e){
            e.printStackTrace();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(int position) {
        PassBill.billID = (requestList.get(position).getrequestNumber());
        PassBill.billNAME = (requestList.get(position).getRequestName());
        PassBill.billDATE = (requestList.get(position).getRequestdateTime());
        PassBill.billSTATUS = (requestList.get(position).getRequestStatus());
        PassBill.billCATER = (requestList.get(position).getRequestCater());
        PassBill.billTOTAL = (requestList.get(position).getRequestTotal());
        Intent intent = new Intent(this, orderBill.class);
        startActivity(intent);
    }

}
