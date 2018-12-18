package com.example.viniciuscoscia.greatrecipes.ui.stepDetailsActivity;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.example.viniciuscoscia.greatrecipes.entity.Step;

import java.util.List;

public class StepDetailsViewModel extends AndroidViewModel {

    private List<Step> stepList;
    private int listPosition;

    public StepDetailsViewModel(@NonNull Application application) {
        super(application);
    }

    public List<Step> getStepList() {
        return stepList;
    }

    public void setStepList(List<Step> stepList) {
        this.stepList = stepList;
    }

    public int getListPosition() {
        return listPosition;
    }

    public void setListPosition(int listPosition) {
        this.listPosition = listPosition;
    }
}
