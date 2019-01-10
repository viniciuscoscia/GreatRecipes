package com.example.viniciuscoscia.greatrecipes.recipeListTest;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.viniciuscoscia.greatrecipes.R;

import com.example.viniciuscoscia.greatrecipes.entity.Recipe;
import com.example.viniciuscoscia.greatrecipes.ui.mainActivity.MainActivity;
import com.example.viniciuscoscia.greatrecipes.ui.recipeDetailsActivity.RecipeDetailsActivity;
import com.example.viniciuscoscia.greatrecipes.utils.Utils;
import com.google.gson.Gson;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.filters.MediumTest;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(JUnit4.class)
public class RecipeListUITest {

    @Rule
    public ActivityTestRule<RecipeDetailsActivity> recipeDetailsActivityTestRule =
            new ActivityTestRule<>(RecipeDetailsActivity.class, false, false);

    @Before
    public void setup() {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Gson gson = new Gson();

        Recipe recipe = gson.fromJson(Utils.jsonFromRaw(context, R.raw.recipe_example), Recipe.class);

        Intent intent = new Intent();
        intent.putExtra(Recipe.RECIPE_KEY, recipe);

        recipeDetailsActivityTestRule.launchActivity(intent);
    }

    @Test
    public void clickRecipe_OpenRecipeStepActivity() {
        onView(withId(R.id.rv_recipe_details))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));

        onView(withId(R.id.playerView)).check(matches(isDisplayed()));
    }

    @Test
    public void startActivity_TabletMode() {
        onView(withId(R.id.tv_stepDescription)).check(matches(isDisplayed()));
    }

    @Test
    public void startActivity_SmartphoneMode() {
        onView(withId(R.id.tv_stepDescription)).check(doesNotExist());
    }

}
