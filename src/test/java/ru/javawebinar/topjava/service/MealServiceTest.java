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

import java.time.LocalDateTime;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

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
        assertMatch(mealService.get(ADMIN_MEAL_ID, ADMIN_ID), ADMIN_MEAL);
    }

    @Test
    public void delete() {
        //NotFoundException когда пользователь удаляет не свою еду
        Assert.assertThrows(NotFoundException.class, () -> mealService.delete(USER_MEAL_ID, ADMIN_ID));

        //NotFoundException когда пользователь удаляет не существующую еду
        Assert.assertThrows(NotFoundException.class, () -> mealService.delete(NOT_FOUND_MEAL_ID, ADMIN_ID));

        //Пользователь удаляет свою еду
        assertMatch(mealService.get(ADMIN_MEAL_ID, ADMIN_ID), ADMIN_MEAL);
        mealService.delete(ADMIN_MEAL_ID, ADMIN_ID);
        Assert.assertThrows(NotFoundException.class, () -> mealService.get(ADMIN_MEAL_ID, ADMIN_ID));
    }

    @Test
    public void getBetweenInclusive() {
//        assertMatch(mealService.getBetweenInclusive(ONE_DAY, ONE_DAY, ADMIN_ID),
//                new Meal(),
//
//                );
    }

    @Test
    public void getAll() {
    }

    @Test
    public void update() {
        //NotFoundException когда пользователь изменяет не свою еду
        Assert.assertThrows(NotFoundException.class, () -> mealService.update(getUpdated(ADMIN_MEAL), USER_ID));

        //Пользователь изменяет свою еду
        assertMatch(mealService.get(ADMIN_MEAL_ID, ADMIN_ID), ADMIN_MEAL);
        mealService.update(getUpdated(ADMIN_MEAL), ADMIN_ID);
        assertMatch(mealService.get(ADMIN_MEAL_ID, ADMIN_ID), getUpdated(ADMIN_MEAL));
    }

    @Test
    public void create() {
    }
}