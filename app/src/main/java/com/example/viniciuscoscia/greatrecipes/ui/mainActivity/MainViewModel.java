package com.example.viniciuscoscia.greatrecipes.ui.mainActivity;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.annotation.NonNull;

import com.example.viniciuscoscia.greatrecipes.entity.Recipe;
import com.example.viniciuscoscia.greatrecipes.repository.IRecipesAPI;
import com.example.viniciuscoscia.greatrecipes.repository.RecipesRepository;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainViewModel extends AndroidViewModel {

    private final Retrofit recipesRepository = RecipesRepository.getRetrofitInstance();
    private MutableLiveData<List<Recipe>> recipesList = new MutableLiveData<>();

    public MainViewModel(@NonNull Application application) {
        super(application);
    }

    public void getRecipes() {
        recipesRepository.create(IRecipesAPI.class)
            .getRecipes()
            .enqueue(new Callback<List<Recipe>>() {
                @Override
                public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                    if(response != null) {
                        recipesList.setValue(response.body());
                    }
                }

                @Override
                public void onFailure(Call<List<Recipe>> call, Throwable t) {
                    t.printStackTrace();
                }
            });
    }

    public MutableLiveData<List<Recipe>> getRecipesList() {
        return recipesList;
    }
}
