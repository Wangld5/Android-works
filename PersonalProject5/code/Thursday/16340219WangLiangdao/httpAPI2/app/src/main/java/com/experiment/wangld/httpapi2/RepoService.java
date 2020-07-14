package com.experiment.wangld.httpapi2;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RepoService {
    @GET("/users/{user_name}/repos")
    Observable<List<Repo>> getRepo(@Path("user_name") String user_name);
}
