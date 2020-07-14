package com.experiment.wangld.httpapi;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface DataService {
    @GET("showTop")
    Observable<RecyclerObj> getUser(@Query("mid") String User);
}
