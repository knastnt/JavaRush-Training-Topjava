package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
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


    //Кастомный коллектор
    private static class Coll implements Collector<UserMeal, List<UserMeal>, List<UserMealWithExcess>> {
        private LocalTime startTime;
        private LocalTime endTime;
        private int caloriesPerDay;

//        private List<UserMealWithExcess> userMealWithExcesses = new ArrayList<>();
        private HashMap<LocalDate, Integer> dateCaloriesMap = new HashMap<>();

        public Coll(LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
            this.startTime = startTime;
            this.endTime = endTime;
            this.caloriesPerDay = caloriesPerDay;
        }

        @Override
        public Supplier<List<UserMeal>> supplier() {
            return ArrayList::new;
        }

        @Override
        public BiConsumer<List<UserMeal>, UserMeal> accumulator() {
            return (userMealWithExcesses, meal) -> {
                LocalTime mealLocalTime = meal.getDateTime().toLocalTime();
                //использую compareTo, чтобы применить открытый спереди интервал
                if (mealLocalTime.compareTo(startTime) >= 0 && mealLocalTime.isBefore(endTime)) {
                    userMealWithExcesses.add(meal);/*.toUserMealWithExcess(
                            false //dateCaloriesMap.get(mealLocalDate) > caloriesPerDay
                    ));*/
                }

                LocalDate mealLocalDate = meal.getDateTime().toLocalDate();
                dateCaloriesMap.put(mealLocalDate, dateCaloriesMap.getOrDefault(mealLocalDate, 0) + meal.getCalories());
            };
        }

//        @Override
//        public BinaryOperator<HashMap<LocalDate, Integer>> combiner() {
//            return (userMealIntegerHashMap, userMealIntegerHashMap2) -> {
//                userMealIntegerHashMap2.forEach((localDate, integer) -> {
//                    userMealIntegerHashMap.merge(localDate, integer, (integer1, integer2) -> integer1 + integer2);
//                });
//                return userMealIntegerHashMap;
//            };
//        }


        @Override
        public BinaryOperator<List<UserMeal>> combiner() {
            return (userMeal, userMeal2) -> {
                userMeal.addAll(userMeal2);
                return userMeal;
            };
        }

//        @Override
//        public Function<HashMap<LocalDate, Integer>, List<UserMealWithExcess>> finisher() {
//            return userMealIntegerHashMap -> {
//                List<UserMealWithExcess> userMealWithExcesses = new ArrayList<>();
//
//                for (UserMeal meal : meals) {
//                    LocalDate mealLocalDate = meal.getDateTime().toLocalDate();
//                    LocalTime mealLocalTime = meal.getDateTime().toLocalTime();
//                    //использую compareTo, чтобы применить открытый спереди интервал
//                    if (mealLocalTime.compareTo(startTime) >= 0 && mealLocalTime.isBefore(endTime)) {
//                        userMealWithExcesses.add(meal.toUserMealWithExcess(
//                                dateCaloriesMap.get(mealLocalDate) > caloriesPerDay
//                        ));
//                    }
//                }
//            };
//        }


        @Override
        public Function<List<UserMeal>, List<UserMealWithExcess>> finisher() {
            return userMeals -> {
              return userMeals.stream().map(userMeal -> {
                  return userMeal.toUserMealWithExcess(dateCaloriesMap.get(userMeal.getDateTime().toLocalDate()) > caloriesPerDay);
              }).collect(Collectors.toList());
            };
        }

        @Override
        public Set<Characteristics> characteristics() {
            Set<Characteristics> ret = new HashSet<>();
            ret.add(Characteristics.UNORDERED);
            return ret;
        }
    }


    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO Implement by streams

        //Через кастомный коллектор (https://habr.com/ru/company/luxoft/blog/270383/)
        List<UserMealWithExcess> uwe = meals.stream().collect(new Coll(startTime, endTime, caloriesPerDay));


        //Не, это не используется, просто тренировки
        Map<LocalDate, Integer> dateCalo = meals.stream().collect(Collector.of(
                () -> { return new HashMap<LocalDate, Integer>(); },
                (dateCaloriesMap, meal) -> {
                    LocalDate mealLocalDate = meal.getDateTime().toLocalDate();
                    dateCaloriesMap.put(mealLocalDate, dateCaloriesMap.getOrDefault(mealLocalDate, 0) + meal.getCalories());
                },
                (userMealIntegerHashMap, userMealIntegerHashMap2) -> {
                    userMealIntegerHashMap2.forEach((localDate, integer) -> {
                        userMealIntegerHashMap.merge(localDate, integer, (integer1, integer2) -> integer1 + integer2);
                    });
                    return userMealIntegerHashMap;
                },
                userMealIntegerHashMap -> {
                    return userMealIntegerHashMap;
                }
        ));


        //Через пару стримов
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

        return uwe;
    }
}
