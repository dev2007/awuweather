package com.awu.awuweather.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.awu.awuweather.db.WeatherDb;
import com.awu.awuweather.model.City;
import com.awu.awuweather.model.County;
import com.awu.awuweather.model.Province;
import android.util.*;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by awu on 2015-09-29.
 */
public class Utility {
    private static final String TAG = "Utility";
    /**
     * Save Province data from http response.
     *
     * @author awu
     * created at 2015-09-30 9:44
     */
    public synchronized static boolean handleProvinceResponse(WeatherDb db,String response){
        if(!TextUtils.isEmpty(response)){
            String[] allProvinces = response.split(",");
            if(allProvinces != null && allProvinces.length > 0){
                for (String p : allProvinces){
                    String[] array = p.split("\\|");
                    Province province = new Province();
                    province.setProvinceCode(array[0]);
                    province.setProvinceName(array[1]);
                    db.saveProvince(province);
                }
                return true;
            }
        }
        return  false;
    }

    /**
     * Save city data from http response.
     *
     * @author awu
     * created at 2015-09-30 9:49
     */
    public static boolean handleCitiesResponse(WeatherDb db,String response,int provinceId){
        if(!TextUtils.isEmpty(response)){
            String[] allCities = response.split(",");
            if(allCities != null && allCities.length > 0){
                for (String c : allCities){
                    String[] array = c.split("\\|");
                    City city = new City();
                    city.setCityCode(array[0]);
                    city.setCityName(array[1]);
                    city.setProvinceId(provinceId);
                    db.saveCity(city);
                }
                return true;
            }
        }
        return  false;
    }

    /**
     * Save County data from http response.
     *
     * @author awu
     * created at 2015-09-30 9:55
     */
    public static boolean handleCountiesResponse(WeatherDb db,String response,int cityId){
        if(!TextUtils.isEmpty(response)){
            String[] allCounties = response.split(",");
            if(allCounties != null && allCounties.length > 0){
                for (String c : allCounties){
                    String[] array = c.split("\\|");
                    County county = new County();
                    county.setCountyCode(array[0]);
                    county.setCountyName(array[1]);
                    county.setCityId(cityId);
                    db.saveCounty(county);
                }
                return true;
            }
        }
        return false;
    }

    public static void handleWeatherResponse(Context context,String response){
        try{
            JSONObject jsonObject = new JSONObject(response);
            JSONObject weatherInfo = jsonObject.getJSONObject("weatherinfo");
            saveWeatherInfo(context,weatherInfo.getString("city"),
                    weatherInfo.getString("cityid"),
                    weatherInfo.getString("temp1"),
                    weatherInfo.getString("temp2"),
                    weatherInfo.getString("weather"),
                    weatherInfo.getString("ptime"));
            Log.i(TAG, "handleWeatherResponse read weather ok");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void saveWeatherInfo(Context context,String cityName,
                                       String weatherCode,String temp1,String temp2,
                                       String weatherDesp,String publishTime){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年M月d日", Locale.CHINA);
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putBoolean("city_selected",true);
        editor.putString("city_name", cityName);
        editor.putString("weather_code", weatherCode);
        editor.putString("temp1", temp1);
        editor.putString("temp2", temp2);
        editor.putString("weather_desp", weatherDesp);
        editor.putString("publish_time", publishTime);
        editor.putString("current_date",sdf.format(new Date()));
        editor.commit();
    }
}
