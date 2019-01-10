package com.example.viniciuscoscia.greatrecipes.widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import com.example.viniciuscoscia.greatrecipes.R;

import androidx.annotation.Nullable;

public class IngredientChangeService extends IntentService {

    public static String ACTION_UPDATE_INGREDIENTS = "updateIngredients";

    public IngredientChangeService() {
        super("IngredientChangeService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String action = intent.getAction();

        if(action.equals(ACTION_UPDATE_INGREDIENTS)) {
            handleUpdateIngredientsList();
        }
    }

    private void handleUpdateIngredientsList() {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, IngredientsWidgetProvider.class));

//        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.lv_ingredients);
        IngredientsWidgetProvider.updateIngredientsWidgets(this, appWidgetManager, appWidgetIds);
    }

    public static void changeIngredientsListWidget(Context context) {
        Intent intent = new Intent(context, IngredientChangeService.class);
        intent.setAction(ACTION_UPDATE_INGREDIENTS);
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, IngredientsWidgetProvider.class));
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.lv_ingredients);
        context.startService(intent);
    }



}
