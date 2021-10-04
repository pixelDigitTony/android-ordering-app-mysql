package com.example.ordersystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.ordersystem.Common.Common;
import com.example.ordersystem.Common.Data;
import com.example.ordersystem.Common.SelectedMenu;
import com.example.ordersystem.Model.Dish;
import com.example.ordersystem.ViewHolder.DishAdapter;
import com.kosalgeek.asynctask.AsyncResponse;
import com.kosalgeek.asynctask.PostResponseAsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class orderDish extends AppCompatActivity implements AsyncResponse, DishAdapter.ItemClickListener {
    RecyclerView recyclerView;
    DishAdapter adapter;
    List<Dish> dishList;
    int imgsrc = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dishlist);
        dishList = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_food);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton bak = (FloatingActionButton) findViewById(R.id.menuBack);
        bak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), orderMenu.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(intent,0);
                finish();
            }
        });

        Intent i = getIntent();
        String menuID = SelectedMenu.menuName;
        if(getIntent() != null)
            if(!menuID.isEmpty() && menuID != null) {
                dishListFood(menuID);
            }

    }

    private void dishListFood(String menuID) {
        HashMap postData = new HashMap();
        postData.put("mobile", "android");
        postData.put("txtUser", Common.currentUser);
        postData.put("txtPass", Common.currentPass);

        postData.put("txtDishCat", menuID);

        PostResponseAsyncTask task = new PostResponseAsyncTask(orderDish.this, postData, new AsyncResponse() {
            @Override
            public void processFinish(String results) {
                Log.i("tagconvertstr", "["+results+"]");
                try {
                    JSONArray products = new JSONArray(results);
                    for (int i = 0; i < products.length();i++){

                        JSONObject dishesObject = products.getJSONObject(i);

                        int dishId = dishesObject.getInt("idDish");
                        String dishName = dishesObject.getString("dishName");
                        double dishPrice = dishesObject.getDouble("price");
                        String dishDescrip = dishesObject.getString("dishDescrip");
                        String dishAvail = dishesObject.getString("avail");

                        if(dishAvail.equals("0")){
                            imgsrc = R.mipmap.ic_knights_table;
                            dishList.add(new Dish(dishId, dishName, dishPrice, imgsrc, dishDescrip, dishAvail));
                        }else{
                            imgsrc =  R.drawable.ic_launcher_background;
                            dishList.add(new Dish(dishId, dishName, dishPrice, imgsrc, dishDescrip, dishAvail));
                        }



                    }
                    adapter = new DishAdapter(orderDish.this, dishList, orderDish.this);
                    recyclerView.setAdapter(adapter);
                } catch (JSONException e){
                    e.printStackTrace();
                }
            }
        });
        task.execute("http://10.0.2.2/client/viewDishMenu.php");


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        adapter.notifyDataSetChanged();
    }


    @Override
    public void onClick(int position) {

        Intent intent = new Intent(this, dishDetail.class);
        int dishId = dishList.get(position).getDishId();
        String dishName = dishList.get(position).getDishName();
        double dishPrice = dishList.get(position).getDishPrice();
        String dishDescrip = dishList.get(position).getDishDescrip();
        String dishAvail = dishList.get(position).getDishAvail();
        Data.dishID = dishId;
        Data.dishNAME = dishName;
        Data.dishPRICE = dishPrice;
        Data.dishDESCRIP = dishDescrip;
        Data.dishAVAILA = dishAvail;
        startActivity(intent);
    }

    @Override
    public void processFinish(String results) {

    }
}