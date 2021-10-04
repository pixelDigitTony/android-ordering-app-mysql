package com.example.ordersystem;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.ordersystem.Common.Common;
import com.example.ordersystem.Common.Data;
import com.example.ordersystem.Common.PassBill;
import com.example.ordersystem.Database.Database;
import com.example.ordersystem.Model.Dish;
import com.example.ordersystem.Model.Order;
import com.kosalgeek.asynctask.AsyncResponse;
import com.kosalgeek.asynctask.PostResponseAsyncTask;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.HashMap;

public class dishDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dish_detail);

        TextView dishName, dishPrice, dishDescrip, dishAvaila;
        ImageView dishImage;
        CollapsingToolbarLayout collapsingToolbarLayout;
        FloatingActionButton btnCart, btnBack;
        final int dishAvailNum;
        final ElegantNumberButton numberButton;



        numberButton = (ElegantNumberButton)findViewById(R.id.number_button);
        btnCart = (FloatingActionButton)findViewById(R.id.btnCart);
        btnBack = (FloatingActionButton)findViewById(R.id.btnBack);



        dishName = (TextView)findViewById(R.id.dish_detail);
        dishPrice = (TextView)findViewById(R.id.dish_price);
        dishImage = (ImageView)findViewById(R.id.dish_image);
        dishDescrip = (TextView)findViewById(R.id.dish_description);
        dishAvaila = (TextView)findViewById(R.id.available_dish_num);


        collapsingToolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.collapsing);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppbar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppbar);


        Intent i = getIntent();
        final String dishDescript = Data.dishDESCRIP;
        final String dishId = String.valueOf(Data.dishID);
        final String dishNAME = Data.dishNAME;
        final Double dishPRICE = Data.dishPRICE;
        final String dishAvail = Data.dishAVAILA;


        dishName.setText(dishNAME);
        dishPrice.setText(""+dishPRICE);
        dishDescrip.setText(dishDescript);
        dishAvaila.setText(dishAvail);
        dishAvailNum = Integer.parseInt(dishAvail);

        numberButton.setRange(0, dishAvailNum);

        collapsingToolbarLayout.setTitle(dishNAME);



        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(numberButton.getNumber().equals("0")){
                    Toast.makeText(dishDetail.this, "Get at least 1", Toast.LENGTH_SHORT).show();
                }else{
                    new Database(getApplicationContext()).addToCart(new Order(
                            dishId,
                            dishNAME,
                            dishPRICE,
                            numberButton.getNumber()
                    ));
                    Toast.makeText(dishDetail.this, "Added to Cart", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(getApplicationContext(), orderDish.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivityForResult(intent,0);
                    finish();
                }
            }
        });


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), orderDish.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(intent,0);
                finish();
            }
        });

    }


}
