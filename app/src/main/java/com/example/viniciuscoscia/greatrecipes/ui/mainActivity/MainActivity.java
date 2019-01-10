package com.example.viniciuscoscia.greatrecipes.ui.mainActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.viniciuscoscia.greatrecipes.R;
import com.example.viniciuscoscia.greatrecipes.entity.Recipe;
import com.example.viniciuscoscia.greatrecipes.ui.recipeDetailsActivity.RecipeDetailsActivity;
import com.example.viniciuscoscia.greatrecipes.widget.IngredientChangeService;
import com.google.gson.Gson;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity implements RecipesAdapter.RecipeOnClick{

    private MainViewModel mainViewModel;
    private RecyclerView recipesRecyclerView;
    private RecipesAdapter recipesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        configureViewModel();
        configureRecyclerView();
    }

    private void configureRecyclerView() {
        recipesAdapter = new RecipesAdapter(this);
        mainViewModel.getRecipesList().observe(this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(@Nullable List<Recipe> recipes) {
                recipesAdapter.setRecipeList(recipes);
            }
        });

        recipesRecyclerView = findViewById(R.id.rv_recipes);
        recipesRecyclerView.setAdapter(recipesAdapter);
        recipesRecyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        recipesRecyclerView.setHasFixedSize(true);
    }

    private void configureViewModel() {
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mainViewModel.getRecipes();
    }

    @Override
    public void onRecipeClickListener(Recipe recipe) {
        Intent intent = new Intent(this, RecipeDetailsActivity.class);
        intent.putExtra(Recipe.RECIPE_KEY, recipe);

        IngredientChangeService.changeIngredientsListWidget(this);

        SharedPreferences.Editor sharedEditor =
                                    getSharedPreferences("recipeApp", MODE_PRIVATE).edit();

        sharedEditor.putString("lastRecipe", new Gson().toJson(recipe, Recipe.class));

        sharedEditor.apply();

        startActivity(intent);
    }
}
