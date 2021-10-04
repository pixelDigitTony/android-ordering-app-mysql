package com.example.ordersystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

public class menuSetting extends AppCompatActivity {


    CardView createCat, remCat, addDish,remDish, menuBack, menuRep, editCat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_setting);


        createCat = (CardView)findViewById(R.id.createCat);
        remCat = (CardView)findViewById(R.id.remCat);
        addDish = (CardView)findViewById(R.id.addDish);
        remDish = (CardView)findViewById(R.id.remDish);
        menuBack = (CardView)findViewById(R.id.menuSetBack);
        menuRep = (CardView)findViewById(R.id.menuReport);
        editCat = (CardView)findViewById(R.id.menuEditCat);


        createCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent orderMenuIntent = new Intent(menuSetting.this, menuAddCategory.class);
                startActivity(orderMenuIntent);
            }
        });

        remCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent orderMenuIntent = new Intent(menuSetting.this, menuRemCategory.class);
                startActivity(orderMenuIntent);
            }
        });

        addDish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent orderMenuIntent = new Intent(menuSetting.this, menuAddDish.class);
                startActivity(orderMenuIntent);
            }
        });

        editCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent orderMenuIntent = new Intent(menuSetting.this, menuEditCategory.class);
                startActivity(orderMenuIntent);
            }
        });

        remDish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent orderMenuIntent = new Intent(menuSetting.this, menuRemDish.class);
                startActivity(orderMenuIntent);
            }
        });

        menuBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), orderMenu.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(intent,0);
                finish();
            }
        });

        menuRep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent orderMenuIntent = new Intent(menuSetting.this, menuReport.class);
                startActivity(orderMenuIntent);
            }
        });



    }

}
