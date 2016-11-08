package com.aihughes.peoplemon.Network;


import android.util.Log;

import com.aihughes.peoplemon.PeoplemonApplication;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
/**
 * Created by alexhughes on 11/6/16.
 */

public class RestClient {
    private ApiService apiService;

    public RestClient(){
        Log.d("*****", "I make it 1");
        GsonBuilder builder = new GsonBuilder();
        Log.d("*****", "I make it 2");
        builder.setDateFormat("EEE, dd MMM yyyy HH:mm:ss Z");
        Log.d("*****", "I make it 3");
        Gson gson = builder.create();
        Log.d("*****", "I make it 4");

        HttpLoggingInterceptor log = new HttpLoggingInterceptor();
        Log.d("*****", "I make it 5");
        log.setLevel(HttpLoggingInterceptor.Level.BODY);

        //web browser
        Log.d("*****", "I make it 6");

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .addInterceptor(new SessionRequestInterceptor())
                .addInterceptor(log)
                .build();
        Log.d("*****", "I make it 7");



        Retrofit restAdapter = new Retrofit.Builder()
                .baseUrl(PeoplemonApplication.API_BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        Log.d("*****", "I make it 8");
        apiService = restAdapter.create(ApiService.class);
        Log.d("*****", "I make it 9");
    }

    public ApiService getApiService() {
        return apiService;
    }
}

