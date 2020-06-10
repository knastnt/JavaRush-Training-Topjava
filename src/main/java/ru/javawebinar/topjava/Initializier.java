package ru.javawebinar.topjava;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealsDAOFabric;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

@WebListener
public class Initializier implements ServletContextListener {
    private static final Logger log = getLogger(Initializier.class);



    @Override
    public void contextInitialized(ServletContextEvent sce) {
        log.info("Hello!");

        try {
            Arrays.asList(
                    new Meal(null, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                    new Meal(null, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                    new Meal(null, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                    new Meal(null, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                    new Meal(null, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                    new Meal(null, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                    new Meal(null, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
            ).forEach(MealsDAOFabric.getMealsDAO()::addMeal);
        }catch (Exception e){
            
        }

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        log.info("Good Bye.");
    }
}
