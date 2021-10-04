package com.example.ordersystem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ordersystem.Common.Common;
import com.kosalgeek.asynctask.AsyncResponse;
import com.kosalgeek.asynctask.PostResponseAsyncTask;

import java.util.HashMap;

public class menuAddCategory extends AppCompatActivity implements AsyncResponse {

    Button menuAddCatSaveB, menuAddCatBackB;
    EditText menuAddCatNameT, menuAddCatDetT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_add_category);

        menuAddCatBackB = (Button)findViewById(R.id.menuAddCatBack);
        menuAddCatSaveB = (Button)findViewById(R.id.menuAddCatSave);
        menuAddCatNameT = (EditText)findViewById(R.id.menuAddCatName);
        menuAddCatDetT = (EditText)findViewById(R.id.menuAddCatDetail);

        menuAddCatSaveB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap postData = new HashMap();
                postData.put("mobile", "android");
                postData.put("txtUser", Common.currentUser);
                postData.put("txtPass", Common.currentPass);
                postData.put("txtAccountType", Common.currentType);

                postData.put("txtMenuName", menuAddCatNameT.getText().toString());
                postData.put("txtMenuDetail", menuAddCatDetT.getText().toString());

                PostResponseAsyncTask task = new PostResponseAsyncTask(menuAddCategory.this, postData, menuAddCategory.this);
                task.execute("http://10.0.2.2/client/menuAddCategory.php");

            }
        });

        menuAddCatBackB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }



    @Override
    public void processFinish(String results) {
        if(results.equals("Complete")) {
            Toast.makeText(menuAddCategory.this, "Saved", Toast.LENGTH_SHORT).show();
            finish();
        }else{
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }
    }
}
