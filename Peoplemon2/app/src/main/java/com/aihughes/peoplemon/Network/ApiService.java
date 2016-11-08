package com.aihughes.peoplemon.Network;

import com.aihughes.peoplemon.Models.Account;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

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



}
