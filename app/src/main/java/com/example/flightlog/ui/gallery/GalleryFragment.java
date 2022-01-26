package com.example.flightlog.ui.gallery;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.flightlog.FetchDetails;
import com.example.flightlog.LogDetails;
import com.example.flightlog.LoginDetailsDatabase;
import com.example.flightlog.R;
import com.example.flightlog.UserInfo;
import com.example.flightlog.UserInfoDataBase;

import java.util.Calendar;

public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;
    private RadioButton FetchAll;
    private RadioButton FetchByDate;
    private RadioButton FetchByPos;
    private RadioButton FetchByMonth;
    private DatePickerDialog picker;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        FetchAll = (RadioButton) root.findViewById(R.id.fetchall);
        FetchByDate = (RadioButton) root.findViewById(R.id.bydate);
        FetchByPos = (RadioButton) root.findViewById(R.id.byPos);
        FetchByMonth = (RadioButton) root.findViewById(R.id.bymonth);


        LoginDetailsDatabase db = new LoginDetailsDatabase(getContext());
        String str = db.getDesignation();
        if(str.equals("Commander")) {
            str = "CoPilot";
        } else {
            str = "Commander";
        }
        final String designation = str;
        FetchByPos.setText(str);


        FetchAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), FetchDetails.class);
                intent.putExtra("Data", 0);
                startActivity(intent);
                FetchAll.setChecked(false);

            }
        });

        FetchByPos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserInfoDataBase userInfoDataBase = new UserInfoDataBase(getContext());
                String [] names = userInfoDataBase.getAllPos(designation).split(":");
                AutoCompleteTextView editText = (AutoCompleteTextView) root.findViewById(R.id.fetchinput);
                editText.setVisibility(View.VISIBLE);
                editText.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, names));
                editText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String pos = editText.getText().toString();
                        Intent intent = new Intent(getActivity(), FetchDetails.class);
                        intent.putExtra("Data", 2);
                        intent.putExtra("Name", pos);
                        startActivity(intent);
                        FetchByPos.setChecked(false);
                        editText.setVisibility(View.GONE);
                    }
                });

            }
        });

        FetchByDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FetchAll.setChecked(false);

                final Calendar cldr = Calendar.getInstance();
                Integer day = cldr.get(Calendar.DAY_OF_MONTH);
                final Integer month = cldr.get(Calendar.MONTH);
                Integer year = cldr.get(Calendar.YEAR);


                picker = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                String sday, smonth;
                                sday = Integer.toString(dayOfMonth);
                                smonth = Integer.toString(monthOfYear+1);
                                if(dayOfMonth < 10) {
                                    sday = "0" + Integer.toString(dayOfMonth);
                                }

                                if(monthOfYear < 9) {
                                    smonth = "0" + Integer.toString(monthOfYear+1);
                                }

                                String date1 = "'" + Integer.toString(year) + "-" + smonth + "-" + sday + " 00:00:00'";
                                String date2 = "'" + Integer.toString(year) + "-" + smonth + "-" + sday + " 23:59:59'";
                                Intent intent = new Intent(getActivity(), FetchDetails.class);
                                intent.putExtra("Date1", date1);
                                intent.putExtra("Date2", date2);
                                intent.putExtra("Data", 1);
                                startActivity(intent);

                                Log.e("DateFormat", date1);
                                Log.e("DateFormat", date2);

                            }
                        }, year, month, day);
                picker.show();

                FetchByDate.setChecked(false);
            }
        });

        FetchByMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                UserInfoDataBase userInfoDataBase = new UserInfoDataBase(getContext());
                String [] names = new String[] {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
                AutoCompleteTextView editText = (AutoCompleteTextView) root.findViewById(R.id.fetchinput);
                editText.setVisibility(View.VISIBLE);
                editText.getText().clear();
                editText.setHint("Month");
                editText.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, names));
                editText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        String smonth = editText.getText().toString();
                        Integer month = UserInfo.getMonth(smonth);
                        Intent intent = new Intent(getActivity(), FetchDetails.class);
                        intent.putExtra("Data", 3);
                        intent.putExtra("Month", month);
                        startActivity(intent);
                        FetchByMonth.setChecked(false);
                        editText.setVisibility(View.GONE);
                    }
                });
            }
        });
        return root;
    }
}