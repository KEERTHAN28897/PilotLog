package com.example.flightlog.ui.home;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.flightlog.LogDetails;
import com.example.flightlog.LoginDetailsDatabase;
import com.example.flightlog.R;
import com.example.flightlog.UserInfo;
import com.example.flightlog.UserInfoDataBase;

import org.w3c.dom.Text;

import java.time.Instant;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;

public class HomeFragment extends Fragment  {

    private ImageButton AddData;
    private HomeViewModel homeViewModel;
    private ProgressBar Totalhours;
    private TextView TotalHrs;
    private TextView Designation;
    private TextView PresentMonth;
    private TextView viewDayTime;
    private TextView viewNightTime;
    private TextView viewTotalHrs;
    private TextView viewFlightNumber;
    private TextView viewPlace;
    private TextView disLastFlight;
    private TextView disTo;
    private TextView disDate;
    private TextView disTotalHrs;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        AddData  = (ImageButton) root.findViewById(R.id.addindb);
        Totalhours = (ProgressBar) root.findViewById(R.id.lastmonthprogress);
        TotalHrs = (TextView) root.findViewById(R.id.progresstext);
        Designation = (TextView) root.findViewById(R.id.welcome);
        disLastFlight = (TextView) root.findViewById(R.id.dislastflight);

        LoginDetailsDatabase loginDetailsDatabase = new LoginDetailsDatabase(getContext());
        String welocmeText = "Welcome ";
        welocmeText = welocmeText + loginDetailsDatabase.getDesignation() + " " + loginDetailsDatabase.getName() + "!";
        Designation.setText(welocmeText);
        Designation.setTextSize(22);
        Designation.setTextColor(Color.BLACK);
        Designation.setTypeface(null, Typeface.BOLD_ITALIC);
        Designation.setGravity(Gravity.CENTER);
        Designation.setBackgroundColor(Color.YELLOW);


        UserInfoDataBase db = new UserInfoDataBase(getContext());

        String daytimeMins = db.getDayTimeByMonth(-1);
        if(daytimeMins == null) {
            daytimeMins = "0";
        }
        Float daytimeHrs = Float.parseFloat(daytimeMins) / 60;
        viewDayTime = (TextView) root.findViewById(R.id.monthdaytime);
        viewDayTime.setText("DayTime: " + daytimeHrs.toString() + " hrs");
        viewDayTime.setTextSize(15);
        viewDayTime.setTextColor(Color.BLACK);
        viewDayTime.setTypeface(null, Typeface.BOLD_ITALIC);

        String nightTimeMins = db.getNightTimeByMonth(-1);
        if(nightTimeMins == null) {
            nightTimeMins = "0";
        }
        Float nightTimeHrs = Float.parseFloat(nightTimeMins) / 60;
        viewNightTime = (TextView) root.findViewById(R.id.monthnighttime);
        viewNightTime.setText("NightTime: " + nightTimeHrs.toString() + " hrs");
        viewNightTime.setTextSize(15);
        viewNightTime.setTextColor(Color.BLACK);
        viewNightTime.setTypeface(null, Typeface.BOLD_ITALIC);


        float v = (Float.parseFloat(daytimeMins) + Float.parseFloat(nightTimeMins)) / 60;
        Float tot_hrs = v;
        TotalHrs.setText(tot_hrs.toString());
        TotalHrs.setTypeface(null, Typeface.BOLD);

        Integer progress = (int) (v * 100) / 172;


        LocalDate currentdate = LocalDate.now();
        Month currentMonth = currentdate.getMonth();
        PresentMonth = (TextView) root.findViewById(R.id.monthstats);
        String string = currentMonth.toString().substring(0, 1).toUpperCase() +
                        currentMonth.toString().substring(1).toLowerCase() + " Statistics";
        PresentMonth.setText(string);
        PresentMonth.setTypeface(null, Typeface.BOLD_ITALIC);
        PresentMonth.setTextSize(20);
        PresentMonth.setTextColor(Color.BLACK);
        PresentMonth.setGravity(Gravity.CENTER);

        viewTotalHrs = (TextView) root.findViewById(R.id.totalHrs);
        String totalHours = db.getHours();
        if(totalHours == null) {
            totalHours = "0";
        }
        Float totalMins = Float.parseFloat(totalHours);

        Integer totalHr = (int)(totalMins / 60);
        String totalHrs = "Total Hours: " + totalHr.toString() + " hrs";
        viewTotalHrs.setTypeface(null, Typeface.BOLD);
        viewTotalHrs.setText(totalHrs);
        viewTotalHrs.setTextSize(90);
        viewTotalHrs.setTextColor(getResources().getColor(R.color.colorPantone1));
        startCountAnimation(viewTotalHrs, totalHr);

        viewFlightNumber = (TextView) root.findViewById(R.id.lastFlight);
        String data = db.getLastFlight();
        if(data == null) {
            data = "None;None;None";
        }
        String[] lastFlightNumber  =  data.split(";");


        viewFlightNumber.setText("Flight Number: " + lastFlightNumber[0]);
        viewFlightNumber.setTextSize(16);


        String place = lastFlightNumber[1];
        viewPlace = root.findViewById(R.id.disfrom);
        viewPlace.setText("From: " + place);
        viewPlace.setTextSize(18);

        disTo = root.findViewById(R.id.disto);
        disTo.setTextSize(18);
        disTo.setText("To: " + lastFlightNumber[2]);

        disTotalHrs = root.findViewById(R.id.distothrs);
        disTotalHrs.setTextSize(15);
        disTotalHrs.setText("Total Hours");
        disTotalHrs.setTypeface(null, Typeface.BOLD_ITALIC);
        disTotalHrs.setTextColor(Color.BLACK);

        disLastFlight.setText("Last Flight");
        disLastFlight.setTextSize(15);
        disLastFlight.setTextColor(Color.BLACK);
        disLastFlight.setTypeface(null, Typeface.BOLD_ITALIC);

        disDate = root.findViewById(R.id.disdate);
        UserInfoDataBase userInfoDataBase = new UserInfoDataBase(getContext());
        String str = userInfoDataBase.getEveryoneByFlightNumber(lastFlightNumber[0]);
        //get date from timestamp
        if(str == null) {
            str = "0;None";
        }
        String[] cols  = str.split(";");
        Long milliseconds = Long.parseLong(cols[0]);
        Instant instant = Instant.ofEpochMilli(milliseconds);
        LocalDate localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();
        Integer day = localDate.getDayOfMonth();
        Integer month = localDate.getMonthValue();
        Integer year = localDate.getYear();
        disDate.setText(day.toString()+ "/" + month.toString() + "/" + year.toString());
        disDate.setTextSize(18);





        ObjectAnimator animation = ObjectAnimator.ofInt(Totalhours, "progress", 0, progress); // see this max value coming back here, we animate towards that value
        animation.setDuration(2000); // in milliseconds
        animation.setInterpolator(new DecelerateInterpolator());
        animation.start();
        AddData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), LogDetails.class);
                startActivity(intent);
            }
        });
        return root;
    }

    private void startCountAnimation(TextView textView, Integer totalHrs) {
        ValueAnimator animator = ValueAnimator.ofInt(0, totalHrs);
        animator.setDuration(2000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                textView.setText(animation.getAnimatedValue().toString());
            }
        });
        animator.start();
    }


}