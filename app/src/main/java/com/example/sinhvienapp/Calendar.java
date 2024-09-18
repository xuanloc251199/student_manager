package com.example.sinhvienapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sinhvienapp.loginandregisteractivity.LoginActivity;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Calendar extends AppCompatActivity {
    CompactCalendarView compactCalendar;
    private SimpleDateFormat dateFormatMonth = new SimpleDateFormat("MMMM- yyyy", Locale.getDefault());
    FloatingActionButton fbHome;
    FloatingActionButton fab;
    FloatingActionButton fblogout;
    Boolean isOpen = false;
    ImageView btnBack;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);


        compactCalendar = (CompactCalendarView) findViewById(R.id.compactcalendar_view);
        compactCalendar.setUseThreeLetterAbbreviation(true);

        fbHome =findViewById(R.id.fbHomeEvent);
        fblogout=findViewById(R.id.fbDangXuatEvent);
        fab = findViewById(R.id.fabevent);

        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(view -> {
            onBackPressed();
        });

        Event ev1 = new Event (Color.GREEN,1625936400000L,"Teachers' Professional Day");
        Event ev2 = new Event (Color.GREEN,1625850000000L,"Tổng kết báo cáo");
        Event ev3 = new Event (Color.YELLOW,1626454800000L,"Báo cáo đồ án lập trình android");

        compactCalendar.addEvent(ev1);
        compactCalendar.addEvent(ev2);
        compactCalendar.addEvent(ev3);


        compactCalendar.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                Context context = getApplicationContext();

                if (dateClicked.toString().compareTo("Sun Jul 11 00:00:00 GMT+07:00 2021") == 0) {
                    Toast.makeText(context, "Teachers' Professional Day", Toast.LENGTH_SHORT).show();
                }else if (dateClicked.toString().compareTo("Sat Jul 10 00:00:00 GMT+07:00 2021") == 0) {
                    Toast.makeText(context, "Tônge kết báo cáo", Toast.LENGTH_SHORT).show();

                }
                else if (dateClicked.toString().compareTo("Sat Jul 17 00:00:00 GMT+07:00 2021") == 0) {
                    Toast.makeText(context, "Báo cáo đồ án lập trình android", Toast.LENGTH_SHORT).show();

                }
                else {
                    Toast.makeText(context, "Không có sự kiện!", Toast.LENGTH_SHORT).show();
                }


            }


            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {

            }

        });
        fbHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Calendar.this, ManagerActivity.class));
            }
        });
        fblogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Calendar.this, LoginActivity.class));
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isOpen) {
                    openMenu();
                } else {
                    closeMenu();
                }
            }
        });
    }


    private void openMenu() {
        isOpen = true;
        fbHome.animate().translationY(-getResources().getDimension(R.dimen.stan_60));
        fblogout.animate().translationY(-getResources().getDimension(R.dimen.stan_105));
    }

    private void closeMenu() {
        isOpen = false;
        fbHome.animate().translationY(0);
        fblogout.animate().translationY(0);
    }
}
