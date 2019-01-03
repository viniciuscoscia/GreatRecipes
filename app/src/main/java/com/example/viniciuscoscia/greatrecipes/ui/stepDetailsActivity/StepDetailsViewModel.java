package com.example.viniciuscoscia.greatrecipes.ui.stepDetailsActivity;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.annotation.NonNull;

import com.example.viniciuscoscia.greatrecipes.entity.Step;

import java.util.List;

public class StepDetailsViewModel extends AndroidViewModel {

    private List<Step> stepList;
    private int listPosition;
    private boolean firstTime = true;

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

    public boolean isFirstTime() {
        return firstTime;
    }

    public void setFirstTime(boolean firstTime) {
        this.firstTime = firstTime;
    }
}
