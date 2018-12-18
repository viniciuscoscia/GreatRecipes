package com.example.viniciuscoscia.greatrecipes.ui.mainActivity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.viniciuscoscia.greatrecipes.R;
import com.example.viniciuscoscia.greatrecipes.entity.Recipe;
import com.example.viniciuscoscia.greatrecipes.ui.recipeDetailsActivity.RecipeDetailsActivity;
import com.example.viniciuscoscia.greatrecipes.widget.IngredientChangeService;

import java.util.List;

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
        IngredientChangeService.changeIngredientsListWidget(this, recipe);
        startActivity(intent);
    }
}
