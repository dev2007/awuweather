package com.awu.awuweather.utils;

import android.text.TextUtils;

import com.awu.awuweather.db.WeatherDb;
import com.awu.awuweather.model.City;
import com.awu.awuweather.model.County;
import com.awu.awuweather.model.Province;

/**
 * Created by awu on 2015-09-29.
 */
public class Utility {

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
}
