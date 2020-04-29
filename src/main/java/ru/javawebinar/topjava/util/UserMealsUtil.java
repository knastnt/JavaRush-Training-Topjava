package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        List<UserMealWithExcess> mealsTo = filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);

//        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO return filtered list with excess. Implement by cycles

        List<UserMealWithExcess> userMealWithExcesses = new ArrayList<>();

        Map<LocalDate, Integer> dateCaloriesMap = new HashMap<>();

        for (UserMeal meal : meals) {
            LocalDate mealLocalDate = meal.getDateTime().toLocalDate();
            dateCaloriesMap.put(mealLocalDate, dateCaloriesMap.getOrDefault(mealLocalDate, 0) + meal.getCalories());
        }

        for (UserMeal meal : meals) {
            LocalDate mealLocalDate = meal.getDateTime().toLocalDate();
            LocalTime mealLocalTime = meal.getDateTime().toLocalTime();
            //использую compareTo, чтобы применить открытый спереди интервал
            if (mealLocalTime.compareTo(startTime) >= 0 && mealLocalTime.isBefore(endTime)) {
                userMealWithExcesses.add(meal.toUserMealWithExcess(
                        dateCaloriesMap.get(mealLocalDate) > caloriesPerDay
                ));
            }
        }

        return userMealWithExcesses;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO Implement by streams

        Map<LocalDate, Integer> dateCaloriesMap = meals.stream()
                .collect(
                        Collectors.groupingBy(
                                userMeal -> userMeal.getDateTime().toLocalDate(),
                                Collectors.summingInt(userMeal -> userMeal.getCalories())
                        )
                );
        List<UserMealWithExcess> userMealWithExcesses = meals.stream()
                .filter(userMeal -> {
                    LocalTime mealLocalTime = userMeal.getDateTime().toLocalTime();
                    return mealLocalTime.compareTo(startTime) >= 0 && mealLocalTime.isBefore(endTime);
                })
                .map(userMeal -> {
                    LocalDate mealLocalDate = userMeal.getDateTime().toLocalDate();
                    return userMeal.toUserMealWithExcess(
                            dateCaloriesMap.get(mealLocalDate) > caloriesPerDay
                    );
                }).collect(Collectors.toList());

        return userMealWithExcesses;
    }
}
