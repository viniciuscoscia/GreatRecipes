package com.example.viniciuscoscia.greatrecipes.ui.fragments.recipeDetails;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.viniciuscoscia.greatrecipes.R;
import com.example.viniciuscoscia.greatrecipes.entity.Step;

import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RecipeDetailsFragment extends Fragment implements RecipeDetailsAdapter.RecipeDetailsOnClick {

    private RecyclerView recyclerView;
    private RecipeDetailsAdapter recipesAdapter;
    private View rootView;
    private RecipeDetailsClickHandler recipeDetailsClickHandler;

    public interface RecipeDetailsClickHandler {
        void clickHandler(Step step, int adapterPosition);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_recipe_details, container, false);
        recyclerView = rootView.findViewById(R.id.rv_recipe_details);

        setupRecyclerView();

        return rootView;
    }

    private void setupRecyclerView() {
        recyclerView.setAdapter(recipesAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext(),
                RecyclerView.VERTICAL, false));
    }

    public void setupAdapter(List<Object> objects) {
        recipesAdapter = new RecipeDetailsAdapter(this);
        recipesAdapter.setObjectList(objects);
    }

    @Override
    public void onClickListener(Step step, int adapterPosition) {
        recipeDetailsClickHandler.clickHandler(step, adapterPosition);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the host activity has implemented the callback interface
        // If not, it throws an exception
        try {
            recipeDetailsClickHandler = (RecipeDetailsClickHandler) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement RecipeDetailsClickHandler");
        }
    }
}
