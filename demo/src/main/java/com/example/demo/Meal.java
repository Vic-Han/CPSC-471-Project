package com.example.demo;

public class Meal {
    private int mealNum;
    private int protein;
    private int carbs;
    private int fat;
    private int calories;
    public Meal(int num, int pro, int carb, int fat, int cals){
        mealNum = num;
        protein = pro;
        carbs = carb;
        this.fat = fat;
        calories = cals;
    }
    public int getNum(){
        return mealNum;
    }
    public int getProtein(){
        return protein;
    }
    public int getCarbs(){
        return carbs;
    }
    public int getfat(){
        return fat;
    }
    public int getCalories(){
        return calories;
    }
}
