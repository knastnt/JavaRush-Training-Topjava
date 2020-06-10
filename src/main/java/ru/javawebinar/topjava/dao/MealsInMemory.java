package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class MealsInMemory implements MealsDAO {
    private static AtomicLong idCounter = new AtomicLong(0);
    private static Set<Meal> mealDb = new ConcurrentSkipListSet<>();

    @Override
    public synchronized void addMeal(Meal meal) {
        if (meal.getId() != null) throw new IllegalArgumentException();
        meal.setId(idCounter.incrementAndGet());
        mealDb.add(meal.clone()); //Отвязываем отображение
    }

    @Override
    public void deleteMeal(Meal meal) {
        deleteMealById(meal.getId());
    }

    @Override
    public void deleteMealById(long mealId) {
        if (!mealDb.removeIf(m -> m.getId()==mealId)) throw new NullPointerException();
    }

    @Override
    public synchronized void updateMeal(Meal meal) {
        if (meal.getId() == null || meal.getId() < 1) throw new IllegalArgumentException();
        deleteMealById(meal.getId());
        addMeal(meal.clone()); //Отвязываем отображение
    }

    @Override
    public List<Meal> getAllMeals() {
        return mealDb.stream()
                .map(m -> m.clone()) //Отвязываем отображение
                .collect(Collectors.toList());
    }

    @Override
    public Meal getMealById(long id) throws NoSuchElementException {
        Optional<Meal> mealOpt = mealDb.stream()
                .filter(meal -> meal.getId() == id)
                .map(m -> m.clone()) //Отвязываем отображение
                .findFirst();
        return mealOpt.get();
    }

}
