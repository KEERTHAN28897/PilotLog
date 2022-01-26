package com.example.flightlog;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import static java.time.temporal.ChronoUnit.MINUTES;


public class LogDetails extends AppCompatActivity implements View.OnClickListener {

    private static Button Submit;
    private EditText Commander;
    private EditText CoPilot;
    private EditText Source;
    private EditText Destination;
    private EditText Date;
    private EditText Start;
    private EditText Stop;
    private EditText DayTime;
    private EditText NightTime;
    private AutoCompleteTextView AircraftType;
    private AutoCompleteTextView FlightNumber;
    private UserInfo Info = new UserInfo();
    private DatePickerDialog picker;
    private CheckBox TrainingFlight;
    private CheckBox ltc;
    private CheckBox tri;
    private CheckBox de;
    private CheckBox underTraining;
    private String rem;
    private long mins;

    final Calendar myCalendar = Calendar.getInstance();

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_details);



        Commander = (EditText) findViewById(R.id.commander);
        CoPilot = (EditText) findViewById(R.id.copilot);
        Source = (EditText) findViewById(R.id.src);
        Destination = (EditText) findViewById(R.id.dest);
        Date = (EditText) findViewById(R.id.date);
        Start = (EditText) findViewById(R.id.start);
        Stop = (EditText) findViewById(R.id.stop);
        Submit = (Button) findViewById(R.id.Submit);
        DayTime = (EditText) findViewById(R.id.daytime);
        NightTime = (EditText) findViewById(R.id.nighttime);
        AircraftType = (AutoCompleteTextView) findViewById(R.id.AircraftType);
        FlightNumber = (AutoCompleteTextView) findViewById(R.id.FlightNumber) ;
        ltc = (CheckBox) findViewById(R.id.ltc);
        tri = (CheckBox) findViewById(R.id.tri);
        de = (CheckBox) findViewById(R.id.de);
        underTraining = (CheckBox) findViewById(R.id.underTraining);

        LoginDetailsDatabase db = new LoginDetailsDatabase(LogDetails.this);
        if(db.getDesignation().toString().equals("Commander")) {
            ltc.setVisibility(View.VISIBLE);
            ltc.setText("LTC");
            tri.setVisibility(View.VISIBLE);
            tri.setText("TRI");
            de.setVisibility(View.VISIBLE);
            de.setText("DE");
            underTraining.setVisibility(View.INVISIBLE);

        } else {
            ltc.setVisibility(View.INVISIBLE);
            tri.setVisibility(View.INVISIBLE);
            de.setVisibility(View.INVISIBLE);
            underTraining.setVisibility(View.VISIBLE);
            underTraining.setText("Under Training");
        }

        underTraining.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                String time = DayTime.getText().toString() + "-" + NightTime.getText().toString() ;
                Info.setTime1(time);
            }
        });

       // LoginDetailsDatabase db = new LoginDetailsDatabase(LogDetails.this);
        String str = db.getDesignation();
        if(str.equals("Commander")) {
            Commander.setText(db.getName());
        } else {
            CoPilot.setText(db.getName());
        }
        UserInfoDataBase userInfoDataBase = new UserInfoDataBase(LogDetails.this);
        String[] flightNumber =  userInfoDataBase.getAllFlightNumber();
        if(flightNumber == null) {
            flightNumber = new String[]{""};
        }

        final String[] getTimeRemaining = flightNumber;
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(LogDetails.this, android.R.layout.simple_dropdown_item_1line, getTimeRemaining);
        FlightNumber.setAdapter(arrayAdapter);

        Start.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                int hour = Calendar.HOUR_OF_DAY;
                int min = Calendar.MINUTE;

                final TimePickerDialog.OnTimeSetListener Timepicker = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        Start.setText(String.valueOf(i) + ":" + String.valueOf(i1));
                        Info.setStartHr(i);
                        Info.setStartMin(i1);
                    }
                };
                new TimePickerDialog(LogDetails.this, Timepicker, hour, min, true).show();
            }
        });

        Stop.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                int hour = Calendar.HOUR_OF_DAY;
                int min = Calendar.MINUTE;

                final TimePickerDialog.OnTimeSetListener Timepicker = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        Stop.setText(String.valueOf(i) + ":" + String.valueOf(i1));
                        Info.setStopHr(i);
                        Info.setStopMin(i1);
                    }
                };
                new TimePickerDialog(LogDetails.this, Timepicker, hour, min, true ).show();
            }
        });


        NightTime.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View view) {
                    int hour = Calendar.HOUR_OF_DAY;
                    int min = Calendar.MINUTE;

                    final TimePickerDialog.OnTimeSetListener Timepicker = new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int i, int i1) {
                            NightTime.setText(String.valueOf(i) + ":" + String.valueOf(i1));
                            Info.setNightTimeHr(i);
                            Info.setNightTimeMin(i1);

                            String[] starthr = Start.getText().toString().split(":");
                            String[] stophr = Stop.getText().toString().split(":");
                            Info.setStartHr(Integer.parseInt(starthr[0]));
                            Info.setStopHr(Integer.parseInt(stophr[0]));
                            Info.setStartMin(Integer.parseInt(starthr[1]));
                            Info.setStopMin(Integer.parseInt(stophr[1]));


                            LocalTime start_time = LocalTime.of(Info.getStartHr(), Info.getStartMin());
                            LocalTime stop_time = LocalTime.of(Info.getStopHr(), Info.getStopMin());
                            mins = start_time.until(stop_time, MINUTES);
                            if(mins < 0) {
                                mins = start_time.until(LocalTime.of(23, 59), MINUTES) + LocalTime.of(0, 0).until(stop_time, MINUTES) + 1;
                            }
                            //mins is time diff b/w source and dest
                            long remaining = mins - (i*60 + i1);
                            Long min = remaining%60;
                            Long hr = remaining/60;
                            rem = hr.toString() + ":" + min.toString();
                            DayTime.setText(rem);
                        }
                    };
                    new TimePickerDialog(LogDetails.this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar ,Timepicker, hour, min, true ).show();
                }
            });


        DayTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int hour = Calendar.HOUR_OF_DAY;
                int min = Calendar.MINUTE;

                final TimePickerDialog.OnTimeSetListener Timepicker = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        DayTime.setText(String.valueOf(i) + ":" + String.valueOf(i1));

                        Info.setDayTimeHr(i);
                        Info.setDayTimeMin(i1);


                        String[] starthr = Start.getText().toString().split(":");
                        String[] stophr = Stop.getText().toString().split(":");
                        Info.setStartHr(Integer.parseInt(starthr[0]));
                        Info.setStopHr(Integer.parseInt(stophr[0]));
                        Info.setStartMin(Integer.parseInt(starthr[1]));
                        Info.setStopMin(Integer.parseInt(stophr[1]));

                        LocalTime start_time = LocalTime.of(Info.getStartHr(), Info.getStartMin());
                        LocalTime stop_time = LocalTime.of(Info.getStopHr(), Info.getStopMin());
                        mins = start_time.until(stop_time, MINUTES);
                        Log.e("Timer", String.valueOf(mins));
                        if(mins < 0) {
                            mins = start_time.until(LocalTime.of(23, 59), MINUTES) + LocalTime.of(0, 0).until(stop_time, MINUTES) + 1;
                        }
                        //mins is time diff b/w source and dest
                        long remaining = mins - (i*60 + i1);
                        Long min = remaining%60;
                        Long hr = remaining/60;
                        rem = hr.toString() + ":" + min.toString();
                        Info.setNightTimeHr(hr.intValue());
                        Info.setNightTimeMin(min.intValue());
                        NightTime.setText(rem);
                    }
                };
                new TimePickerDialog(LogDetails.this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar ,Timepicker, hour, min, true ).show();
            }
        });


        Submit.setOnClickListener(this);

        Date.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                final int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);

                picker = new DatePickerDialog(LogDetails.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                Date.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                Info.setYear(year);
                                Info.setDay(dayOfMonth);
                                Info.setMonth(monthOfYear+1);

                            }
                        }, year, month, day);
                picker.show();
            }
        });

        AircraftType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        FlightNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //FlightNumber.showDropDown();

            }
        });


        FlightNumber.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String str = userInfoDataBase.getEveryoneByFlightNumber(FlightNumber.getText().toString());

                //get date from timestamp
                String[] cols  = str.split(";");
                Long milliseconds = Long.parseLong(cols[0]);
                Instant instant = Instant.ofEpochMilli(milliseconds);
                LocalDate localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();
                Integer day = localDate.getDayOfMonth();
                Integer month = localDate.getMonthValue();
                Integer year = localDate.getYear();
                Date.append(day.toString()+ "/" + month.toString() + "/" + year.toString());

                Commander.setText(cols[1]);
                CoPilot.setText(cols[2]);
                Source.setText(cols[3]);
                Destination.setText(cols[4]);
                Start.setText(cols[5]);
                Stop.setText(cols[6]);
                DayTime.setText(cols[7]);
                NightTime.setText(cols[8]);
                AircraftType.setText(cols[9]);
                Log.e("AutoFill", str);
            }
        });
    }




    @Override
    public void onClick(View view) {

        switch(view.getId()){
            case R.id.Submit: {
                //Populate in class Info
                Info.setCommander(Commander.getText().toString());
                Info.setCoPilot(CoPilot.getText().toString());
                Info.setDestination(Destination.getText().toString());
                Info.setSource(Source.getText().toString());
                Info.setFlightNumber(FlightNumber.getText().toString());
                Info.setAircraftType(AircraftType.getText().toString());

                String[] daytime = DayTime.getText().toString().split(":");
                Info.setDayTimeMin(Integer.parseInt(daytime[1]));
                Info.setDayTimeHr(Integer.parseInt(daytime[0]));

                String[] nighttime = NightTime.getText().toString().split(":");
                Info.setNightTimeMin(Integer.parseInt(nighttime[1]));
                Info.setNightTimeHr(Integer.parseInt(nighttime[0]));

                String[] start = Start.getText().toString().split(":");
                Info.setStartMin(Integer.parseInt(start[1]));
                Info.setStartHr(Integer.parseInt(start[0]));

                String[] stop = Stop.getText().toString().split(":");
                Info.setStopMin(Integer.parseInt(stop[1]));
                Info.setStopHr(Integer.parseInt(stop[0]));

                String[] date = Date.getText().toString().split("/");
                Info.setYear(Integer.parseInt(date[2]));
                Info.setMonth(Integer.parseInt(date[1]));
                Info.setDay(Integer.parseInt(date[0]));

                //Populate in db (make an entry)
                UserInfoDataBase db = new UserInfoDataBase(LogDetails.this);
                Long l = db.addOne(Info);
                Toast.makeText(getApplicationContext(), l.toString(),
                        Toast.LENGTH_LONG).show();
            }
        }
    }
}