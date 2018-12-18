package com.example.viniciuscoscia.greatrecipes.ui.mainActivity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.viniciuscoscia.greatrecipes.databinding.RecipeCardBinding;
import com.example.viniciuscoscia.greatrecipes.entity.Recipe;
import com.example.viniciuscoscia.greatrecipes.utils.ImageResources;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.RecipesViewHolder> {

    private RecipeOnClick recipeOnClick;

    private List<Recipe> recipeList = new ArrayList<>();

    public RecipesAdapter(RecipeOnClick recipeOnClick) {
        this.recipeOnClick = recipeOnClick;
    }

    @NonNull
    @Override
    public RecipesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        final RecipeCardBinding view = RecipeCardBinding
                .inflate(LayoutInflater.from(viewGroup.getContext()), viewGroup, false);
        return new RecipesViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull RecipesViewHolder recipesViewHolder, int i) {
        Context context = recipesViewHolder.itemView.getContext();
        Recipe actualRecipe = recipeList.get(i);

        recipesViewHolder.recipeCardBinding.setRecipe(actualRecipe);

        setImage(recipesViewHolder, context, actualRecipe);
    }

    private void setImage(@NonNull RecipesViewHolder recipesViewHolder, Context context, Recipe actualRecipe) {
        //Verify if ImagePath is empty, if it is, may throw a exception
        if(!actualRecipe.getImagePath().isEmpty()) {
            Picasso.with(recipesViewHolder.itemView.getContext())
                    .load(actualRecipe.getImagePath())
                    .error(ImageResources.getImageResource(context, actualRecipe.getName()))
                    .into(recipesViewHolder.recipeCardBinding.ivRecipeImage);
            return;
        }

        Picasso.with(recipesViewHolder.itemView.getContext())
                .load(ImageResources.getImageResource(context, actualRecipe.getName()))
                .into(recipesViewHolder.recipeCardBinding.ivRecipeImage);
    }

    public void setRecipeList(List<Recipe> recipeList) {
        this.recipeList = recipeList;
        notifyDataSetChanged();
    }

    public interface RecipeOnClick{
        void onRecipeClickListener(Recipe recipe);
    }

    public class RecipesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        RecipeCardBinding recipeCardBinding;

        public RecipesViewHolder(RecipeCardBinding itemView) {
            super(itemView.getRoot());
            recipeCardBinding = itemView;
            recipeCardBinding.getRoot().setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Recipe recipe = recipeList.get(getAdapterPosition());
            recipeOnClick.onRecipeClickListener(recipe);
        }
    }
}
