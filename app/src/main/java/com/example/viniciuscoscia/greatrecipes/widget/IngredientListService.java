package com.example.viniciuscoscia.greatrecipes.widget;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.shapes.Shape;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.viniciuscoscia.greatrecipes.R;
import com.example.viniciuscoscia.greatrecipes.entity.Recipe;
import com.google.gson.Gson;

public class IngredientListService extends RemoteViewsService {


    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new IngredientListViewsFactory(this.getApplicationContext());
    }
}


class IngredientListViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    Recipe recipe;
    Context mContext;

    public IngredientListViewsFactory(Context applicationContext) {
        mContext = applicationContext;
    }

    @Override
    public void onDataSetChanged() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("recipeApp", Context.MODE_PRIVATE);
        String recipeString = sharedPreferences.getString("lastRecipe", "");

        if(recipeString.equals("")) {
           return;
        }
        recipe = new Gson().fromJson(recipeString, Recipe.class);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return recipe.getIngredientList().size();
    }

    @Override
    public RemoteViews getViewAt(int position) {

        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.recipe_ingredient);
        views.setTextViewText(R.id.tv_ingredient, recipe.getIngredientList().get(position).getIngredientName());

        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public void onCreate() {

    }
}