package com.example.ordersystem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import com.example.ordersystem.Common.Common;
import com.example.ordersystem.Model.Dish;
import com.example.ordersystem.Model.DishAdd;
import com.example.ordersystem.Model.Report;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.kosalgeek.asynctask.AsyncResponse;
import com.kosalgeek.asynctask.PostResponseAsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import static com.github.mikephil.charting.utils.ColorTemplate.MATERIAL_COLORS;

public class menuReport extends AppCompatActivity {

    Button menuReportBackB;
    BarChart menuReportBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_report);

        menuReportBackB = (Button) findViewById(R.id.menuReportBack);
        menuReportBar = (BarChart) findViewById(R.id.menuReportBar);


        menuReportBackB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        HashMap postData = new HashMap();
        postData.put("mobile", "android");
        postData.put("txtUser", Common.currentUser);
        postData.put("txtPass", Common.currentPass);
        postData.put("txtAccountType", Common.currentType);

        PostResponseAsyncTask task = new PostResponseAsyncTask(menuReport.this, postData, new AsyncResponse() {
            @Override
            public void processFinish(String results) {
                Log.i("tagconvertstr", "["+results+"]");
                ArrayList<BarEntry> barEntries = new ArrayList<>();
                ArrayList<String> names = new ArrayList<>();
                float maxs = 0;
                int count = 0;


                try {
                    JSONArray products = new JSONArray(results);
                    for (int i = 0; i < products.length(); i++) {

                        JSONObject dishesObject = products.getJSONObject(i);

                        int dishReportId = dishesObject.getInt("idReportDish");
                        String dishReportName = dishesObject.getString("dishReportName");
                        int dishReportValue = dishesObject.getInt("dishReportQuantity");
                        barEntries.add(new BarEntry(dishReportId,dishReportValue));
                        names.add(dishReportName);
                        maxs++;
                        count++;


                    }
                    BarDataSet barDataSet = new BarDataSet(barEntries, "Dishes served of the day");
                    BarData theData = new BarData(barDataSet);
                    menuReportBar.setData(theData);
                    menuReportBar.setVisibleXRangeMaximum(maxs);
                    XAxis xAxis = menuReportBar.getXAxis();
                    xAxis.setValueFormatter(new IndexAxisValueFormatter(names));
                    xAxis.setLabelCount(count);
                    xAxis.setGranularity(1f);
                    xAxis.setCenterAxisLabels(true);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        task.execute("http://10.0.2.2/client/menuReportValues.php");



        menuReportBar.setTouchEnabled(true);
        menuReportBar.setDragEnabled(true);
        menuReportBar.setScaleEnabled(false);
    }


}
