package com.awu.awuweather.app;

import android.app.Application;
import android.content.Context;

/**
 * Created by awu on 2015-09-28.
 */
public class WeatherApp extends Application {
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
    }

    /**
     * Get the application context.
     *
     * @author awu
     * created at 2015-09-28 17:32
     */
    public static Context getContext() {
        return mContext;
    }
}
