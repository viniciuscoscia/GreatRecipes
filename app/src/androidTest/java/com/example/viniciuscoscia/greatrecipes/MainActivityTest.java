package com.example.viniciuscoscia.greatrecipes;

import com.example.viniciuscoscia.greatrecipes.ui.mainActivity.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.assertion.ViewAssertions.selectedDescendantsMatch;
import static androidx.test.espresso.matcher.ViewMatchers.withId;


@RunWith(JUnit4.class)
public class MainActivityTest {
    @Rule public ActivityTestRule<MainActivity> mainActivityActivityTestRule =
                                    new ActivityTestRule<>(MainActivity.class);

    @Test
    public void clickRecipeItem_OpenRecipe(){
        onView(withId(R.id.rv_recipes)).perform(
                RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.layout_fragment_recipe_details)).
                check(selectedDescendantsMatch(withId(R.id.layout_fragment_recipe_details),
                        withId(R.id.rv_recipe_details)));
    }
}
