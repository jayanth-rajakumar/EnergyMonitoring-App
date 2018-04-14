package com.em.energymonitor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.PointsGraphSeries;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Intent intent = getIntent();
        float[] arr=null;

        int arrkey=0;


        if(((MyApplication) this.getApplication()).c=='E'){
            arr=((MyApplication) this.getApplication()).arr;
            arrkey=((MyApplication) this.getApplication()).arrkey;
        }
        else if(((MyApplication) this.getApplication()).c=='V'){
            arr=((MyApplication) this.getApplication()).arr_vrms;
            arrkey=((MyApplication) this.getApplication()).arrkey_vrms;
        }
        else if(((MyApplication) this.getApplication()).c=='I'){
            arr=((MyApplication) this.getApplication()).arr_irms;
            arrkey=((MyApplication) this.getApplication()).arrkey_irms;
        }
        else if(((MyApplication) this.getApplication()).c=='F'){
            arr=((MyApplication) this.getApplication()).arr_freq;
            arrkey=((MyApplication) this.getApplication()).arrkey_freq;
        }
        GraphView graph = (GraphView) findViewById(R.id.graph);


        DataPoint[] dp = new DataPoint[1000];
        for(int i=0;i<=arrkey;i++){
            dp[i] = new DataPoint(i, arr[i]);
        }

        Log.d("d",Integer.toString(arrkey));
        //LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dp);



        LineGraphSeries<DataPoint> series;
        graph = (GraphView) findViewById(R.id.graph);
        DataPoint[] values = new DataPoint[arrkey];
        for(int i=0;i<arrkey;i++){
            DataPoint v = new DataPoint(i,arr[i]);
            values[i] = v;
        }
        series= new LineGraphSeries<>(values);

        //series.setShape(PointsGraphSeries.Shape.);
        graph.addSeries(series);
       // graph.getViewport().setMinY(0);
        //graph.getViewport().setMaxY(120);

        graph.getViewport().setYAxisBoundsManual(true);

        GridLabelRenderer gridLabel = graph.getGridLabelRenderer();
        gridLabel.setHorizontalAxisTitle("Samples");
       // gridLabel.setVerticalAxisTitle("Energy(WH)");

    }


}
