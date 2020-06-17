package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.model.Meal;

import java.util.Objects;

import static ru.javawebinar.topjava.util.MealsUtil.DEFAULT_CALORIES_PER_DAY;

public class SecurityUtil {

    private static int myId = 1;

    public static void setMyId(int myId) {
        SecurityUtil.myId = myId;
    }

    public static int authUserId() {
        return myId;
    }

    public static int authUserCaloriesPerDay() {
        return DEFAULT_CALORIES_PER_DAY;
    }

    public static boolean isMealBelongsToAuthUser(Meal meal){
        Objects.requireNonNull(meal);
        return meal.getUserId() == SecurityUtil.authUserId();
    }
}