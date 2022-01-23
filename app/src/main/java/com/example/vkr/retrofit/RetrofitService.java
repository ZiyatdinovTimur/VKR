package com.example.vkr.retrofit;

import com.google.gson.Gson;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {

    private Retrofit retrofit;

    public RetrofitService() {
        initializeRetrofit();
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

    private void initializeRetrofit() {
        //SERVER!!!
        retrofit = new Retrofit.Builder()
                .baseUrl("https://valternis-server.herokuapp.com")
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();
    }
}
