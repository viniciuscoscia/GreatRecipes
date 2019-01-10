package com.example.viniciuscoscia.greatrecipes.mainActivityTest;

import com.example.viniciuscoscia.greatrecipes.R;
import com.example.viniciuscoscia.greatrecipes.ui.mainActivity.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.rule.ActivityTestRule;
import androidx.test.espresso.matcher.BoundedMatcher;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.assertion.ViewAssertions.selectedDescendantsMatch;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;


@RunWith(JUnit4.class)
public class MainActivityUITest {
    @Rule public ActivityTestRule<MainActivity> mainActivityActivityTestRule =
                                    new ActivityTestRule<>(MainActivity.class);

    @Test
    public void clickRecipeItem_OpenRecipe(){
        onView(ViewMatchers.withId(R.id.rv_recipes))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(withId(R.id.tv_recipe_name)).check(matches(withText("Nutella Pie")));
    }
}
