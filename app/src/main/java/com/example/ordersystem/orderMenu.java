package com.example.ordersystem;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ordersystem.Common.SelectedMenu;
import com.example.ordersystem.Model.Category;
import com.example.ordersystem.ViewHolder.MenuAdapter;
import com.kosalgeek.asynctask.AsyncResponse;
import com.kosalgeek.asynctask.PostResponseAsyncTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.example.ordersystem.Common.Common;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class orderMenu extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, MenuAdapter.ItemClickListener {

    public static final String EXTRA_TEXT = "com.example.ordersystem.EXTRA_TEXT";

    RecyclerView recyclerView;
    MenuAdapter adapter;
    List<Category> categoryList;
    TextView user;
    ClipData.Item admin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_menu);

        categoryList = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_menu);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Menu");
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cartIntent = new Intent(orderMenu.this, Cart.class);
                startActivity(cartIntent);
            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(orderMenu.this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if(Common.currentType.equals("ADMIN") || Common.currentType.equals("COOK") || Common.currentType.equals("GROCER")){

        }else {
            Menu nav_Menu = navigationView.getMenu();
            nav_Menu.findItem(R.id.nav_menu_sched).setVisible(false);
            nav_Menu.findItem(R.id.nav_menu_sched).setEnabled(false);
            nav_Menu.findItem(R.id.nav_menu_sched).setCheckable(false);
        }

        View headerview = navigationView.getHeaderView(0);

        adapter = new MenuAdapter(this, categoryList, this);
        recyclerView.setAdapter(adapter);

        loadMenu();

    }



    private void loadMenu() {
        HashMap postData = new HashMap();
        postData.put("mobile", "android");
        postData.put("txtUser", Common.currentUser);
        postData.put("txtPass", Common.currentPass);
        postData.put("txtAccountType", Common.currentType);

        PostResponseAsyncTask task = new PostResponseAsyncTask(orderMenu.this, postData, new AsyncResponse() {
            @Override
            public void processFinish(String results) {
                Log.i("tagconvertstr", "["+results+"]");
                int count1 = 0;
                try {
                    JSONArray products = new JSONArray(results);
                    for (int i = 0; i < products.length();i++){

                        JSONObject dishesObject = products.getJSONObject(i);

                        count1++;
                        String menuName = dishesObject.getString("menuName");
                        int imgsrc = R.drawable.ic_launcher_background;
                        categoryList.add(new Category(count1, menuName, imgsrc));

                    }
                    adapter = new MenuAdapter(orderMenu.this, categoryList, orderMenu.this);
                    recyclerView.setAdapter(adapter);
                } catch (JSONException e){
                    e.printStackTrace();
                }
            }
        });
        task.execute("http://10.0.2.2/client/viewMenu.php");

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        user = (TextView) findViewById(R.id.userAccountName);
        user.setText(Common.currentName);
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.order_menu, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_menu_sched) {
                Intent orderMenuIntent = new Intent(orderMenu.this, menuReport.class);
                startActivity(orderMenuIntent);
        }
        else if (id == R.id.nav_cart){
            Intent orderMenuIntent = new Intent(orderMenu.this, Cart.class);
            startActivity(orderMenuIntent);
        }
        else if (id == R.id.nav_profile){
            Intent orderMenuIntent = new Intent(orderMenu.this, profile.class);
            startActivity(orderMenuIntent);
        }
        else if (id == R.id.nav_order){
            Intent orderMenuIntent = new Intent(orderMenu.this, orderStatus.class);
            startActivity(orderMenuIntent);
        }
        else if (id == R.id.nav_log_out){
            Intent orderMenuIntent = new Intent(orderMenu.this, Home.class);
            startActivity(orderMenuIntent);

            finishAfterTransition();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(int position) {
        Toast.makeText(this, categoryList.get(position).getName(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, orderDish.class);
        SelectedMenu.menuName = categoryList.get(position).getName();
        startActivity(intent);
    }
}
