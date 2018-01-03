package com.example.cemdirman.comkonusarakogren.utility;

import android.util.Log;


/**
 * Created by cemdirman on 3.01.2018.
 */

public class RequestURL {

    private static final String TAG = "RequestURL";

    private static final String apiKey  = "b75cb6deeb7444afb2ee353ffba092ec";
    private static final String yandexApiKey = "trnsl.1.1.20180103T161334Z.5eec7b2c9069a5b8.fc06bfb57a212621f2882da57e26a7d6533aaa5a";
    private static final String baseUrl = "https://newsapi.org/v2/everything?sources=wired&apiKey=";
    private static final String yandexBaseUrl = "https://translate.yandex.net/api/v1.5/tr.json/translate?key=";

    public static String getNewsUrl(){
        String newsUrl = baseUrl.concat(apiKey);
        Log.d(TAG, "getNewsUrl: " + newsUrl);
        return  newsUrl;
    }

    public static String getTranslateUrl(){
        String translateUrl  = yandexBaseUrl.concat(yandexApiKey);
        Log.d(TAG,"getTranslateUrl: " + translateUrl );
        return translateUrl;
    }


}
