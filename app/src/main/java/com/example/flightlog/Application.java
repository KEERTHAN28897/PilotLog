package com.example.flightlog;
import com.jakewharton.threetenabp.AndroidThreeTen;

//Current min sdk version is being set to 23, In order to backport use following!
public class Application extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AndroidThreeTen.init(this);
    }
}
