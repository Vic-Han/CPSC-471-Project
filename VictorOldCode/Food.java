
package com.example.demo;

public class Food {
    private String foodName;
    private int g_serving;
    private int ml_serving;
    private int carbs;
    private int fats;
    private int proteins;
    private int calories;
    //private float servings;
    public Food(String name , int grams, int ml, int carb, int fat, int protein, int calorie){
        setName(name);
        setGramsPerServing(grams);
        setmlPerServing(ml);
        setProtein(protein);
        setCarbs(carb);
        setFats(fat);
        setCalories(calorie);
    }

    public String getName(){
        return foodName;
    }
    public void setName(String name){
        foodName = name;
    }
    public int getGramsPerServing()
    {
        return g_serving;
    }
    public void setGramsPerServing(int grams)
    {
        g_serving = grams;
    }
    public int getmlPerServing()
    {
        return ml_serving;
    }
    public void setmlPerServing(int ml)
    {
        ml_serving = ml;
    }
    public int getCarbs()
    {
        return carbs;
    }
    public void setCarbs(int carb)
    {
        carbs = carb;
    }
    public int getProtien()
    {
        return proteins;
    }
    public void setProtein(int protein)
    {
        proteins = protein;
    }
    public int getFats()
    {
        return fats;
    }
    public void setFats(int fat)
    {
        fats = fat;
    }
    public int getCalories()
    {
        return calories;
    }
    public void setCalories(int calorie)
    {
        calories = calorie;
    }

}