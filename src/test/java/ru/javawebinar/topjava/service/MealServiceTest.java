package ru.javawebinar.topjava.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.jdbc.JdbcMealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.Collections;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;
import static ru.javawebinar.topjava.UserTestData.NOT_FOUND_USER_ID;

//Настройка спринг контекста
@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
//Внедрение зависимостей
@RunWith(SpringRunner.class)
//Перед каждым тестом выполняется популяция базы
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    @Autowired
    private JdbcMealRepository jdbcMealRepository;

    @Autowired
    private MealService mealService;

    @Test
    public void get() {
        //NotFoundException когда пользователь берет не свою еду
        Assert.assertThrows(NotFoundException.class, () -> mealService.get(USER_MEAL_ID, ADMIN_ID));

        //Пользователь берет не существующую еду
        Assert.assertThrows(NotFoundException.class, () -> mealService.get(NOT_FOUND_MEAL_ID, ADMIN_ID));

        //Пользователь берет свою еду
        assertMatch(mealService.get(ADMIN_MEAL1_ID, ADMIN_ID), ADMIN_MEAL1);
    }

    @Test
    public void delete() {
        //NotFoundException когда пользователь удаляет не свою еду
        Assert.assertThrows(NotFoundException.class, () -> mealService.delete(USER_MEAL_ID, ADMIN_ID));

        //NotFoundException когда пользователь удаляет не существующую еду
        Assert.assertThrows(NotFoundException.class, () -> mealService.delete(NOT_FOUND_MEAL_ID, ADMIN_ID));

        //Пользователь удаляет свою еду
        assertMatch(mealService.get(ADMIN_MEAL1_ID, ADMIN_ID), ADMIN_MEAL1);
        mealService.delete(ADMIN_MEAL1_ID, ADMIN_ID);
        Assert.assertThrows(NotFoundException.class, () -> mealService.get(ADMIN_MEAL1_ID, ADMIN_ID));
    }

    @Test
    public void getBetweenInclusive() {
        assertMatch(mealService.getBetweenInclusive(ONE_DAY, ONE_DAY, ADMIN_ID),
                ADMIN_MEAL5,
                ADMIN_MEAL4,
                ADMIN_MEAL1,
                ADMIN_MEAL3
                );
    }

    @Test
    public void getAll() {
        assertMatch(mealService.getAll(ADMIN_ID),
                ADMIN_MEAL6,
                ADMIN_MEAL5,
                ADMIN_MEAL4,
                ADMIN_MEAL1,
                ADMIN_MEAL3,
                ADMIN_MEAL2
        );
        assertMatch(mealService.getAll(USER_ID),
                USER_MEAL
        );
        assertMatch(mealService.getAll(NOT_FOUND_USER_ID),
                Collections.emptyList()
        );
    }

    @Test
    public void update() {
        //NotFoundException когда пользователь изменяет не свою еду
        Assert.assertThrows(NotFoundException.class, () -> mealService.update(getUpdated(ADMIN_MEAL1), USER_ID));

        //Пользователь изменяет свою еду
        assertMatch(mealService.get(ADMIN_MEAL1_ID, ADMIN_ID), ADMIN_MEAL1);
        mealService.update(getUpdated(ADMIN_MEAL1), ADMIN_ID);
        assertMatch(mealService.get(ADMIN_MEAL1_ID, ADMIN_ID), getUpdated(ADMIN_MEAL1));
    }

    @Test
    public void create() {
        Meal newMeal = getNew();
        Meal created = mealService.create(newMeal, ADMIN_ID);
        Integer newId = created.getId();
        newMeal.setId(newId);
        assertMatch(created, newMeal);
        assertMatch(mealService.get(newId, ADMIN_ID), newMeal);
    }
}