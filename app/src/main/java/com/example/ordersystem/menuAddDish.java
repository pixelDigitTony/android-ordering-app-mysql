package com.example.ordersystem;

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

public class menuAddDish extends AppCompatActivity {

    Spinner menuAddDishNamD, menuAddDishCatD;
    TextView menuAddDishDetT;
    Button menuAddDishBackB, menuAddDishSaveB;
    List<Menu> menuList;
    List<DishAdd> dishAddList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_add_dish);

        menuList = new ArrayList<>();
        dishAddList = new ArrayList<>();

        menuAddDishNamD = (Spinner)findViewById(R.id.menuAddDishName);
        menuAddDishCatD = (Spinner)findViewById(R.id.menuAddDishCategory);
        menuAddDishDetT = (TextView) findViewById(R.id.menuAddDishDetail);
        menuAddDishSaveB = (Button)findViewById(R.id.menuAddDishSave);
        menuAddDishBackB = (Button)findViewById(R.id.menuAddDishBack);

        loadMenu();

        menuAddDishCatD.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Menu menu = (Menu)parent.getSelectedItem();
                int addMenuID = menu.getMenuId();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        loadDish();

        menuAddDishNamD.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                DishAdd dishAdd = (DishAdd)parent.getSelectedItem();
                menuAddDishDetT.setText(dishAdd.getDishDishDescrip());

                int addDishID = dishAdd.getDishDishId();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        menuAddDishSaveB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap postData = new HashMap();
                postData.put("mobile", "android");
                postData.put("txtUser", Common.currentUser);
                postData.put("txtPass", Common.currentPass);
                postData.put("txtAccountType", Common.currentType);

                postData.put("txtAddDishName", String.valueOf(dishAddList.get(menuAddDishNamD.getSelectedItemPosition()).getDishDishId()));
                postData.put("txtAddMenuName", String.valueOf(menuList.get(menuAddDishCatD.getSelectedItemPosition()).getMenuId()));

                PostResponseAsyncTask task = new PostResponseAsyncTask(menuAddDish.this, postData, new AsyncResponse() {
                    @Override
                    public void processFinish(String results) {
                        Log.i("tagconvertstr", "["+results+"]");
                        if(results.equals("Complete")){
                            Toast.makeText(menuAddDish.this, "Dish Added to Category", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                task.execute("http://10.0.2.2/client/menuAddDish.php");
            }
        });
        menuAddDishBackB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void loadDish() {

        HashMap postData = new HashMap();
        postData.put("mobile", "android");
        postData.put("txtUser", Common.currentUser);
        postData.put("txtPass", Common.currentPass);
        postData.put("txtAccountType", Common.currentType);

        PostResponseAsyncTask task = new PostResponseAsyncTask(menuAddDish.this, postData, new AsyncResponse() {
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

                    ArrayAdapter<DishAdd> adapter = new ArrayAdapter<DishAdd>(menuAddDish.this, android.R.layout.simple_spinner_item, dishAddList);
                    menuAddDishNamD.setAdapter(adapter);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        task.execute("http://10.0.2.2/client/viewDish.php");

    }

    private void loadMenu() {
        HashMap postData = new HashMap();
        postData.put("mobile", "android");
        postData.put("txtUser", Common.currentUser);
        postData.put("txtPass", Common.currentPass);
        postData.put("txtAccountType", Common.currentType);

        PostResponseAsyncTask task = new PostResponseAsyncTask(menuAddDish.this, postData, new AsyncResponse() {
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

                    ArrayAdapter<Menu> adapter = new ArrayAdapter<Menu>(menuAddDish.this, android.R.layout.simple_spinner_item, menuList);
                    menuAddDishCatD.setAdapter(adapter);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        task.execute("http://10.0.2.2/client/viewMenu.php");

    }


}


