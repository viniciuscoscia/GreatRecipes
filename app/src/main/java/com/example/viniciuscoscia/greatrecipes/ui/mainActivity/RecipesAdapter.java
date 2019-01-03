package com.example.viniciuscoscia.greatrecipes.ui.mainActivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.viniciuscoscia.greatrecipes.databinding.RecipeCardBinding;
import com.example.viniciuscoscia.greatrecipes.entity.Recipe;
import com.example.viniciuscoscia.greatrecipes.utils.ImageResources;
import com.squareup.picasso.Picasso;
import com.example.viniciuscoscia.greatrecipes.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.RecipesViewHolder> {

    private RecipeOnClick recipeOnClick;

    private List<Recipe> recipeList = new ArrayList<>();

    public RecipesAdapter(RecipeOnClick recipeOnClick) {
        this.recipeOnClick = recipeOnClick;
    }

    @NonNull
    @Override
    public RecipesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View itemLista = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recipe_card,
                                                                    viewGroup, false);

        return new RecipesViewHolder(itemLista);

//        final RecipeCardBinding view = RecipeCardBinding
//                .inflate(LayoutInflater.from(viewGroup.getContext()), viewGroup, false);
//        return new RecipesViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull RecipesViewHolder recipesViewHolder, int i) {
        Context context = recipesViewHolder.itemView.getContext();
        Recipe actualRecipe = recipeList.get(i);

        recipesViewHolder.recipeName.setText(actualRecipe.getName());
        setImage(recipesViewHolder.recipeImage, context, actualRecipe);
        recipesViewHolder.recipeServings.setText(context.getResources()
                .getQuantityString(R.plurals.recipe_servings, actualRecipe.getServings(),
                        actualRecipe.getServings()));
    }

    private void setImage(@NonNull ImageView recipeServings, Context context, Recipe actualRecipe) {
        //Verify if ImagePath is empty, if it is, may throw a exception
        if(!actualRecipe.getImagePath().isEmpty()) {
            Picasso.with(context)
                    .load(actualRecipe.getImagePath())
                    .error(ImageResources.getImageResource(context, actualRecipe.getName()))
                    .into(recipeServings);
            return;
        }

        Picasso.with(context)
                .load(ImageResources.getImageResource(context, actualRecipe.getName()))
                .into(recipeServings);
    }

    void setRecipeList(List<Recipe> recipeList) {
        this.recipeList = recipeList;
        notifyDataSetChanged();
    }

    public interface RecipeOnClick{
        void onRecipeClickListener(Recipe recipe);
    }

    public class RecipesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView recipeName;
        ImageView recipeImage;
        TextView recipeServings;

        RecipesViewHolder(View itemView) {
            super(itemView);
            recipeName = itemView.findViewById(R.id.tv_recipeName);
            recipeImage = itemView.findViewById(R.id.iv_recipeImage);
            recipeServings = itemView.findViewById(R.id.tv_recipeServings);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Recipe recipe = recipeList.get(getAdapterPosition());
            recipeOnClick.onRecipeClickListener(recipe);
        }
    }
}
