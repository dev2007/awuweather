package com.awu.awuweather.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.*;

import com.awu.awuweather.model.City;
import com.awu.awuweather.model.County;
import com.awu.awuweather.model.Province;

import java.util.ArrayList;
import java.util.List;

/**
 * Operator class for weather db.
 * Created by awu on 2015-09-29.
 */
public class WeatherDb {
    private static  final  String TAG = "WeatherDb";
    //database name.
    public static final  String DB_NAME = "awu_weather";
    //database version.
    public static final  int VERSION = 1;
    //class instance.
    private static WeatherDb weatherDb = null;
    //database instance.
    private SQLiteDatabase db;

    /**
     * private constructor.
     *
     * @author awu
     * created at 2015-09-29 16:21
     */
    private WeatherDb(Context context){
        WeatherDbHelper helper = new WeatherDbHelper(context,DB_NAME,null,VERSION);
        db = helper.getWritableDatabase();
    }

    /**
     * instance method.
     *
     * @author awu
     * created at 2015-09-29 16:21
     */
    public synchronized  static WeatherDb getInstance(Context context){
        if(weatherDb == null){
            weatherDb = new WeatherDb(context);
        }
        return weatherDb;
    }

    /**
     * Save province data.
     *
     * @author awu
     * created at 2015-09-29 16:21
     * @param province Province model.
     */
    public void saveProvince(Province province){
        if(province != null){
            ContentValues values = new ContentValues();
            values.put("province_name",province.getProvinceName());
            values.put("province_code",province.getProvinceCode());
            db.insert("Province",null,values);
        }
    }

    /**
     * Load province list from db.
     *
     * @author awu
     * created at 2015-09-29 16:32
     * @return Province list.
     */
    public List<Province> loadProvince(){
        List<Province> list = new ArrayList<Province>();
        Cursor cursor = db.query("Province",null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            do {
                Province province = new Province();
                province.setId(cursor.getInt(cursor.getColumnIndex("id")));
                province.setProvinceName(cursor.getString(cursor.getColumnIndex("province_name")));
                province.setProvinceCode(cursor.getString(cursor.getColumnIndex("province_code")));
                list.add(province);
            }while (cursor.moveToNext());
        }
        if(cursor != null){
            cursor.close();
        }

        return list;
    }

    /**
     * Save City data.
     *
     * @author awu
     * created at 2015-09-29 16:35
     * @param city City model.
     */
    public void saveCity(City city){
        if(city != null){
            ContentValues values = new ContentValues();
            values.put("city_name",city.getCityName());
            values.put("city_code",city.getCityCode());
            values.put("province_id",city.getProvinceId());
            db.insert("City", null, values);
        }
    }

    /**
     * Load city list from db.
     *
     * @author awu
     * created at 2015-09-29 16:42
     * @return city list.
     */
    public List<City> loadCities(int provinceId){
        List<City> list = new ArrayList<City>();
        Cursor cursor = db.query("City",null,"province_id = ?",
                new String[]{String.valueOf(provinceId)},null,null,null);

        if(cursor.moveToFirst()){
            do{
                City city = new City();
                city.setId(cursor.getInt(cursor.getColumnIndex("id")));
                city.setCityName(cursor.getString(cursor.getColumnIndex("city_name")));
                city.setCityCode(cursor.getString(cursor.getColumnIndex("city_code")));
                city.setProvinceId(provinceId);
                list.add(city);
            }while (cursor.moveToNext());
        }

        if(cursor != null){
            cursor.close();
        }

        return list;
    }

    /**
     * Save county data.
     *
     * @author awu
     * created at 2015-09-29 16:48
     * @param county County model.
     */
    public  void saveCounty(County county){
        if(county != null){
            ContentValues values = new ContentValues();
            values.put("county_name",county.getCountyName());
            values.put("county_code",county.getCountyCode());
            values.put("city_id",county.getCityId());
            Log.i(TAG, "saveCounty "+county.getCityId());
            db.insert("County", null, values);
        }
    }

    /**
     * Load county list from db.
     *
     * @author awu
     * created at 2015-09-29 16:57
     * @return county list.
     */
    public List<County> loadCounties(int cityCode){
        List<County> list = new ArrayList<County>();
        Cursor cursor = db.query("County",null,"city_id = ?",
                new String[]{String.valueOf(cityCode)},null,null,null);
        if(cursor.moveToFirst()){
            do{
                County county = new County();
                county.setId(cursor.getInt(cursor.getColumnIndex("id")));
                county.setCountyName(cursor.getString(cursor.getColumnIndex("county_name")));
                county.setCountyCode(cursor.getString(cursor.getColumnIndex("county_code")));
                county.setCityId(cursor.getInt(cursor.getColumnIndex("city_id")));
                list.add(county);
            }while (cursor.moveToNext());
        }

        if(cursor != null){
            cursor.close();
        }
        return list;
    }

}
