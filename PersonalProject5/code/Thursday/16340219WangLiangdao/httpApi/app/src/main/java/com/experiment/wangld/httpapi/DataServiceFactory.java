package com.experiment.wangld.httpapi;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class DataServiceFactory {
    private static final DataServiceFactory instance = new DataServiceFactory();
    private DataService dataService;
    public static DataServiceFactory getInstance(){
        return instance;
    }
    private DataServiceFactory(){
        OkHttpClient build = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .build();
        //"https://space.bilibili.com/ajax/top/"
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://space.bilibili.com/ajax/top/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(build)
                .build();
        dataService = retrofit.create(DataService.class);
    }

    public DataService getDataService() {
        return dataService;
    }
}
