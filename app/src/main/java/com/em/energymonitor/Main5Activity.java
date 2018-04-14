package com.em.energymonitor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Main5Activity extends AppCompatActivity implements
        View.OnClickListener {
    private DatabaseReference mDatabase;

    Button btnDatePicker, btnTimePicker;
    EditText txtDate, txtTime;
    private int mYear, mMonth, mDay, mHour, mMinute;
    int xYear,xMonth,xDay,xHour,xMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);
        mDatabase = FirebaseDatabase.getInstance().getReference().child( ((MyApplication) this.getApplication()).username);
        btnDatePicker=(Button)findViewById(R.id.button9);
        btnTimePicker=(Button)findViewById(R.id.button6);
        txtDate=(EditText)findViewById(R.id.editText4);
        txtTime=(EditText)findViewById(R.id.editText3);

        btnDatePicker.setOnClickListener(this);
        btnTimePicker.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        if (v == btnDatePicker) {

            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            txtDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            xDay=dayOfMonth;
                                xMonth=monthOfYear+1;
                                xYear=year;


                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
        if (v == btnTimePicker) {

            // Get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            txtTime.setText(hourOfDay + ":" + minute);
                            xHour=hourOfDay;
                            xMinute=minute;

                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        }
    }

    public void fn_on(View view) {

        if(!txtDate.getText().equals("") && !txtTime.getText().equals(""))
        {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd/hh/mm");
            String dt=xYear+"/"+xMonth+"/"+xDay+"/"+xHour+"/"+xMinute;

            Date currentDate = null;
            try{
                currentDate=formatter.parse(dt);

            }catch (Exception e){
                e.printStackTrace();
            }

            Toast.makeText(getApplication().getBaseContext(),"Set on time to " + String.valueOf(currentDate.getTime()/1000),Toast.LENGTH_SHORT).show();
            mDatabase.child("OnTime").setValue((currentDate.getTime()/1000));
        }


}

    public void fn_off(View view) {

        if(!txtDate.getText().equals("") && !txtTime.getText().equals(""))
        {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd/hh/mm");
            String dt=xYear+"/"+xMonth+"/"+xDay+"/"+xHour+"/"+xMinute;

            Date currentDate = null;
            try{
                currentDate=formatter.parse(dt);

            }catch (Exception e){
                e.printStackTrace();
            }

            Toast.makeText(getApplication().getBaseContext(),"Set off time to " + String.valueOf(currentDate.getTime()/1000),Toast.LENGTH_SHORT).show();
            mDatabase.child("OffTime").setValue((currentDate.getTime()/1000));
        }

    }
}
