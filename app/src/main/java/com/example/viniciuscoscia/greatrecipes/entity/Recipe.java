package com.example.viniciuscoscia.greatrecipes.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Recipe implements Parcelable {

    public static String RECIPE_KEY = "recipe_key";

    private int id;
    private String name;
    @SerializedName("ingredients")
    private List<Ingredient> ingredientList;
    @SerializedName("steps")
    private List<Step> stepList;
    private int servings;
    @SerializedName("image")
    private String imagePath;

    protected Recipe(Parcel in) {
        id = in.readInt();
        name = in.readString();
        ingredientList = in.createTypedArrayList(Ingredient.CREATOR);
        stepList = in.createTypedArrayList(Step.CREATOR);
        servings = in.readInt();
        imagePath = in.readString();
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Ingredient> getIngredientList() {
        return ingredientList;
    }

    public void setIngredientList(List<Ingredient> ingredientList) {
        this.ingredientList = ingredientList;
    }

    public List<Step> getStepList() {
        return stepList;
    }

    public void setStepList(List<Step> stepList) {
        this.stepList = stepList;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeTypedList(ingredientList);
        dest.writeTypedList(stepList);
        dest.writeInt(servings);
        dest.writeString(imagePath);
    }
}
