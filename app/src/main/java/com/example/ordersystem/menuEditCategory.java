package com.example.ordersystem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
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

public class menuEditCategory extends AppCompatActivity {

    Spinner menuEditCatD;
    EditText menuEditCatName, menuEditCatDetail;
    Button menuEditCatBack, menuEditCatSave;
    List<Menu> menuList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_edit_category);

        menuList = new ArrayList<>();

        loadMenu();

        menuEditCatD = (Spinner)findViewById(R.id.menuEditCatDrop);
        menuEditCatName = (EditText)findViewById(R.id.menuEditCatName);
        menuEditCatDetail = (EditText) findViewById(R.id.menuEditCatDet);
        menuEditCatSave = (Button)findViewById(R.id.menuEditCatSave);
        menuEditCatBack = (Button)findViewById(R.id.menuEditCatBack);



        menuEditCatD.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Menu menu = (Menu)parent.getSelectedItem();
                menuEditCatName.setText(menu.getMenuName());
                menuEditCatDetail.setText(menu.getMenuDescrip());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        menuEditCatBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        menuEditCatSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                HashMap postData = new HashMap();
                postData.put("mobile", "android");
                postData.put("txtUser", Common.currentUser);
                postData.put("txtPass", Common.currentPass);
                postData.put("txtAccountType", Common.currentType);

                postData.put("txtEditCategoryName", menuEditCatName.getText().toString());
                postData.put("txtEditCategoryDetail", menuEditCatDetail.getText().toString());
                postData.put("txtEditCategoryID", String.valueOf(menuList.get(menuEditCatD.getSelectedItemPosition()).getMenuId()));

                PostResponseAsyncTask task = new PostResponseAsyncTask(menuEditCategory.this, postData, new AsyncResponse() {
                    @Override
                    public void processFinish(String results) {
                        Log.i("tagconvertstr", "["+results+"]");
                        if(results.equals("Complete")){
                            Toast.makeText(menuEditCategory.this, "Category, Edited", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                task.execute("http://10.0.2.2/client/menuEditCategory.php");
                finish();

            }
        });


    }

    private void loadMenu() {
        HashMap postData = new HashMap();
        postData.put("mobile", "android");
        postData.put("txtUser", Common.currentUser);
        postData.put("txtPass", Common.currentPass);
        postData.put("txtAccountType", Common.currentType);

        PostResponseAsyncTask task = new PostResponseAsyncTask(menuEditCategory.this, postData, new AsyncResponse() {
            @Override
            public void processFinish(String results) {
                Log.i("tagconvertstr", "["+results+"]");
                try {
                    JSONArray products = new JSONArray(results);
                    for (int i = 0; i < products.length(); i++) {

                        JSONObject dishesObject = products.getJSONObject(i);

                        int dishEditMenuId = dishesObject.getInt("idMenu");
                        String dishEditMenuName = dishesObject.getString("menuName");
                        String dishEditMenuDescrip = dishesObject.getString("menuDescrip");

                        menuList.add(new Menu(dishEditMenuId, dishEditMenuName, dishEditMenuDescrip));
                    }

                    ArrayAdapter<Menu> adapter = new ArrayAdapter<Menu>(menuEditCategory.this, android.R.layout.simple_spinner_item, menuList);
                    menuEditCatD.setAdapter(adapter);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        task.execute("http://10.0.2.2/client/viewMenu.php");
    }



}
