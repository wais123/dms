package com.example.dms.data.remote;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Api {
    private static final String BASE_URL = "https://dms.sandbox.idetree.com/api/";

    public static ServiceApi getService(){
        Gson gson = new GsonBuilder().serializeNulls().create();

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        ServiceApi apiInterface = retrofit.create(ServiceApi.class);

        return apiInterface;
    }

//    private static Api instance = new Api();
//
//    public static Api getInstance(){
//        return instance;
//    }
//
//    public Retrofit getRetrofit(GsonConverterFactory gcf){
//        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss")
//                .setLenient()
//                .create();
//
//        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
//        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
//
//        OkHttpClient.Builder httpClient = new OkHttpClient.Builder().
//                connectTimeout(30, TimeUnit.SECONDS)
//                .writeTimeout(30, TimeUnit.SECONDS)
//                .readTimeout(30, TimeUnit.SECONDS);
//        httpClient.addInterceptor(logging);
//
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(BASE_URL)
//                .addConverterFactory(gcf)
//                .client(httpClient.build())
//                .build();
//
//        return retrofit;
//    }
//
//    public <S> S createService(Class<S> serviceClass, GsonConverterFactory gcf){
//        return getRetrofit(gcf)
//                .create(serviceClass);
//    }
//    public Retrofit getRetrofitDev(GsonConverterFactory gcf){
//        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss")
//                .setLenient()
//                .create();
//
//        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
//        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
//
//        OkHttpClient.Builder httpClient = new OkHttpClient.Builder().
//                connectTimeout(30, TimeUnit.SECONDS)
//                .writeTimeout(30, TimeUnit.SECONDS)
//                .readTimeout(30, TimeUnit.SECONDS);
//        httpClient.addInterceptor(logging);
//
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(BASE_URL)
//                .addConverterFactory(gcf)
//                .client(httpClient.build())
//                .build();
//
//        return retrofit;
//    }
//
//    public <S> S createServiceCustom(Class<S> serviceClass, GsonConverterFactory gcf){
//        return getRetrofitDev(gcf)
//                .create(serviceClass);
//    }
}
