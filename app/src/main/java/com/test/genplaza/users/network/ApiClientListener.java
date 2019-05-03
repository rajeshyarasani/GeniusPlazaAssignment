package com.test.genplaza.users.network;

import com.test.genplaza.users.network.model.NewUser;
import com.test.genplaza.users.network.model.UsersResponse;

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