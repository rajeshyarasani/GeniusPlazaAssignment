package com.test.genplaza.users.ui.network;

import retrofit2.Call;;
import retrofit2.http.GET;
import retrofit2.http.Query;

/*
*  ApiClientListener interface, declare Retrofit APi call methods.
*/
public interface ApiClientListener {

    @GET("users")
    Call<UsersResponse> getImageSearchResponse(@Query("page") int pageNumber);

}