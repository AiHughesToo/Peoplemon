package com.aihughes.peoplemon.Network;

import com.aihughes.peoplemon.Models.Account;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by alexhughes on 11/6/16.
 */

public interface ApiService {


    @POST("register")
    Call<Account> register(@Body Account account);

    @POST("auth")
    Call<Account> login(@Body Account account);


}
