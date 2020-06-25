package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int NOT_FOUND_MEAL_ID = 99999;
    public static final int USER_MEAL_ID = START_SEQ + 2;
    public static final int ADMIN_MEAL1_ID = START_SEQ + 3;

    public static final LocalDate ONE_DAY = LocalDate.of(2020, 06, 23);

    public static final Meal USER_MEAL = new Meal(USER_MEAL_ID, LocalDateTime.of(2020, 06, 23, 15,6,1), "Описание еды", 15);

    public static final Meal ADMIN_MEAL1 = new Meal(ADMIN_MEAL1_ID, LocalDateTime.of(2020, 06, 23, 15,6,1), "Админская еда", 30);
    public static final Meal ADMIN_MEAL2 = new Meal(ADMIN_MEAL1_ID+1, LocalDateTime.of(2020, 06, 22, 23,59,59), "Админская еда прошлого дня", 150);
    public static final Meal ADMIN_MEAL3 = new Meal(ADMIN_MEAL1_ID+2, LocalDateTime.of(2020, 06, 23, 0,0,0), "Админская еда в начале суток", 100);
    public static final Meal ADMIN_MEAL4 = new Meal(ADMIN_MEAL1_ID+3, LocalDateTime.of(2020, 06, 23, 23,59,0), "Админская еда в 23:59:00", 1000);
    public static final Meal ADMIN_MEAL5 = new Meal(ADMIN_MEAL1_ID+4, LocalDateTime.of(2020, 06, 23, 23,59,59), "Админская еда в 23:59:59", 870);
    public static final Meal ADMIN_MEAL6 = new Meal(ADMIN_MEAL1_ID+5, LocalDateTime.of(2020, 06, 24, 13,0,5), "Админская еда следующего дня", 500);

    public static Meal getNew() {
        return new Meal(LocalDateTime.now(), "Now Meal Desc", 55);
    }

    public static Meal getUpdated(Meal meal) {
        Meal updated = new Meal(meal);
        updated.setDescription("Updated " + updated.getDescription());
        updated.setCalories(1 + updated.getCalories());
        return updated;
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingFieldByFieldElementComparator().isEqualTo(expected);
    }
}
