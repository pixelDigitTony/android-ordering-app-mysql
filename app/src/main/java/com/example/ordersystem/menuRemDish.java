package com.example.ordersystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ordersystem.Common.Common;
import com.example.ordersystem.Model.Dish;
import com.example.ordersystem.Model.DishAdd;
import com.example.ordersystem.Model.Menu;
import com.kosalgeek.asynctask.AsyncResponse;
import com.kosalgeek.asynctask.PostResponseAsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class menuRemDish extends AppCompatActivity {

    Spinner menuRemDishNameD, menuRemDishCatNameD;
    TextView menuRemDishDetailT;
    Button  menuRemDishSave, menuRemDishBack;
    List<Menu> menuList;
    List<DishAdd> dishAddList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_rem_dish);

        menuList = new ArrayList<>();
        dishAddList = new ArrayList<>();

        menuRemDishNameD = (Spinner)findViewById(R.id.remDishDrop);
        menuRemDishCatNameD = (Spinner)findViewById(R.id.remDishCatDrop);
        menuRemDishDetailT = (TextView) findViewById(R.id.remDishDet);
        menuRemDishSave = (Button)findViewById(R.id.menuRemDishSave);
        menuRemDishBack = (Button)findViewById(R.id.menuRemDishBack);

        loadMenu();

        menuRemDishBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        menuRemDishCatNameD.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Menu menu = (Menu)parent.getSelectedItem();
                loadDish(menu);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        menuRemDishSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap postData = new HashMap();
                postData.put("mobile", "android");
                postData.put("txtUser", Common.currentUser);
                postData.put("txtPass", Common.currentPass);
                postData.put("txtAccountType", Common.currentType);
                postData.put("txtmenuRemDishID", String.valueOf(dishAddList.get(menuRemDishNameD.getSelectedItemPosition()).getDishDishId()));
                postData.put("txtmenuRemDishCatID", String.valueOf(menuList.get(menuRemDishCatNameD.getSelectedItemPosition()).getMenuId()));

                PostResponseAsyncTask task = new PostResponseAsyncTask(menuRemDish.this, postData, new AsyncResponse() {
                    @Override
                    public void processFinish(String results) {
                        Log.i("tagconvertstr", "["+results+"]");
                        if(results.equals("Complete")){
                            Toast.makeText(menuRemDish.this, "Category, Edited", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                task.execute("http://10.0.2.2/client/menuRemDish.php");
                finish();
            }
        });

    }

    private void loadDish(Menu menu){
        HashMap postData = new HashMap();
        postData.put("mobile", "android");
        postData.put("txtUser", Common.currentUser);
        postData.put("txtPass", Common.currentPass);
        postData.put("txtAccountType", Common.currentType);

        postData.put("txtmenuRemDishCatID", String.valueOf(menu.getMenuId()));

        PostResponseAsyncTask task = new PostResponseAsyncTask(menuRemDish.this, postData, new AsyncResponse() {
            @Override
            public void processFinish(String results) {
                Log.i("tagconvertstr", "["+results+"]");
                try {
                    JSONArray products = new JSONArray(results);
                    for (int i = 0; i < products.length(); i++) {

                        JSONObject dishesObject = products.getJSONObject(i);

                        int dishAddDishId = dishesObject.getInt("idDish");
                        String dishAddDishName = dishesObject.getString("dishName");
                        String dishAddDishDescrip = dishesObject.getString("dishDescrip");



                        dishAddList.add(new DishAdd(dishAddDishId, dishAddDishName, dishAddDishDescrip));

                    }

                    ArrayAdapter<DishAdd> adapter = new ArrayAdapter<DishAdd>(menuRemDish.this, android.R.layout.simple_spinner_item, dishAddList);
                    menuRemDishNameD.setAdapter(adapter);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        task.execute("http://10.0.2.2/client/viewDishMenu.php");

        menuRemDishNameD.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                DishAdd dishAdd = (DishAdd) parent.getSelectedItem();
                menuRemDishDetailT.setText(dishAdd.getDishDishDescrip());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private void loadMenu() {
        HashMap postData = new HashMap();
        postData.put("mobile", "android");
        postData.put("txtUser", Common.currentUser);
        postData.put("txtPass", Common.currentPass);
        postData.put("txtAccountType", Common.currentType);

        PostResponseAsyncTask task = new PostResponseAsyncTask(menuRemDish.this, postData, new AsyncResponse() {
            @Override
            public void processFinish(String results) {
                Log.i("tagconvertstr", "["+results+"]");
                try {
                    JSONArray products = new JSONArray(results);
                    for (int i = 0; i < products.length(); i++) {

                        JSONObject dishesObject = products.getJSONObject(i);

                        int dishAddMenuId = dishesObject.getInt("idMenu");
                        String dishAddMenuName = dishesObject.getString("menuName");
                        String dishAddMenuDescrip = dishesObject.getString("menuDescrip");

                        menuList.add(new Menu(dishAddMenuId, dishAddMenuName, dishAddMenuDescrip));
                    }

                    ArrayAdapter<Menu> adapter = new ArrayAdapter<Menu>(menuRemDish.this, android.R.layout.simple_spinner_item, menuList);
                    menuRemDishCatNameD.setAdapter(adapter);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        task.execute("http://10.0.2.2/client/viewMenu.php");


    }

}
