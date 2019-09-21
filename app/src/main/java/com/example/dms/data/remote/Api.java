package com.example.dms.data.remote;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Api {
    private static final String BASE_URL = "https://beskem.mozaik.id/";

    private static Api instance = new Api();

    public static Api getInstance(){
        return instance;
    }

    public Retrofit getRetrofit(GsonConverterFactory gcf){
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss")
                .setLenient()
                .create();

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder().
                connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS);
        httpClient.addInterceptor(logging);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(gcf)
                .client(httpClient.build())
                .build();

        return retrofit;
    }

    public <S> S createService(Class<S> serviceClass, GsonConverterFactory gcf){
        return getRetrofit(gcf)
                .create(serviceClass);
    }
    public Retrofit getRetrofitDev(GsonConverterFactory gcf){
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss")
                .setLenient()
                .create();

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder().
                connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS);
        httpClient.addInterceptor(logging);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(gcf)
                .client(httpClient.build())
                .build();

        return retrofit;
    }

    public <S> S createServiceCustom(Class<S> serviceClass, GsonConverterFactory gcf){
        return getRetrofitDev(gcf)
                .create(serviceClass);
    }
}
