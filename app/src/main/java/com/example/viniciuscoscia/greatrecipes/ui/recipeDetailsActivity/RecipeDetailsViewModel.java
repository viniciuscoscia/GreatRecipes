package com.example.viniciuscoscia.greatrecipes.ui.recipeDetailsActivity;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.example.viniciuscoscia.greatrecipes.entity.Recipe;

public class RecipeDetailsViewModel extends AndroidViewModel {

    private Recipe recipe;

    public RecipeDetailsViewModel(@NonNull Application application) {
        super(application);
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }
}
