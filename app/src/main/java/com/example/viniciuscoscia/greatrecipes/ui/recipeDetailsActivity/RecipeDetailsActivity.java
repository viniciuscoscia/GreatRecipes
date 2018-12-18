package com.example.viniciuscoscia.greatrecipes.ui.recipeDetailsActivity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.viniciuscoscia.greatrecipes.R;
import com.example.viniciuscoscia.greatrecipes.entity.Ingredient;
import com.example.viniciuscoscia.greatrecipes.entity.Recipe;
import com.example.viniciuscoscia.greatrecipes.entity.Step;
import com.example.viniciuscoscia.greatrecipes.ui.fragments.recipeDetails.RecipeDetailsFragment;
import com.example.viniciuscoscia.greatrecipes.ui.fragments.stepDetails.StepDetailsFragment;
import com.example.viniciuscoscia.greatrecipes.ui.stepDetailsActivity.StepDetailsActivity;

import java.util.ArrayList;
import java.util.List;

public class RecipeDetailsActivity extends AppCompatActivity implements RecipeDetailsFragment.RecipeDetailsClickHandler {

    private RecipeDetailsViewModel recipeDetailsViewModel;
    private boolean twoPanel = false;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        fragmentManager = getSupportFragmentManager();

        setupViewModel();
        setupTwoPanel();

        startRecipeDetailsFragment();
    }

    private void setupTwoPanel() {
        if(findViewById(R.id.step_details_container) == null){
            return;
        }

        twoPanel = true;
        startStepDetailFragment(recipeDetailsViewModel.getRecipe().getStepList().get(0));
    }

    private void startStepDetailFragment(Step step) {
        StepDetailsFragment stepDetailsFragment = new StepDetailsFragment();
        stepDetailsFragment.setStep(step);

        fragmentManager.beginTransaction()
                .replace(R.id.step_details_container, stepDetailsFragment)
                .commit();
    }

    private void setupViewModel() {
        recipeDetailsViewModel = ViewModelProviders.of(this).get(RecipeDetailsViewModel.class);

        Intent intent = getIntent();
        Recipe recipe = intent.getParcelableExtra(Recipe.RECIPE_KEY);
        recipeDetailsViewModel.setRecipe(recipe);
    }

    private List<Object> extractObjectList(Recipe recipe) {
        List<Object> objectList = new ArrayList<>();
        objectList.add(getIngredientList(recipe));
        objectList.addAll(recipe.getStepList());

        return objectList;
    }

    private void startRecipeDetailsFragment() {
        RecipeDetailsFragment recipeDetailsFragment = new RecipeDetailsFragment();
        recipeDetailsFragment.setupAdapter(extractObjectList(recipeDetailsViewModel.getRecipe()));

        fragmentManager.beginTransaction()
                .add(R.id.recipe_details_container, recipeDetailsFragment)
                .commit();
    }

    private String getIngredientList(Recipe recipe) {
        StringBuilder sb = new StringBuilder();

        for (Ingredient ingredient : recipe.getIngredientList()) {
            sb.append(ingredient.getQuantity())
                .append(ingredient.getMeasure())
                .append(" - ")
                .append(ingredient.getIngredientName())
                .append("\n");
        }

        sb.deleteCharAt(sb.length()-1); //Deletes last brakline from above loop
        return sb.toString();
    }

    @Override
    public void clickHandler(Step step, int adapterPosition) {
        if(twoPanel) {
            startStepDetailFragment(step);
            return;
        } else {
            startStepDetailActivity(step, adapterPosition);
        }
    }

    private void startStepDetailActivity(Step step, int adapterPosition) {
        Intent intent = new Intent(this, StepDetailsActivity.class);

        Log.d("ADAPTER POSITION", adapterPosition+"");
        intent.putExtra(Recipe.RECIPE_KEY, recipeDetailsViewModel.getRecipe());
        intent.putExtra(Step.STEP_POSITION, adapterPosition-1);
        startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
