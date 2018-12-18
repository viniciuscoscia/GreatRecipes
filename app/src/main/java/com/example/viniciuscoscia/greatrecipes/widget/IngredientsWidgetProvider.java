package com.example.viniciuscoscia.greatrecipes.widget;

import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.RemoteViews;

import com.example.viniciuscoscia.greatrecipes.R;
import com.example.viniciuscoscia.greatrecipes.entity.Recipe;
import com.example.viniciuscoscia.greatrecipes.ui.mainActivity.MainActivity;

/**
 * Implementation of App Widget functionality.
 */
public class IngredientsWidgetProvider extends AppWidgetProvider {

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                Recipe recipe, int appWidgetId) {

//        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.ingredients_widget);
//
//        Intent intent = new Intent(context, MainActivity.class);
//        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
//
//        remoteViews.setOnClickPendingIntent(R.id.tv_ingredient, pendingIntent);
//
//        appWidgetManager.updateAppWidget(appWidgetId, remoteViews);


        RemoteViews rv = getIngredientsRemoteView(context, recipe);
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.ingredients_view);
        appWidgetManager.updateAppWidget(appWidgetId, rv);
    }

    private static RemoteViews getIngredientsRemoteView(Context context, Recipe recipe) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.ingredients_widget);
        Intent intent = new Intent(context, IngredientListService.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(Recipe.RECIPE_KEY, recipe);
        intent.putExtra(IngredientListService.BUNDLE_KEY, bundle);

        views.setRemoteAdapter(R.id.lv_ingredients, intent);

        Intent appIntent = new Intent(context, MainActivity.class);
        PendingIntent appPendingIntent = PendingIntent.getActivity(context, 0, appIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.ingredients_view, appPendingIntent);
        views.setEmptyView(R.id.lv_ingredients, R.id.empty_view);
        return views;
    }

    public static void updateIngredientsWidgets(Context context, AppWidgetManager appWidgetManager,
                                                Recipe recipe, int[] appWidgetIds) {
        for(int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, recipe, appWidgetId);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

