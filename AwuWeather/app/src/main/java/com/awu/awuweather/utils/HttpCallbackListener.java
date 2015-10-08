package com.awu.awuweather.utils;

/**
 * Created by awu on 2015-09-29.
 */
public interface HttpCallbackListener {
    void onFinish(String response);
    void onError(Exception e);
}
