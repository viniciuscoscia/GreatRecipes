package com.example.viniciuscoscia.greatrecipes.ui.recipeDetailsActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.viniciuscoscia.greatrecipes.R;
import com.example.viniciuscoscia.greatrecipes.entity.Ingredient;
import com.example.viniciuscoscia.greatrecipes.entity.Recipe;
import com.example.viniciuscoscia.greatrecipes.entity.Step;
import com.example.viniciuscoscia.greatrecipes.ui.fragments.recipeDetails.RecipeDetailsFragment;
import com.example.viniciuscoscia.greatrecipes.ui.fragments.stepDetails.StepDetailsFragment;
import com.example.viniciuscoscia.greatrecipes.ui.stepDetailsActivity.StepDetailsActivity;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;

public class RecipeDetailsActivity extends AppCompatActivity implements RecipeDetailsFragment.RecipeDetailsClickHandler {

    private RecipeDetailsViewModel recipeDetailsViewModel;
    private boolean twoPanel = false;
    private FragmentManager fragmentManager;
    private StepDetailsFragment fragment;
    private TextView recipeName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        recipeName = findViewById(R.id.tv_recipe_name);
        fragmentManager = getSupportFragmentManager();

        if(savedInstanceState != null) {
            fragment = (StepDetailsFragment)getSupportFragmentManager().getFragment(savedInstanceState, StepDetailsFragment.TAG);
        }

        setupViewModel();
        startRecipeDetailsFragment();
        checkIsTwoPanel();

        if(recipeDetailsViewModel.isFirstTime() && twoPanel){
            startStepDetailFragment(recipeDetailsViewModel.getRecipe().getStepList()
                    .get(0));
            recipeDetailsViewModel.setFirstTime(false);
        }
    }

    private void checkIsTwoPanel() {
        if(findViewById(R.id.step_details_container) == null){
            return;
        }

        twoPanel = true;
    }

    private void startStepDetailFragment(Step step) {
        fragment = new StepDetailsFragment();
        fragment.setStep(step);

        fragmentManager.beginTransaction()
                .replace(R.id.step_details_container, fragment)
                .commit();
    }

    private void setupViewModel() {
        recipeDetailsViewModel = ViewModelProviders.of(this).get(RecipeDetailsViewModel.class);

        Intent intent = getIntent();
        Recipe recipe = intent.getParcelableExtra(Recipe.RECIPE_KEY);
        recipeDetailsViewModel.setRecipe(recipe);
        recipeName.setText(recipe.getName());
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
            startStepDetailActivity(adapterPosition);
        }
    }

    private void startStepDetailActivity(int adapterPosition) {
        Intent intent = new Intent(this, StepDetailsActivity.class);

        intent.putExtra(Recipe.RECIPE_KEY, recipeDetailsViewModel.getRecipe());
        intent.putExtra(Step.STEP_POSITION, adapterPosition-1);
        startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        fragmentManager.executePendingTransactions();

        if(fragment != null && fragment.isAdded()) {
            fragmentManager.putFragment(outState, StepDetailsFragment.TAG, fragment);
        }
    }
}
