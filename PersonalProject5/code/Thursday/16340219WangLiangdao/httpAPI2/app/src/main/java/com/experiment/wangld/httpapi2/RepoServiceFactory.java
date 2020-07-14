package com.experiment.wangld.httpapi2;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RepoServiceFactory {
    private static final RepoServiceFactory instance = new RepoServiceFactory();
    private RepoService repoService;
    public static RepoServiceFactory getInstance(){return instance;}
    private RepoServiceFactory(){
        OkHttpClient build = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(build)
                .build();
        repoService = retrofit.create(RepoService.class);
    }
    public RepoService getRepoService() {
        return repoService;
    }
}
