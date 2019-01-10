package com.example.viniciuscoscia.greatrecipes.mainActivityTest;

import com.example.viniciuscoscia.greatrecipes.R;
import com.example.viniciuscoscia.greatrecipes.entity.Step;
import com.example.viniciuscoscia.greatrecipes.ui.mainActivity.MainActivity;

import org.junit.Rule;
import org.junit.Test;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasExtraWithKey;
import static org.hamcrest.Matchers.anyOf;

public class MainActivityIntentTest {
    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void mainActivity_intentTest() {
        onView(ViewMatchers.withId(R.id.rv_recipes))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        intended(anyOf(hasExtraWithKey(Step.STEP_KEY)));
    }
}
