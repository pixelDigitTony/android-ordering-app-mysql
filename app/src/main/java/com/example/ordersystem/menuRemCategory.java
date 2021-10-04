package com.example.ordersystem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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

public class menuRemCategory extends AppCompatActivity {

    Spinner menuRemCatD;
    TextView menuRemCatDet;
    Button menuRemCatSave, menuRemCatBack;
    List<Menu> menuList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_rem_category);

        menuList = new ArrayList<>();

        menuRemCatD = (Spinner)findViewById(R.id.menuRemCatDrop);
        menuRemCatDet = (TextView) findViewById(R.id.menuRemCatDetail);
        menuRemCatSave = (Button)findViewById(R.id.menuRemCatSave);
        menuRemCatBack = (Button)findViewById(R.id.menuRemCatBack);

        loadMenu();

        menuRemCatSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap postData = new HashMap();
                postData.put("mobile", "android");
                postData.put("txtUser", Common.currentUser);
                postData.put("txtPass", Common.currentPass);
                postData.put("txtAccountType", Common.currentType);
                postData.put("txtmenuRemCatID", String.valueOf(menuList.get(menuRemCatD.getSelectedItemPosition()).getMenuId()));

                PostResponseAsyncTask task = new PostResponseAsyncTask(menuRemCategory.this, postData, new AsyncResponse() {
                    @Override
                    public void processFinish(String results) {
                        Log.i("tagconvertstr", "["+results+"]");
                        if(results.equals("Complete")){
                            Toast.makeText(menuRemCategory.this, "Category, Edited", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                task.execute("http://10.0.2.2/client/menuRemCat.php");
                finish();
            }
        });

        menuRemCatBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        menuRemCatD.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Menu menu = (Menu)parent.getSelectedItem();
                menuRemCatDet.setText(menu.getMenuDescrip());
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

        PostResponseAsyncTask task = new PostResponseAsyncTask(menuRemCategory.this, postData, new AsyncResponse() {
            @Override
            public void processFinish(String results) {
                Log.i("tagconvertstr", "["+results+"]");
                try {
                    JSONArray products = new JSONArray(results);
                    for (int i = 0; i < products.length(); i++) {

                        JSONObject dishesObject = products.getJSONObject(i);

                        int dishRemCatId = dishesObject.getInt("idMenu");
                        String dishRemCatName = dishesObject.getString("menuName");
                        String dishRemCatDescrip = dishesObject.getString("menuDescrip");

                        menuList.add(new Menu(dishRemCatId, dishRemCatName, dishRemCatDescrip));
                    }

                    ArrayAdapter<Menu> adapter = new ArrayAdapter<Menu>(menuRemCategory.this, android.R.layout.simple_spinner_item, menuList);
                    menuRemCatD.setAdapter(adapter);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        task.execute("http://10.0.2.2/client/viewMenu.php");
    }
}
