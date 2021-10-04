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
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ordersystem.Common.Common;
import com.example.ordersystem.Database.Database;
import com.example.ordersystem.Model.Order;
import com.example.ordersystem.Model.Verify;
import com.example.ordersystem.ViewHolder.CartAdapter;
import com.kosalgeek.asynctask.AsyncResponse;
import com.kosalgeek.asynctask.PostResponseAsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringJoiner;

public class Cart extends AppCompatActivity implements AsyncResponse {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    List<Order> cart = new ArrayList<>();
    CartAdapter adapter;
    TextView txtTotalPrice;
    Button btnPlace, cleanCart, headBack;
    List<Verify> verify = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        recyclerView = (RecyclerView)findViewById(R.id.listCart);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        txtTotalPrice=(TextView)findViewById(R.id.total);
        btnPlace = (Button) findViewById(R.id.btnPlaceOrder);
        cleanCart = (Button) findViewById(R.id.clear_items);
        headBack = (Button) findViewById(R.id.head_back);


        loadListFood();

        btnPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alertDialog();
            }
        });



    }

    private void alertDialog() {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(Cart.this);
        alertDialog.setTitle("Warning");
        alertDialog.setMessage("Is this the final order?");
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        alertDialog.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                verifyAvailability();
                dialog.dismiss();
            }
        });
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.show();
    }

    private void verifyAvailability() {
        HashMap postData2 = new HashMap();
        postData2.put("mobile", "android");
        postData2.put("txtUser", Common.currentUser);
        postData2.put("txtPass", Common.currentPass);
        postData2.put("txtAccountType", Common.currentType);

        PostResponseAsyncTask task2 = new PostResponseAsyncTask(Cart.this, postData2, new AsyncResponse() {
            @Override
            public void processFinish(String results) {
                ArrayList<String> messageList = new ArrayList<>();
                String message = "";
                Log.i("tagconvertstr", "["+results+"]");
                try {

                    JSONArray products = new JSONArray(results);
                    for (int i = 0; i < products.length(); i++) {

                        JSONObject dishesObject = products.getJSONObject(i);

                        int dishStatusID = dishesObject.getInt("dishStatusID");
                        String dishStatusName = dishesObject.getString("dishStatusName");
                        int dishStatusOrderT = Integer.parseInt(dishesObject.getString("dishStatusOrderT"));
                        int dishStatusAvailT = Integer.parseInt(dishesObject.getString("dishStatusAvailT"));

                        if(dishStatusOrderT>dishStatusAvailT){
                             message += dishStatusName+" ";
                        }else{

                        }

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


                if (message.equals("")) {
                    Toast.makeText(Cart.this, "Submitting...", Toast.LENGTH_SHORT).show();
                    HashMap postData = new HashMap();
                    postData.put("mobile", "android");
                    postData.put("txtUser", Common.currentUser);
                    postData.put("txtPass", Common.currentPass);
                    postData.put("txtAccountType", Common.currentType);
                    postData.put("txtTotalPrice", txtTotalPrice.getText().toString());

                    PostResponseAsyncTask task = new PostResponseAsyncTask(Cart.this, postData, Cart.this);
                    task.execute("http://10.0.2.2/client/order.php");

                    loadDishList();

                    new Database(getBaseContext()).cleanCart();
                } else {
                    showAlertDialogPending(message);
                }

            }
        });
        task2.execute("http://10.0.2.2/client/verifyAvailabilityPending.php");
    }

    private void showAlertDialogPending(String message) {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(Cart.this);
        alertDialog.setTitle("Warning");
        alertDialog.setMessage("Pending Orders have exceeded available stocks of "+ message + ". orders with the following dishes may be cancelled." + " Continue?");
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        alertDialog.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                HashMap postData = new HashMap();
                postData.put("mobile", "android");
                postData.put("txtUser", Common.currentUser);
                postData.put("txtPass", Common.currentPass);
                postData.put("txtAccountType", Common.currentType);
                postData.put("txtTotalPrice", txtTotalPrice.getText().toString());

                PostResponseAsyncTask task = new PostResponseAsyncTask(Cart.this, postData, Cart.this);
                task.execute("http://10.0.2.2/client/order.php");

                loadDishList();

                new Database(getBaseContext()).cleanCart();
                dialog.dismiss();
            }
        });
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.show();
    }



    private void loadDishList() {
        cart = new Database(this).getCart();
        adapter = new CartAdapter(cart,this);
        recyclerView.setAdapter(adapter);
        for(Order order:cart) {

            HashMap postData = new HashMap();
            postData.put("mobile", "android");
            postData.put("txtUser", Common.currentUser);
            postData.put("txtPass", Common.currentPass);
            postData.put("txtAccountType", Common.currentType);
            postData.put("txtDishName", order.getFoodName());
            postData.put("txtQuantity", order.getFoodQuantity());

            PostResponseAsyncTask task = new PostResponseAsyncTask(Cart.this, postData, Cart.this);
            task.execute("http://10.0.2.2/client/orderDishes.php");

        }
    }


    private void loadListFood() {
        cart = new Database(this).getCart();
        adapter = new CartAdapter(cart,this);
        recyclerView.setAdapter(adapter);

        double total = 0;
        for(Order order:cart) {
            try {
                total += (order.getFoodPrice()) * (Double.parseDouble(order.getFoodQuantity()));
                txtTotalPrice.setText(String.valueOf(total));
            }catch (NumberFormatException e){

            }
        }

        cleanCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Database(getBaseContext()).cleanCart();
                Toast.makeText(Cart.this, "Cart Cleaned", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), orderMenu.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(intent,0);
                finish();
            }
        });
        headBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), orderMenu.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(intent,0);
                finish();
            }
        });
    }

    @Override
    public void processFinish(String result) {
        Log.i("tagconvertstr", "["+result+"]");

            Toast.makeText(Cart.this, "Thank you, order placed!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), orderMenu.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT|Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivityForResult(intent,0);
            finish();

    }
}
