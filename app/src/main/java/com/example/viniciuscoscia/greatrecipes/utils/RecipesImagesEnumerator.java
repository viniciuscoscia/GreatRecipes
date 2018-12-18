package com.example.viniciuscoscia.greatrecipes.utils;

import com.example.viniciuscoscia.greatrecipes.R;

public enum RecipesImagesEnumerator {
    NUTELLA_PIE("Nutella Pie", R.drawable.nutella_pie),
    BROWNIES("Brownies", R.drawable.brownies),
    YELLOW_CAKE("Yellow Cake", R.drawable.yellow_cake),
    CHEESECAKE("Cheesecake", R.drawable.cheese_cake);

    private String recipeName;
    private int resourceId;

    RecipesImagesEnumerator(String recipeName, int resourceId) {
        this.recipeName = recipeName;
        this.resourceId = resourceId;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public int getResourceId() {
        return resourceId;
    }
}
