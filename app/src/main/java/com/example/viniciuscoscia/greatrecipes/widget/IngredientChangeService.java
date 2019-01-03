package com.example.viniciuscoscia.greatrecipes.widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import androidx.annotation.Nullable;

import com.example.viniciuscoscia.greatrecipes.entity.Recipe;

public class IngredientChangeService extends IntentService {

    public static String ACTION_UPDATE_INGREDIENTS = "updateIngredients";

    public IngredientChangeService() {
        super("IngredientChangeService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String action = intent.getAction();

        if(action.equals(ACTION_UPDATE_INGREDIENTS)) {
            Recipe recipe = intent.getParcelableExtra(Recipe.RECIPE_KEY);
            handleUpdateIngredientsList(recipe);
        }
    }

    private void handleUpdateIngredientsList(Recipe recipe) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, IngredientsWidgetProvider.class));

//        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.lv_ingredients);
        IngredientsWidgetProvider.updateIngredientsWidgets(this, appWidgetManager, recipe, appWidgetIds);
    }

    public static void changeIngredientsListWidget(Context context, Recipe recipe) {
        Intent intent = new Intent(context, IngredientChangeService.class);
        intent.setAction(ACTION_UPDATE_INGREDIENTS);
        intent.putExtra(Recipe.RECIPE_KEY, recipe);
        context.startService(intent);
    }



}
