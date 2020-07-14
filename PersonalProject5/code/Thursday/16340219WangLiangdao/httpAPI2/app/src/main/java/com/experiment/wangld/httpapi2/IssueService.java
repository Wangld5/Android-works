package com.experiment.wangld.httpapi2;

import com.google.gson.internal.bind.MapTypeAdapterFactory;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface IssueService {
    @GET("/repos/{user_name}/{repo_name}/issues")
    Observable<List<Issues>> getIssue(@Path("user_name") String user_name , @Path("repo_name") String repo_name);
    @POST("/repos/{user_name}/{repo_name}/issues")
    Observable<Issues> postIssue(@Header("Authorization") String token, @Path("user_name") String user_name , @Path("repo_name") String repo_name, @Body RequestBody route);
}
