package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealsDAO {
    void addMeal(Meal meal);
    void deleteMeal(Meal meal);
    void deleteMealById(long mealId);
    void updateMeal(Meal meal);
    List<Meal> getAllMeals();
    Meal getMealById(long id);
}
