package com.example.viniciuscoscia.greatrecipes.ui.stepDetailsActivity;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.viniciuscoscia.greatrecipes.R;
import com.example.viniciuscoscia.greatrecipes.entity.Recipe;
import com.example.viniciuscoscia.greatrecipes.entity.Step;
import com.example.viniciuscoscia.greatrecipes.ui.fragments.stepDetails.StepDetailsFragment;

import java.util.Objects;

public class StepDetailsActivity extends AppCompatActivity implements StepDetailsFragment.VideoConfiguration {

    private StepDetailsViewModel viewModel;
    private ImageView imgPreviousStep;
    private ImageView imgNextStep;
    private View viewBotton;
    private StepDetailsFragment fragment;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_details);

        fragmentManager = getSupportFragmentManager();

        if(savedInstanceState != null) {
            fragment = (StepDetailsFragment)getSupportFragmentManager().getFragment(savedInstanceState, StepDetailsFragment.TAG);
        }

        imgPreviousStep = findViewById(R.id.iv_previous_step);
        imgNextStep = findViewById(R.id.iv_next_step);
        viewBotton = findViewById(R.id.view_botton);

        setupViewModel();

        if(viewModel.isFirstTime()) {
            getDataFromIntent();
            startStepDetailsFragment();
            viewModel.setFirstTime(false);
        }

        checkNavigationArrows();
    }

    private void getDataFromIntent() {
        if(viewModel.isFirstTime()){
            Recipe recipe = getIntent().getParcelableExtra(Recipe.RECIPE_KEY);
            viewModel.setStepList(recipe.getStepList());
            int stepPosition = getIntent().getIntExtra(Step.STEP_POSITION, 0);
            viewModel.setListPosition(stepPosition);
        }
    }

    private void setupViewModel() {
        viewModel = ViewModelProviders.of(this).get(StepDetailsViewModel.class);
    }

    private void startStepDetailsFragment() {
        fragment = new StepDetailsFragment();
        fragment.setStep(viewModel.getStepList().get(viewModel.getListPosition()));

        fragmentManager.beginTransaction()
                .replace(R.id.step_details_container, fragment)
                .commit();
    }

    public void nextStep(View view) {
        viewModel.setListPosition(viewModel.getListPosition()+1);
        checkNavigationArrows();
        startStepDetailsFragment();
    }

    public void previousStep(View view) {
        viewModel.setListPosition(viewModel.getListPosition()-1);
        checkNavigationArrows();
        startStepDetailsFragment();
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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        fragmentManager.executePendingTransactions();
        //Save the fragment's instance

        if(fragment.isAdded()) {
            fragmentManager.putFragment(outState, StepDetailsFragment.TAG, fragment);
        }
    }

    @Override
    public void enterFullScreen() {
        Objects.requireNonNull(getSupportActionBar()).hide();
        imgPreviousStep.setVisibility(View.GONE);
        imgNextStep.setVisibility(View.GONE);
        viewBotton.setVisibility(View.GONE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    public void exitFullScreen() {
        Objects.requireNonNull(getSupportActionBar()).show();
        imgPreviousStep.setVisibility(View.VISIBLE);
        imgNextStep.setVisibility(View.VISIBLE);
        viewBotton.setVisibility(View.VISIBLE);
        checkNavigationArrows();
    }
}
