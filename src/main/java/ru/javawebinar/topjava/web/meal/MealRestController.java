package ru.javawebinar.topjava.web.meal;

import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Controller
public class MealRestController extends AbstractMealController {

    public List<Meal> getAll(LocalDate startDate, LocalDate endDate) {
        log.info("getAll from {} to {}", startDate, endDate);
        return service.getAll(SecurityUtil.authUserId(), startDate, endDate);
    }

}