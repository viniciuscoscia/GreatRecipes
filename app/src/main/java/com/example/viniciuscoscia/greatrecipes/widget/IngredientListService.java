package com.example.viniciuscoscia.greatrecipes.widget;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.viniciuscoscia.greatrecipes.R;
import com.example.viniciuscoscia.greatrecipes.entity.Recipe;

public class IngredientListService extends RemoteViewsService {

    public static final String BUNDLE_KEY = "bundleKey";

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        Bundle bundle = intent.getBundleExtra(BUNDLE_KEY);
        Recipe recipe = bundle.getParcelable(Recipe.RECIPE_KEY);

        return new IngredientListViewsFactory(this.getApplicationContext(), recipe);
    }
}


class IngredientListViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    Recipe recipe;
    Context mContext;

    public IngredientListViewsFactory(Context applicationContext, Recipe recipe) {
        mContext = applicationContext;
        this.recipe = recipe;
    }

    @Override
    public void onDataSetChanged() {
        Log.d("TESTE", "TESTE");
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