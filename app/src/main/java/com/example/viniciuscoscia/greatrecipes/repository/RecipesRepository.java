package com.example.viniciuscoscia.greatrecipes.repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RecipesRepository {

    private static Retrofit retrofitInstance;
    private static final String baseUrl = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/";
    private RecipesRepository(){}

    public static Retrofit getRetrofitInstance() {
        if(retrofitInstance == null) {
            Gson gson = new GsonBuilder().setLenient().create();

            OkHttpClient client = new OkHttpClient.Builder()
                    .build();

            retrofitInstance = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(client)
                    .build();
        }
        return retrofitInstance;
    }

}
