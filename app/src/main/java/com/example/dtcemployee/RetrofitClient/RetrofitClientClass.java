package com.example.dtcemployee.RetrofitClient;



import com.example.dtcemployee.BuildConfig;
import com.example.dtcemployee.Interface.APIinterface;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;


import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientClass {
    private static final String BASE_URL = "http://dtc.anstm.com/dtcAdmin/api/Employee/";
    private static RetrofitClientClass mInstance;
    private Retrofit retrofit;

    public RetrofitClientClass() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        if (BuildConfig.DEBUG)
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor)//third, log at the end
                .build();
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    public static synchronized RetrofitClientClass getInstance() {
        if (mInstance == null) {
            mInstance = new RetrofitClientClass();
        }
        return mInstance;
    }

    public APIinterface getInterfaceInstance() {
        return retrofit.create(APIinterface.class);
    }
}



