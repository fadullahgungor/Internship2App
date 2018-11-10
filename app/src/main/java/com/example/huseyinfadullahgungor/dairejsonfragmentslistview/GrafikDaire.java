package com.example.huseyinfadullahgungor.dairejsonfragmentslistview;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;


/**
 * Created by huseyinfadullahgungor on 3.07.2018.
 */

public class GrafikDaire extends Activity {


    Button geriButonuDaire;
    LineChart lineChart;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grafik_daire_layout);


        geriButonuDaire=findViewById(R.id.geributonudaire);
        lineChart=findViewById(R.id.linechart);



        String [] aylarGrafikDaireClassı=new String[13];
        String []  tutarGrafikDaireClassı=new String[13];

        for (int i=0; i<DaireAylikFatura.faturaTutarıDaire.length; i++){


                tutarGrafikDaireClassı[i] = DaireAylikFatura.faturaTutarıDaire[i];



        }

        for (int i=0; i<DaireAylikFatura.aylarDaire.length; i++){


                aylarGrafikDaireClassı[i] = DaireAylikFatura.aylarDaire[i];

        }



        geriDaire();

        ArrayList<Entry> entries=new ArrayList<>();

        for(int i=0; i<tutarGrafikDaireClassı.length; i++){
            if(!aylarGrafikDaireClassı[i].equals("Sıfırlama"))
            entries.add(new Entry(Float.parseFloat(tutarGrafikDaireClassı[i]),i));
        }


        LineDataSet lineDataSet=new LineDataSet(entries,"Aylar");
        lineDataSet.setLineWidth(3f);
        ArrayList<String> labels= new ArrayList<>();

        for (int i=0; i<aylarGrafikDaireClassı.length; i++){
            if(!aylarGrafikDaireClassı[i].equals("Sıfırlama"))
                labels.add(aylarGrafikDaireClassı[i]);
        }

        LineData data=new LineData(labels, lineDataSet);
        lineChart.setData(data);
        data.setValueTextSize(18f);
        data.setValueTextColor(Color.BLACK);


        lineDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        lineChart.animateX(4000);





    }




    public void geriDaire() {
        geriButonuDaire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                return;


            }

        });
    }
}
