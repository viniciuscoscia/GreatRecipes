package com.example.viniciuscoscia.greatrecipes.utils;

import android.content.Context;

import com.example.viniciuscoscia.greatrecipes.R;

public class ImageResources {
    public static int getImageResource(Context context, String recipeName) {
        if(recipeName.equals(RecipesImagesEnumerator.NUTELLA_PIE.getRecipeName())) {
            return RecipesImagesEnumerator.NUTELLA_PIE.getResourceId();
        }

        if(recipeName.equals(RecipesImagesEnumerator.BROWNIES.getRecipeName())) {
            return RecipesImagesEnumerator.BROWNIES.getResourceId();
        }

        if(recipeName.equals(RecipesImagesEnumerator.CHEESECAKE.getRecipeName())) {
            return RecipesImagesEnumerator.CHEESECAKE.getResourceId();
        }

        if(recipeName.equals(RecipesImagesEnumerator.YELLOW_CAKE.getRecipeName())) {
            return RecipesImagesEnumerator.YELLOW_CAKE.getResourceId();
        }

        return R.drawable.no_image_found;
    }
}
