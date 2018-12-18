package com.example.viniciuscoscia.greatrecipes.repository;

import com.example.viniciuscoscia.greatrecipes.entity.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface IRecipesAPI {

    @GET("baking.json")
    Call<List<Recipe>> getRecipes();

}
