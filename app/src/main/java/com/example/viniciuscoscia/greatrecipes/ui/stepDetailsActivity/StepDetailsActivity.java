package com.example.viniciuscoscia.greatrecipes.ui.stepDetailsActivity;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.example.viniciuscoscia.greatrecipes.R;
import com.example.viniciuscoscia.greatrecipes.entity.Recipe;
import com.example.viniciuscoscia.greatrecipes.entity.Step;
import com.example.viniciuscoscia.greatrecipes.ui.fragments.stepDetails.StepDetailsFragment;

public class StepDetailsActivity extends AppCompatActivity {

    private StepDetailsViewModel viewModel;
    private FragmentManager fragmentManager;
    private ImageView imgPreviousStep;
    private ImageView imgNextStep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_details);

        imgPreviousStep = findViewById(R.id.iv_previous_step);
        imgNextStep = findViewById(R.id.iv_next_step);

        setupViewModel();

        getDataFromIntent();
        startStepDetailsFragment();
        checkNavigationArrows();
    }

    private void getDataFromIntent() {
        Recipe recipe = getIntent().getParcelableExtra(Recipe.RECIPE_KEY);
        viewModel.setStepList(recipe.getStepList());
        int stepPosition = getIntent().getIntExtra(Step.STEP_POSITION, 0);
        viewModel.setListPosition(stepPosition);
    }

    private void setupViewModel() {
        viewModel = ViewModelProviders.of(this).get(StepDetailsViewModel.class);
    }

    private void startStepDetailsFragment() {

        fragmentManager = getSupportFragmentManager();
        StepDetailsFragment fragment = new StepDetailsFragment();
        fragment.setStep(viewModel.getStepList().get(viewModel.getListPosition()));

        fragmentManager.beginTransaction()
                .replace(R.id.step_details_container, fragment)
                .commit();

    }

    public void nextStep(View view) {
        viewModel.setListPosition(viewModel.getListPosition()+1);
        changeStep();
    }

    public void previousStep(View view) {
        viewModel.setListPosition(viewModel.getListPosition()-1);
        changeStep();
    }

    private void changeStep() {
        checkNavigationArrows();

        StepDetailsFragment fragment = new StepDetailsFragment();
        fragment.setStep(viewModel.getStepList().get(viewModel.getListPosition()));

        fragmentManager.beginTransaction()
                .replace(R.id.step_details_container, fragment)
                .commit();
    }

    private void checkNavigationArrows() {
        int listSize = viewModel.getStepList().size();

        if(viewModel.getListPosition() >= listSize-1)
            imgNextStep.setVisibility(View.GONE);
         else
            imgNextStep.setVisibility(View.VISIBLE);


        if(viewModel.getListPosition() <= 0)
            imgPreviousStep.setVisibility(View.GONE);
        else
            imgPreviousStep.setVisibility(View.VISIBLE);

    }


}
