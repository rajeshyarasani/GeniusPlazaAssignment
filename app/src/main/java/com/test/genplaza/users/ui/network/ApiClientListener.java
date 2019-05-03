package com.test.genplaza.users.ui.network;

import retrofit2.Call;;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/*
 *  ApiClientListener interface, declare Retrofit APi call methods.
 */
public interface ApiClientListener {

    @GET("users")
    Call<UsersResponse> getUsersResponse(@Query("page") int pageNumber, @Query("per_page") int perPage);

    @POST("users")
    Call<NewUser> createUser(@Body NewUser user);
}