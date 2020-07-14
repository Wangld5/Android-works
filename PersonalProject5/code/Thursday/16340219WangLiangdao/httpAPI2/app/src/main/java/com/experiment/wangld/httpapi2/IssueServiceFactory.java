package com.experiment.wangld.httpapi2;

import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class IssueServiceFactory {
    private static final IssueServiceFactory instance = new IssueServiceFactory();
    private IssueService issueService;
    public static IssueServiceFactory getInstance(){return instance;}
    private IssueServiceFactory(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                try {
                    String text = URLDecoder.decode(message, "utf-8");
                    Log.e("Issue", text);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    Log.e("Issue", message);
                }
            }
        });
        OkHttpClient build = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .build();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(build)
                .build();
        issueService = retrofit.create(IssueService.class);
    }
    public IssueService getIssueService() {
        return issueService;
    }
}
