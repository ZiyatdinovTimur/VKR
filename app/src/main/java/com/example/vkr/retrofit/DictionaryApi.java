package com.example.vkr.retrofit;

import com.example.vkr.model.Dictionary;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface DictionaryApi {

    @GET("dictionary/get-all")
    Call<List<Dictionary>> getAllDictionary();

    @POST("dictionary/save")
    Call<Dictionary> save(@Body Dictionary dictionary);
}
