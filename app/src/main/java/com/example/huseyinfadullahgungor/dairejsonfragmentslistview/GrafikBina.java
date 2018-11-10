package com.example.huseyinfadullahgungor.dairejsonfragmentslistview;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

/**
 * Created by huseyinfadullahgungor on 28.06.2018.
 */

public class GrafikBina extends Activity {

    Button geriButonu;
    BarChart barChart;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grafik_bina_layout);


        geriButonu=findViewById(R.id.geributonu);
        barChart=findViewById(R.id.chart);

        String [] aylarGrafikClassı=new String[13];
        String []  tutarGrafikClassı=new String[13];

        for (int i=0; i<BinaAylikFatura.faturaTutarı.length; i++){
            tutarGrafikClassı[i]=BinaAylikFatura.faturaTutarı[i];
        }

        for (int i=0; i<BinaAylikFatura.aylar.length; i++){
            aylarGrafikClassı[i]=BinaAylikFatura.aylar[i];
        }



        geri();

        ArrayList<BarEntry> entries=new ArrayList<>();

        for(int i=0; i<tutarGrafikClassı.length; i++){
            entries.add(new BarEntry(Float.parseFloat(tutarGrafikClassı[i]),i));
        }

//        entries.add(new BarEntry(8892f,0));
//        entries.add(new BarEntry(5215f,1));
//        entries.add(new BarEntry(4155f,2));
//        entries.add(new BarEntry(1247f,3));
//        entries.add(new BarEntry(7115f,4));
//        entries.add(new BarEntry(1439f,5));
//        entries.add(new BarEntry(8221f,6));
//        entries.add(new BarEntry(2342f,7));
//        entries.add(new BarEntry(5332f,8));
//        entries.add(new BarEntry(2320f,9));
//        entries.add(new BarEntry(1325f,10));
//        entries.add(new BarEntry(1119f,11));

        BarDataSet barDataSet=new BarDataSet(entries,"Aylar");
        ArrayList<String> labels= new ArrayList<>();

        for (int i=0; i<aylarGrafikClassı.length; i++){
            labels.add(aylarGrafikClassı[i]);
        }
//        labels.add("Jan");
//        labels.add("Feb");
//        labels.add("Mar");
//        labels.add("Apr");
//        labels.add("May");
//        labels.add("Jun");
//        labels.add("Jly");
//        labels.add("Aug");
//        labels.add("Sep");
//        labels.add("Oct");
//        labels.add("Nov");
//        labels.add("Dec");

        BarData data=new BarData(labels, barDataSet);
        data.setValueTextSize(13f);
        data.setValueTextColor(Color.BLACK);
        barChart.setData(data);

        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        barChart.animateY(2500);


    }




    public void geri() {
        geriButonu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                return;


            }

        });
    }
}
