package com.em.energymonitor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import  java.lang.*;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;
    Button onoff = null;
    float[] arr = new float[1000];
    float[] arr_vrms =new float[1000];
    float[] arr_irms =new float[1000];
    float[] arr_freq =new float[1000];

    int arrkey=0,arrkey_vrms=0,arrkey_irms=0,arrkey_freq=0;

    int switchtoggle=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        onoff=(Button)findViewById(R.id.button4);

        mDatabase = FirebaseDatabase.getInstance().getReference().child( ((MyApplication) this.getApplication()).username);


        Query lastQuery = mDatabase.child("mp").orderByKey().limitToLast(1);
        mDatabase.addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot dataSnapshot){

                TextView t_vrms = (TextView)findViewById(R.id.textView3);
                TextView t_irms = (TextView)findViewById(R.id.textView9);
                TextView t_frequency= (TextView)findViewById(R.id.textView10);
                TextView t_powerfactor= (TextView)findViewById(R.id.textView11);
                TextView t_power= (TextView)findViewById(R.id.textView12);
                TextView t_energy= (TextView)findViewById(R.id.textView13);
                TextView t_apppower= (TextView)findViewById(R.id.textView19);
                TextView t_timestamp= (TextView)findViewById(R.id.textView15);
                String status="";

                try{
                    status=dataSnapshot.child("Status").getValue().toString();
                //    Log.d("TAG",status);
                }catch(Exception e){
                    e.printStackTrace();
                    status="1";
                }


                if(status.equals("1"))
                {
                    onoff.setText("Turn Off");
                    switchtoggle=1;
                }
                else
                {
                    switchtoggle=0;
                    onoff.setText("Turn On");
                }

                arrkey_vrms=0;
                for (DataSnapshot subsnap: dataSnapshot.child("VRMS").getChildren()) {
                t_vrms.setText(subsnap.getValue().toString() + "V");
                    arr_vrms[arrkey_vrms++]=Float.parseFloat(subsnap.getValue().toString());
                }

                arrkey_irms=0;
                for (DataSnapshot subsnap: dataSnapshot.child("IRMS").getChildren()) {
                    t_irms.setText(subsnap.getValue().toString() +"A");
                    arr_irms[arrkey_irms++]=Float.parseFloat(subsnap.getValue().toString());
                }

                for (DataSnapshot subsnap: dataSnapshot.child("PowerFactor").getChildren()) {
                    t_powerfactor.setText(subsnap.getValue().toString());

                }

                arrkey_freq = 0;

                for (DataSnapshot subsnap: dataSnapshot.child("Frequency").getChildren()) {
                    t_frequency.setText(subsnap.getValue().toString() +"Hz");
                    arr_freq[arrkey_freq++]=Float.parseFloat(subsnap.getValue().toString());
                }

                float f=0;arrkey=0;
                for (DataSnapshot subsnap: dataSnapshot.child("ActivePower").getChildren()) {
                    t_power.setText(subsnap.getValue().toString() +"WH");

                    f=Float.parseFloat(subsnap.getValue().toString());

                    arr[arrkey++]=f;
                   // Log.d("TT",String.valueOf(arr[arrkey-1]));
                }

                for (DataSnapshot subsnap: dataSnapshot.child("ReactivePower").getChildren()) {
                    t_energy.setText(subsnap.getValue().toString());
                    t_timestamp.setText(subsnap.getKey());
                }

                for (DataSnapshot subsnap: dataSnapshot.child("ApparentPower").getChildren()) {
                    t_apppower.setText(subsnap.getValue().toString());
                    t_timestamp.setText(subsnap.getKey());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError){

            }


        });
    }

    public void testbtn(View view) {

        Random rand = new Random();
        float f=rand.nextFloat();

       // SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss");

       // String format = simpleDateFormat.format(new Date());
       long unixTime = System.currentTimeMillis() / 1000L;
       String format=Long.toString(unixTime);

        mDatabase = FirebaseDatabase.getInstance().getReference().child( ((MyApplication) this.getApplication()).username);
        mDatabase.child("VRMS").child(format).setValue(String.format("%.02f", (2*f-1)*10+220));
        mDatabase.child("IRMS").child(format).setValue(String.format("%.02f", (2*f-1)*4+7));
        mDatabase.child("PowerFactor").child(format).setValue(String.format("%.02f", (2*f-1)*0.3+0.7));
        mDatabase.child("Frequency").child(format).setValue(String.format("%.02f", (2*f-1)*2+50));
        mDatabase.child("ActivePower").child(format).setValue(String.format("%.02f", (2*f-1)*20+50));
        mDatabase.child("ReactivePower").child(format).setValue(String.format("%.02f", (2*f-1)*10+100));
        mDatabase.child("ApparentPower").child(format).setValue(String.format("%.02f", (2*f-1)*10+90));
    }

    public void test_graph(View view) {
        int x=10;
        Intent myIntent = new Intent(MainActivity.this, Main2Activity.class);
        ((MyApplication) this.getApplication()).arr=this.arr;
        ((MyApplication) this.getApplication()).arrkey=this.arrkey;
        ((MyApplication) this.getApplication()).c='E';


        myIntent.putExtra("arr", arr); //Optional parameters
        myIntent.putExtra("arrkey", arrkey);
        MainActivity.this.startActivity(myIntent);
    }

    public void btn_onoff(View view) {

        if(switchtoggle==0) {
            //onoff.setText("Turn Off");
            mDatabase.child("Status").setValue(1);
        }
        else{
           // onoff.setText("Turn On");
            mDatabase.child("Status").setValue(0);
    }}

    public void btn_schedule(View view) {
        Intent myIntent = new Intent(MainActivity.this, Main5Activity.class);
        MainActivity.this.startActivity(myIntent);
    }

    public void test_graph_vrms(View view) {

        Intent myIntent = new Intent(MainActivity.this, Main2Activity.class);
        ((MyApplication) this.getApplication()).arr_vrms=this.arr_vrms;
        ((MyApplication) this.getApplication()).arrkey_vrms=this.arrkey_vrms;

        ((MyApplication) this.getApplication()).c='V';


        MainActivity.this.startActivity(myIntent);
    }

    public void test_graph_irms(View view) {

        Intent myIntent = new Intent(MainActivity.this, Main2Activity.class);
        ((MyApplication) this.getApplication()).arr_irms=this.arr_irms;
        ((MyApplication) this.getApplication()).arrkey_irms=this.arrkey_irms;

        ((MyApplication) this.getApplication()).c='I';


        MainActivity.this.startActivity(myIntent);
    }
    public void test_graph_freq(View view) {

        Intent myIntent = new Intent(MainActivity.this, Main2Activity.class);
        ((MyApplication) this.getApplication()).arr_freq=this.arr_freq;
        ((MyApplication) this.getApplication()).arrkey_freq=this.arrkey_freq;

        ((MyApplication) this.getApplication()).c='F';


        MainActivity.this.startActivity(myIntent);
    }
}
