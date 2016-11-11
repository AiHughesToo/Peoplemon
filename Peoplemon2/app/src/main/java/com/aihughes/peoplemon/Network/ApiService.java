package com.aihughes.peoplemon.Network;

import com.aihughes.peoplemon.Models.Account;
import com.aihughes.peoplemon.Models.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by alexhughes on 11/6/16.
 */

public interface ApiService {


    @POST("/api/Account/Register")
    Call<Void> register(@Body Account account);

    @FormUrlEncoded
    @POST("/token")
    Call<Account>login(@Field(value = "grant_type", encoded = true) String grant_type,
                       @Field(value = "username", encoded = true) String username,
                       @Field(value = "password", encoded = true) String password);

    @POST("/v1/User/CheckIn")
    Call<Void>checkin(@Body Account checkin);


    //Get User Info Call
    @GET("/api/Account/UserInfo")
    Call<Account>getUserInfo();

    @POST("/api/Account/UserInfo")
    Call<Void>postUserInfo(@Body Account account);

    @GET("v1/User/Nearby")
    Call<User[]> findNearby(@Query("radiusInMeters") Integer radiusInMeters);

    @POST("/v1/User/Catch")
    Call<Void>catchUser(@Body User user);

    @GET ("/v1/User/Caught")
    Call<User[]> caughtUsers();





}
