package com.example.viniciuscoscia.greatrecipes.ui.fragments.recipeDetails;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.viniciuscoscia.greatrecipes.R;
import com.example.viniciuscoscia.greatrecipes.entity.Step;

import java.util.List;

public class RecipeDetailsAdapter extends RecyclerView.Adapter<RecipeDetailsAdapter.RecipeDetailsViewHolder>{

    private List<Object> objectList;
    private RecipeDetailsOnClick recipeDetailsOnClick;

    public RecipeDetailsAdapter(RecipeDetailsOnClick recipeDetailsOnClick) {
        this.recipeDetailsOnClick = recipeDetailsOnClick;
    }

    public interface RecipeDetailsOnClick {
        void onClickListener(Step step, int adapterPosition);
    }

    @NonNull
    @Override
    public RecipeDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_recipe_ingredients_step, viewGroup, false);
        return new RecipeDetailsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeDetailsViewHolder recipeDetailsViewHolder, int i) {
        if (objectList.get(i) instanceof Step){
            Step step = (Step) objectList.get(i);
            recipeDetailsViewHolder.textView.setText(step.getId() + " - " + step.getShortDescription());
        }

        if(objectList.get(i) instanceof String) {
            recipeDetailsViewHolder.textView.setText((String)objectList.get(i));
        }
    }

    @Override
    public int getItemCount() {
        return objectList.size();
    }

    public void setObjectList(List<Object> objectList) {
        this.objectList = objectList;
        notifyDataSetChanged();
    }

    public class RecipeDetailsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView textView;

        public RecipeDetailsViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv_ingredients_step);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Object object = objectList.get(adapterPosition);

            if(object instanceof String) {
                return;
            }

            if(object instanceof Step) {
                recipeDetailsOnClick.onClickListener((Step) object, adapterPosition);
            }
        }
    }
}
